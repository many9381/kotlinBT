package com.example.kotlinbt.lock

import android.bluetooth.*
import android.bluetooth.le.*
import android.content.*
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.widget.TextView
import com.example.kotlinbt.Application.AppController
import com.example.kotlinbt.DeviceRegister.presenter.DeviceAdapter
import com.example.kotlinbt.R
import com.example.kotlinbt.database.DbOpenHelper
import com.example.kotlinbt.database.ItemData
import kotlinx.android.synthetic.main.activity_lock.*
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.concurrent.thread
import kotlin.coroutines.*

class LockActivity : AppCompatActivity() {

    var countUp = 0

    var totalDeviceSize: Int = 0
    var connectDeviceMax: Int = 0
    var connectDeviceCount: Int = 0
    var unconnectDeviceCount: Int = 0

    var targetDeviceMax: Int = 0
    var targetDeviceCount: Int = 0

    var isPinCheck: Boolean = false
    var countCheck: Boolean = false
    var targetCheck: Boolean = false

    var isRunning: Boolean = true

    lateinit var mDbOpenHelper: DbOpenHelper


    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private var mBluetoothManager: BluetoothManager? = null
    private lateinit var mBluetoothLeScanner: BluetoothLeScanner

    lateinit var moduleList: ArrayList<ItemData>


    private val connectedDevices= mutableListOf<BluetoothGatt>()




