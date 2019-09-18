package com.example.kotlinbt.lock

import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.*
import android.content.res.Configuration
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
import kotlinx.coroutines.channels.Channel
import java.lang.Runnable
import kotlin.concurrent.thread
import kotlin.coroutines.*

class LockActivity : AppCompatActivity(), CoroutineScope{

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

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

    lateinit var moduleList: ArrayList<ItemData>
    private val connectedDevices= mutableListOf<BluetoothGatt>()
    lateinit var  testset : MutableSet<BluetoothDevice>
    val checkSet : MutableMap<String, Boolean> = mutableMapOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        job = Job()

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

        checkSet.clear()




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


            if(isPinCheck && countCheck && targetCheck) {
                addLog("-------- OPEN --------")

                val test = AppController.instance.mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT_SERVER)


                //@TODO 실제 구현시에는 connection 유지, 인증 실패할 때 connection 종료로 구현해야함

                val inten = Intent()
                inten.putExtra("result2", "12")
                if(parent == null) {
                    setResult(Activity.RESULT_OK, inten)
                }
                else {
                    parent.setResult(Activity.RESULT_OK, inten)
                }
                stateImg.setImageResource(R.drawable.unlock)
                isRunning = false
                if(AppController.instance.requestedDevice != null) {
                    AppController.instance.mBluetoothGattServer?.cancelConnection(AppController.instance.requestedDevice)


                    AppController.instance.requestedGATT.discoverServices()



                }
                finish()
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

        job.cancel()


        Log.d("lock_destroy", "connected device : " + connectedDevices.size )




        for(device in connectedDevices) {
            //device.disconnect()
            val jobs = CoroutineScope(Dispatchers.Default).launch {

                //device.disconnect()
                //device.close()
                device.disconnect()

                Log.d("Lock_Destroy", "disconnect !! : ${device.device.name} ")
                delay(2000)
                Log.d("Lock_Destroy", "closed !! : ${device.device.name}")
                device.close()

            }
        }



        //mBluetoothAdapter.startDiscovery()

    }

    override fun onResume() {
        super.onResume()
        var addLogText: TextView = TextView(this@LockActivity)
        addLogText.text = ""

        //mBluetoothAdapter.cancelDiscovery()

        testset = AppController.instance.mBluetoothAdapter.bondedDevices

        testset.forEach {
            checkSet[it.address] = false
        }



        launch {

            val jobs: ArrayList<Job> = ArrayList<Job>()


            launch {

                var nu : Int = 1
                while(true) {

                    jobs.clear()

                    testset.forEachIndexed { index, bluetoothDevice ->
                        if(checkSet[bluetoothDevice.address] == false) {
                            jobs.add(launch {

                                val gatt = bluetoothDevice.connectGatt(this@LockActivity, false, mGattCallback, BluetoothDevice.TRANSPORT_LE)
                                delay(300)
                                Log.e("Coroutine", "device ADDR : " + gatt.device.address)
                                Log.e("Coroutine", "device NAME : " + gatt.device.name)

                            })
                        }


                    }


                    jobs.forEach{
                        it.join()
                        delay(180)
                        Log.d("Coroutine", "Cycle : ${nu}" )
                    }

                    if(checkSet.all { it.value == true }) {
                        break
                    }
                    else {
                        Log.d("Coroutine", "delayed .... ")
                        delay(1000)
                        nu = nu + 1
                    }

                    if(nu == 10) {
                        break
                    }
                }
            }

        }


    }

    fun searchFun()  {



    }


/*
    suspend fun searchFun(it: Int) {
        if(checkSet[testset.elementAt(it).address] == false) {
            delay(20)
            val gatt = testset.elementAt(it).connectGatt(this@LockActivity, false, mGattCallback, BluetoothDevice.TRANSPORT_LE)
            Log.d("Coroutine", "device : " + gatt.device)
        }


    }

 */

    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {

            Log.i("LOCK_mGattCallback", "OnStatus : ${status}, NewSatus : ${newState}")

            if(status == BluetoothGatt.GATT_SUCCESS) {
                if(newState == BluetoothProfile.STATE_CONNECTING) {
                    Log.d("Connecting", "Device : ${gatt.device}")
                }

                if (newState == BluetoothProfile.STATE_CONNECTED) {

                    val device = gatt.device

                    connectDeviceCount++

                    if (mDbOpenHelper.DbTargetFind(device.address) != null) {
                        targetDeviceCount++
                    }

                    checkSet[gatt.device.address] = true
                    addLog("Connected Device : " + device.name)


                    connectedDevices.add(gatt)

                    if(gatt.device.address != "B8:27:EB:A6:F0:21") {
                        AppController.instance.mBluetoothGattServer?.cancelConnection(gatt.device)
                        gatt.disconnect()
                    }




                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                    Log.i("myTag", "---- Disconnected ----")

                    val device = gatt.device
                    Log.i("myTestLog", "disconnected Device : " + device.name)

                    //unconnectDeviceCount++

                    gatt.close()

                    addLog("Disconnected Device : " + device.name)



                }

            }
            else {
                gatt.close()
            }

        }

    }





}
