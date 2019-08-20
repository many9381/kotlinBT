package com.example.kotlinbt.lock

import android.bluetooth.*
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
import com.example.kotlinbt.R
import com.example.kotlinbt.bluetooth.BluetoothLeService
import com.example.kotlinbt.database.DbOpenHelper
import com.example.kotlinbt.database.ItemData
import kotlinx.android.synthetic.main.activity_lock.*
import kotlin.concurrent.thread

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

    lateinit var moduleList: ArrayList<ItemData>

    private var mBluetoothLeService: BluetoothLeService? = null

    private val connectedDevices= mutableListOf<BluetoothGatt>()


    val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
    val ACTION_GATT_CONNECTING = "com.example.bluetooth.le.ACTION_GATT_CONNECTING"
    val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        var bluetoothManager: BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        mBluetoothAdapter = bluetoothManager.adapter
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

        var addLogText: TextView = TextView(this@LockActivity)
        addLogText.text = text
        addLogText.textSize = 12F
        logArea.addView(addLogText)
    }

    val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {

            Log.i("myTestLog", "onServiceConnected")

            mBluetoothLeService = (service as BluetoothLeService.LocalBinder).getService()!!
            if (!mBluetoothLeService!!.initialize()) {
                //                Log.e(TAG, "Unable to initialize Bluetooth");
                //                finish();
            }


            /**
             * 연결된 기기 갯수 파악
             */


            for (i in moduleList.indices) {


                Log.i("myTestLog", "counting attempt : " + moduleList[i].identNum)

                connectBle(moduleList[i].identNum)

            }

            Log.i("myTag", "count_check : $countCheck")


            // Automatically connects to the device upon successful start-up initialization.
            //            mBluetoothLeService.connect(mDeviceAddress);
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
            Log.i("myTag", "application onServiceDisconnected")
        }
    }

    fun connectBle(mDeviceAddress: String) : Boolean
    {


        Log.i("myTestLog", "> counting attempt : " + mDeviceAddress)


        if (mBluetoothAdapter == null || mDeviceAddress == null) {
            Log.i("myTestLog", "BluetoothAdapter not initialized or unspecified address.")
            return false
        }

        val device = mBluetoothAdapter.getRemoteDevice(mDeviceAddress)

        if (device == null) {
            Log.i("myTestLog", " >> Device not found.  Unable to connect.")
            return false
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        device.connectGatt(this, false, mGattCallback)
        Log.i("myTestLog", " >><<")


        return false
    }


    override fun onDestroy() {
        super.onDestroy()
        isRunning = false


        Log.d("lock_destroy", "connected device : " + connectedDevices.size )

        for(device in connectedDevices) {
            //device.disconnect()
            val handler = Handler()

            handler.postDelayed({
                device.disconnect()
                Log.d("Lock_Destroy", "disconnect !! : " + device.device.name)
            } , 400)


        }


        unbindService(mServiceConnection)
        unregisterReceiver(mGattUpdateReceiver)
    }

    override fun onResume() {
        super.onResume()
        var addLogText: TextView = TextView(this@LockActivity)
        addLogText.text = ""


        val gattServiceIntent = Intent(applicationContext, BluetoothLeService::class.java)
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())

    }

    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val intentAction: String

            Log.i("myTestLog", " >>>> onConnectionStateChange")

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED

                val device = gatt.device


                connectDeviceCount++

                if (mDbOpenHelper.DbTargetFind(device.address) != null) {
                    targetDeviceCount++
                }

                addLog("Connected Device : " + device.name)

                connectedDevices.add(gatt)

                // disconnect all devices !! @TODO
                // gatt.disconnect()

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED
                Log.i("myTag", "---- Disconnected ----")

                val device = gatt.device
                Log.i("myTestLog", "disconnected Device : " + device.name)

                unconnectDeviceCount++


                addLog("Disconnected Device : " + device.name)


            } else {
                Log.i("myTestLog", "newState : $newState")
            }
        }
    }

    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {

                Log.i("myTestLog", " >>> BroadcastReceiver 연결성공")

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.i("myTestLog", " >>> BroadcastReceiver 연결 해제")

            }
        }
    }

    private fun makeGattUpdateIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()

        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTING)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED_CAROUSEL)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED_OTA)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECT_OTA)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED_OTA)
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICE_DISCOVERY_UNSUCCESSFUL)
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_ERROR)
        intentFilter.addAction(BluetoothLeService.ACTION_WRITE_SUCCESS)
        intentFilter.addAction(BluetoothLeService.ACTION_WRITE_FAILED)
        intentFilter.addAction(BluetoothLeService.ACTION_PAIR_REQUEST)
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.EXTRA_BOND_STATE)
        intentFilter.addAction(BluetoothLeService.ACTION_WRITE_COMPLETED)
        return intentFilter
    }

}