    val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
    val ACTION_GATT_CONNECTING = "com.example.bluetooth.le.ACTION_GATT_CONNECTING"
    val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)


        mBluetoothManager = AppController.instance.mBluetoothManager
        mBluetoothAdapter = AppController.instance.mBluetoothAdapter



        mDbOpenHelper = AppController.instance.mDbOpenHelper
        moduleList = mDbOpenHelper.DbMainSelect()


        val timer: TimerHandler = TimerHandler()

        val remianThread = Thread(Runnable {
            while (isRunning) {
                val msg = timer.obtainMessage()
                timer.sendMessage(msg)

                try {
                    Thread.sleep(10) // 10 = 1
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        })

        remianThread.start()

        totalDeviceSize = mDbOpenHelper.DbMainSelect().size

        var temp: String = AppController.connectInfo.getString("countNum", "")
        if(temp.equals("")) {
            connectDeviceMax = 0
        }
        else {
            connectDeviceMax = temp.toInt()
        }

        targetDeviceMax = mDbOpenHelper.DbTarget().size


        // target Device Check
        if(AppController.connectInfo.getBoolean("target_check", false)) {

        }
        else {
            addLog("특정 조건 결과 : Success")

            targetCheck = true
            targetStateText.text = "Success"
            targetStateText.setTextColor(Color.parseColor("#2D7BD7"))
        }

        //Pin check
        if(AppController.connectInfo.getBoolean("pin_check", false)) {

        }
        else {
            addLog("핀 조건 결과 : Success")

            isPinCheck = true
            pinStateText.text = "Success"
            pinStateText.setTextColor(Color.parseColor("#2D7BD7"))
        }



    }

    inner class TimerHandler : android.os.Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            countUp++
            var temp: String = ""

            if((countUp / 100) / 60 < 10) {
                temp = "0${(countUp / 100) / 60}m "
            }
            else {
                temp = "${(countUp / 100) / 60}m "
            }

            if ((countUp / 100) % 60 < 10)
                temp = "${temp}0${(countUp / 100) % 60}s "
            else {
                temp = "$temp${(countUp / 100) % 60}s "
            }


            if (countUp % 100 < 10)
                temp = "${temp}0${countUp % 100}ms "
            else
                temp = "$temp${countUp % 100}ms "

            countupText.text = temp
            //Log.d("timer", "working")

            if(isPinCheck && countCheck && targetCheck) {
                addLog("-------- OPEN --------")

                stateImg.setImageResource(R.drawable.unlock)
                isRunning = false
            }

            if (connectDeviceCount >= connectDeviceMax) {

                if (!countCheck)
                    addLog("연결 조건 결과 : Success")

                countStateText.setTextColor(Color.parseColor("#2D7BD7"))//#E1092E
                countStateText.text = "Success"
                countCheck = true
            }

            if (targetDeviceMax == targetDeviceCount) {

                if (!targetCheck) {
                    addLog("타겟 조건 결과 : Success")
                }

                targetCheck = true
                targetStateText.text = "Success"
                targetStateText.setTextColor(Color.parseColor("#2D7BD7"))//#E1092E
            }

            if (unconnectDeviceCount + connectDeviceCount == totalDeviceSize) {

                isRunning = false

                if (!countCheck) {

                    addLog("연결 조건 결과 : Fail")

                    countStateText.text = "Fail"
                    countStateText.setTextColor(Color.parseColor("#E1092E"))//#E1092E
                }

                if (!targetCheck) {

                    addLog("타켓 조건 결과 : Fail")

                    targetStateText.text = "Fail"
                    targetStateText.setTextColor(Color.parseColor("#E1092E"))//#E1092E
                }
            }


        }
    }

    fun addLog(text: String) {
        runOnUiThread {
            val newString : String = logText.text as String + "\n" + text
            logText.setText(newString)
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        isRunning = false


        Log.d("lock_destroy", "connected device : " + connectedDevices.size )


        for(device in connectedDevices) {
            //device.disconnect()
            val job = CoroutineScope(Dispatchers.Default).launch {

                //device.disconnect()
                //device.close()
                device.disconnect()

                Log.d("Lock_Destroy", "disconnect !! : ${device.device.name} ")
                delay(2000)
                Log.d("Lock_Destroy", "closed !! : ${device.device.name}")
                device.close()

            }
        }


        mBluetoothAdapter.startDiscovery()

    }

    override fun onResume() {
        super.onResume()
        var addLogText: TextView = TextView(this@LockActivity)
        addLogText.text = ""

        mBluetoothAdapter.cancelDiscovery()

        val testset = mBluetoothAdapter.bondedDevices
        //val testset = AppController.instance.checkedBLE



        for(device in testset) {
            val job = CoroutineScope(Dispatchers.Default).launch {

                //delay(50)
                val gatt = device.connectGatt(this@LockActivity, false, mGattCallback, BluetoothDevice.TRANSPORT_LE)

                Log.d("Coroutine", "device : " + device.address)

                }

            }




        /*
        for(device in testset) {
                device.connectGatt(this@LockActivity, false, mGattCallback, BluetoothDevice.TRANSPORT_LE)
                Log.d("Single_connect", "device : " + device.address)
        }

         */






    }

    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val intentAction: String

            if(status == BluetoothGatt.GATT_SUCCESS) {
                if(newState == BluetoothProfile.STATE_CONNECTING) {
                    Log.d("Connecting", "Device : ${gatt.device}")
                }

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    intentAction = ACTION_GATT_CONNECTED

                    val device = gatt.device


                    connectDeviceCount++

                    if (mDbOpenHelper.DbTargetFind(device.address) != null) {
                        targetDeviceCount++
                    }

                    addLog("Connected Device : " + device.name)

                    connectedDevices.add(gatt)
                    broadcastUpdate(intentAction)
                    gatt.disconnect()
                    //gatt.close()


                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    intentAction = ACTION_GATT_DISCONNECTED
                    Log.i("myTag", "---- Disconnected ----")

                    val device = gatt.device
                    Log.i("myTestLog", "disconnected Device : " + device.name)

                    //unconnectDeviceCount++

                    gatt.close()

                    addLog("Disconnected Device : " + device.name)
                    broadcastUpdate(intentAction)


                } else {
                    Log.i("myTestLog", "newState : $newState")
                }

            }
            else {
                gatt.close()
            }

        }

        private fun broadcastUpdate(action: String) {
            val intent = Intent(action)
            sendBroadcast(intent)
        }
    }





}
