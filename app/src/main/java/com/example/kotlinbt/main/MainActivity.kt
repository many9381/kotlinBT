package com.example.kotlinbt.main

import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.*
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.os.Build
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateFormat
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kotlinbt.Application.AppController
import com.example.kotlinbt.DeviceRegister.DeviceSearchActivity
import com.example.kotlinbt.R
import com.example.kotlinbt.condition.ConditionActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.example.kotlinbt.database.DbOpenHelper
import com.example.kotlinbt.database.ItemData
import com.example.kotlinbt.lock.LockActivity
import com.example.kotlinbt.main.presenter.MainAdapter

import com.example.kotlinbt.main.advertisement.AdvertiseService
import com.example.kotlinbt.main.advertisement.TimeProfile
import java.util.*
import android.content.Intent
import kotlinx.android.synthetic.main.main_list_item.*
import kotlinx.coroutines.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    /*
    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private lateinit var mBluetoothManager: BluetoothManager
    private lateinit var mBluetoothLeScanner: BluetoothLeScanner

     */

    private val REQUEST_LOCATION_PERMISSION = 2018
    private val SCAN_PERIOD: Long = 10000


    lateinit var mLayoutManager: LinearLayoutManager
    var itemDatas: ArrayList<ItemData> = ArrayList<ItemData>()
    lateinit var mAdapter: MainAdapter

    lateinit var mHandler: Handler
    private var mScanning: Boolean = false

    lateinit var mDbOpenHelper: DbOpenHelper

    // Check press Back Button Two times
    private val FINSH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0


    //private var mBluetoothGattServer : BluetoothGattServer? = null
    private val registeredDevices = mutableSetOf<BluetoothDevice>()

    private val TAG = "ADVERTISING"

    private var scanSettings: ScanSettings? = null
    private var mScanSettingBuilder: ScanSettings.Builder? = null
    val filters : List<ScanFilter> = ArrayList<ScanFilter>()

    var flag : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbOpenHelper = AppController.instance.mDbOpenHelper

        // Recyclerview Remove Button Onclick function
        val obj = object : MainAdapter.BtnClickListener {
            override fun deleteModule(position: Int) {

                val builder = AlertDialog.Builder(this@MainActivity)


                builder.setMessage("삭제하시겠습니까?")
                    .setCancelable(true)
                    .setPositiveButton("확인") { dialog, whichButton ->
                        // 확인 버튼 클릭시 설정



                        val address = itemDatas[position].identNum
                        val device = AppController.instance.mBluetoothAdapter.getRemoteDevice(address)
                        unpairDevice(device)


                        mDbOpenHelper.DbDelete(itemDatas[position].Id.toString())
                        itemDatas.removeAt(position)


                        mAdapter.notifyDataSetChanged()


                        Toast.makeText(applicationContext, "삭제 완료", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("취소") { dialog, whichButton ->
                        // 취소 버튼 클릭시 설정
                        dialog.cancel()
                    }

                val dialog = builder.create()    // 알림창 객체 생성
                dialog.show()


            }
        }


        //mBluetoothAdapter.cancelDiscovery()


        mAdapter = MainAdapter(itemDatas, this, obj)
        mainList.adapter = mAdapter

        // RecyclerView Layout setting
        mainList.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL

        mainList.layoutManager = mLayoutManager
        mainList.overScrollMode = View.OVER_SCROLL_NEVER


        mHandler = Handler()


        // BT Permission Request
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?

                //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 20)

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    Log.d("BTapp_MainActivity", "BT Scan Permission Request")

                } else {

                    // No explanation needed, we can request the permission.

                    var manifast: Array<String> = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH)

                    requestPermissions(manifast, REQUEST_LOCATION_PERMISSION);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }

            //var manifast: Array<String> = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            //requestPermissions(manifast, REQUEST_LOCATION_PERMISSION)

            Log.d("BTapp_MainActivity", "BT Scan Permission Request")
        }
        //mBluetoothAdapter.startDiscovery()


        Log.d("oncreate", "${AppController.instance.mBluetoothAdapter.scanMode}")


        mScanSettingBuilder = ScanSettings.Builder()
        //val scanSettings = mScanSettingBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(10).setLegacy(false).setPhy(ScanSettings.PHY_LE_ALL_SUPPORTED).build()
        mScanSettingBuilder!!.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        mScanSettingBuilder!!.setReportDelay(0)
        //mScanSettingBuilder.set
        scanSettings = mScanSettingBuilder!!.build()



        //val bluetoothManager: BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        /*
        bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager?.adapter
        mbluetoothLeScanner = mBluetoothAdapter?.bluetoothLeScanner

         */

        // onClick Listener
        deviceRegisterArea.setOnClickListener(this)
        conditionRegisterArea.setOnClickListener(this)
        unlockArea.setOnClickListener(this)


        reScanBtn.setOnClickListener {
            scanLeDevice(true)
            flag = true
            reScanBtn.visibility = View.INVISIBLE
        }



    }

    private fun startServer() {
        //mBluetoothGattServer = mBluetoothManager.openGattServer(this, gattServerCallback)
        if(AppController.instance.mBluetoothGattServer == null) {
            AppController.instance.mBluetoothGattServer = AppController.instance.mBluetoothManager.openGattServer(this, gattServerCallback)
        }

        if(AppController.instance.mBluetoothGattServer?.services.isNullOrEmpty()) {
            AppController.instance.mBluetoothGattServer?.addService(TimeProfile.createTimeService())
        }
        //?: Log.w(TAG, "Unable to create GATT server")
        Log.d("TAG", "GATT server started")

        // Initialize the local UI
        updateLocalUi(System.currentTimeMillis())


    }

    private fun updateLocalUi(timestamp: Long) {
        val date = Date(timestamp)
        val displayDate = DateFormat.getMediumDateFormat(this).format(date)
        val displayTime = DateFormat.getTimeFormat(this).format(date)
    }

    private fun stopServer() {
        AppController.instance.mBluetoothGattServer?.close()
        AppController.instance.mBluetoothGattServer = null
        Log.d("TAG", "GATT server finished")
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        //filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        registerReceiver(mPairingRequestReceiver, filter)

        //recyclerview add item
        itemDatas = mDbOpenHelper.DbMainSelect()
        mAdapter.renewDatas(itemDatas)
        AppController.instance.checkedBLE = ArrayList<BluetoothDevice>()

        AppController.instance.mBluetoothAdapter.startDiscovery()
        startServer()

        //val testset = AppController.instance.checkedBLE

        Log.d("Main_OnResume" , "APPCONTROLLER : ${AppController.instance.requestedDevice}")
        //checkAuth()


        scanLeDevice(true)

        val intent = Intent(this, AdvertiseService::class.java)
        //startService(intent)
        startForegroundService(intent)


    }

    fun checkAuth() {

        if(AppController.instance.requestedDevice != null) {
            runBlocking {
                AppController.instance.mBluetoothGattServer?.cancelConnection(AppController.instance.requestedDevice)
                delay(100)
            }

            while (true) {
                if(AppController.instance.mBluetoothManager.getConnectionState(AppController.instance.requestedDevice, BluetoothProfile.GATT_SERVER) == BluetoothProfile.STATE_CONNECTED) {

                    val ret = AppController.instance.mBluetoothGattServer?.cancelConnection(AppController.instance.requestedDevice)
                }
                else {
                    break
                }
            }

        }

    }

    override fun onPause() {
        super.onPause()
        scanLeDevice(false)

    }

    override fun onStop() {
        super.onStop()
        scanLeDevice(false)


        AppController.instance.mBluetoothAdapter.cancelDiscovery()
        val intent = Intent(this, AdvertiseService::class.java)
        stopService(intent)


    }

    // onClick Functions
    override fun onClick(v: View) {
        when(v.id) {
            R.id.deviceRegisterArea -> {
                scanLeDevice(false)
                stopServer()
                val intent = Intent(this, DeviceSearchActivity::class.java)
                startActivity(intent)
            }
            R.id.conditionRegisterArea -> {
                scanLeDevice(false)
                val intent: Intent = Intent(this, ConditionActivity::class.java)
                startActivity(intent)
            }
            R.id.unlockArea -> {
                scanLeDevice(false)
                val intent: Intent = Intent(this, LockActivity::class.java)
                startActivity(intent)
            }

        }

    }

    // BackPress Function
    override fun onBackPressed() {


        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        /**
         * Back키 두번 연속 클릭 시 앱 종료
         */

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "뒤로 가기 키을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }


    // BT Adapter check
    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled



    private fun onPermissionsGranted() {
        // Do nothing in particular...
        //@TODO("need granted checker")
        //btn_pill_button.isEnabled = true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onPermissionsGranted()
                } else {
                    // Permission denied :-( we should deal with this as per Google's docs. in this case, we probably
                    // just want to disable the button and close the
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }


    fun scanLeDevice(enable: Boolean) {

        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed({
                mScanning = false
                AppController.instance.mBluetoothLeScanner.stopScan(leScanCallback)
                invalidateOptionsMenu()

                reScanBtn.visibility = View.VISIBLE
            }, SCAN_PERIOD)





            mScanning = true
            AppController.instance.mBluetoothLeScanner.startScan(filters, scanSettings, leScanCallback)
        } else {
            mScanning = false
            AppController.instance.mBluetoothLeScanner.stopScan(leScanCallback)
            runBlocking {
                delay(360)
            }
        }
        invalidateOptionsMenu()
    }




    // Device scan callback.
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            if (mDbOpenHelper.DbFind(device.address) != null) {

                if (device.bondState == BluetoothDevice.BOND_BONDING || device.bondState == BluetoothDevice.BOND_BONDED) {
                    mAdapter.setOnline(device.address)
                    if(!AppController.instance.checkedBLE.any{it -> it.address == device.address}) {
                        AppController.instance.checkedBLE.add(device)
                    }

                } else if (device.bondState == BluetoothDevice.BOND_NONE) {

                    if(pairDevice(device)) {

                        /*
                        if(device.bondState == BluetoothDevice.BOND_BONDED || device.bondState == BluetoothDevice.BOND_BONDING) {
                            mAdapter.setOnlineCheck(device)
                            if(!AppController.instance.checkedBLE.any{it -> it.address == device.address}) {
                                AppController.instance.checkedBLE.add(device)
                            }
                        }
                         */
                        Log.d("LeScanCallback", "UUID : " + device.name)
                        Log.d("LeScanCallback", "ADDRESS : " + device.address)
                        Log.d("LeScanCallback", "BONDSTATE : " + device.bondState)

                    }

                }

            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)



            results!!.forEach {



                val device = it.device
                Log.d("onBatchScanResult", "device : ${device.name}")

                if (mDbOpenHelper.DbFind(device.address) != null) {

                    if (device.bondState == BluetoothDevice.BOND_BONDING || device.bondState == BluetoothDevice.BOND_BONDED) {
                        mAdapter.setOnline(device.address)
                        if(!AppController.instance.checkedBLE.any{it -> it.address == device.address}) {
                            AppController.instance.checkedBLE.add(device)
                        }

                    } else if (device.bondState == BluetoothDevice.BOND_NONE) {

                        if(pairDevice(device)) {

                            /*
                            if(device.bondState == BluetoothDevice.BOND_BONDED || device.bondState == BluetoothDevice.BOND_BONDING) {
                                mAdapter.setOnlineCheck(device)
                                if(!AppController.instance.checkedBLE.any{it -> it.address == device.address}) {
                                    AppController.instance.checkedBLE.add(device)
                                }
                            }
                             */
                            Log.d("LeScanCallback", "UUID : " + device.name)
                            Log.d("LeScanCallback", "ADDRESS : " + device.address)
                            Log.d("LeScanCallback", "BONDSTATE : " + device.bondState)

                        }

                    }
                }

            }

        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)

            Log.d("LeScan Failed", "Err Code : $errorCode")
        }
    }


    private fun unpairDevice(device: BluetoothDevice) {
        device::class.java.getMethod("removeBond").invoke(device)

    }

    private fun pairDevice(device: BluetoothDevice) : Boolean {
        //Log.i("MainActivity-Pairing", "name : " + device.getName().toString())
        //Log.i("MainActivity-Pairing", "address : " + device.getAddress().toString())


       /*
       val mGatt = device.connectGatt(this, false, gattCallback, BluetoothDevice.TRANSPORT_LE)

        mGatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH)
         */
        val bondresult = device.createBond()

        Log.i("MainActivity-Pairing", "Bondresult : " + bondresult)


        return bondresult

    }


    private val pairedDevices= mutableListOf<BluetoothGatt>()


    override fun onDestroy() {
        super.onDestroy()
        stopServer()
        AppController.instance.mBluetoothAdapter.cancelDiscovery()
        unregisterReceiver(mPairingRequestReceiver)
        mDbOpenHelper.close()

    }



    //@TODO check RequeistReceiver
    private val mPairingRequestReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            Log.i("mPairingRequestReceiver", "OnReceive : " + action)

            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

            when(action) {
                BluetoothDevice.ACTION_PAIRING_REQUEST -> {
                    try {

                        //the pin in case you need to accept for an specific pin

                        val pin =
                            intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0)

                        val pinByte: ByteArray = ("" + pin).toByteArray(Charsets.UTF_8)
                        device.setPin(pinByte)

                        abortBroadcast()

                    } catch (e: Exception) {
                        Log.i("myTag", "Error occurs when trying to auto pair")
                        e.printStackTrace()
                    }

                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)
                    val prev_state = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR)


                    if((state == BluetoothDevice.BOND_BONDED && prev_state == BluetoothDevice.BOND_BONDING) || state == BluetoothDevice.BOND_BONDED) {
                        Log.d("PairingRequest", "Paired")
                        //AppController.instance.mBluetoothGattServer?.connect(device,false)
                        device.connectGatt(context, false,  mGattCallback, BluetoothDevice.TRANSPORT_LE)
                        val inputData = ItemData(0, device.name, device.address, 0)
                        mDbOpenHelper.DbInsert(inputData)

                    }
                    else if (state == BluetoothDevice.BOND_NONE && prev_state == BluetoothDevice.BOND_BONDED){
                        Log.d("PairingRequest", "unPaired")
                    }
                    else {
                        Log.d("PairingRequest", "${state}")
                    }

                }
                BluetoothDevice.ACTION_ACL_CONNECTED -> {

                    Log.i("mPairingRequestReceiver", "Connected : ${device.name}")

                    val mhandler: Handler = Handler()
                    mhandler.postDelayed({
                        AppController.instance.mBluetoothGattServer?.cancelConnection(device)

                    }, 1500)


                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {

                    Log.i("mPairingRequestReceiver", "DisConnected : ${device.name}")
                }

            }


        }
    }

    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {


            Log.i("mGattCallback", "OnStatus : ${status}, NewSatus : ${newState}")

            if(status == BluetoothGatt.GATT_SUCCESS) {
                if(newState == BluetoothProfile.STATE_CONNECTING) {
                    Log.d("Connecting", "Device : ${gatt.device}")
                }
                else if (newState == BluetoothProfile.STATE_CONNECTED) {

                    val device = gatt.device
                    AppController.instance.mBluetoothGattServer?.cancelConnection(device)
                    gatt.disconnect()



                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.i("myTag", "---- Disconnected ----")

                    val device = gatt.device
                    Log.i("myTestLog", "disconnected Device : " + device.name)

                    gatt.close()

                } else {
                    Log.i("myTestLog", "newState : $newState")
                }

            }
            else {
                gatt.close()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 11) {
            if(resultCode ==  Activity.RESULT_OK) {

                Log.d("onActivityResult", "RESULT_OK")

            }
        }


    }


    private val gattServerCallback = object : BluetoothGattServerCallback() {

        override fun onConnectionStateChange(device: BluetoothDevice, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "BluetoothDevice CONNECTED: ${device.name}")

                if(device.address.equals("B8:27:EB:A6:F0:21") && flag) {

                    val i = Intent(applicationContext, LockActivity::class.java)
                    //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    AppController.instance.requestedDevice = device
                    //mBluetoothGattServer!!.connect(device, false)
                    flag = false


                    Log.d("test", "tttttttttttttttttttttttttttttttttttttttt")
                    AppController.instance.mBluetoothGattServer?.connect(AppController.instance.requestedDevice, false)
                    stopServer()



                    startActivityForResult(i, 11)
                }
                else {
                    AppController.instance.mBluetoothGattServer?.cancelConnection(device)
                }


            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "BluetoothDevice DISCONNECTED: $device")

                //Remove device from any active subscriptions
                registeredDevices.remove(device)

            }
            else if(newState == BluetoothProfile.STATE_CONNECTING) {
                Log.i(TAG, "BluetoothDevice CONNECTTING: ${device.name}")


            }
        }

        override fun onCharacteristicReadRequest(device: BluetoothDevice, requestId: Int, offset: Int, characteristic: BluetoothGattCharacteristic) {
            val now = System.currentTimeMillis()
            when {
                TimeProfile.CURRENT_TIME == characteristic.uuid -> {
                    Log.i(TAG, "Read CurrentTime")
                    AppController.instance.mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, TimeProfile.getExactTime(now, TimeProfile.ADJUST_NONE))
                }
                TimeProfile.LOCAL_TIME_INFO == characteristic.uuid -> {
                    Log.i(TAG, "Read LocalTimeInfo")
                    AppController.instance.mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, TimeProfile.getLocalTimeInfo(now))
                }
                else -> {
                    // Invalid characteristic
                    Log.w(TAG, "Invalid Characteristic Read: " + characteristic.uuid)
                    AppController.instance.mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, 0, null)
                }
            }
        }

        override fun onDescriptorReadRequest(device: BluetoothDevice, requestId: Int, offset: Int,
                                             descriptor: BluetoothGattDescriptor) {
            if (TimeProfile.CLIENT_CONFIG == descriptor.uuid) {
                Log.d(TAG, "Config descriptor read")
                val returnValue = if (registeredDevices.contains(device)) {
                    BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                } else {
                    BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                }
                AppController.instance.mBluetoothGattServer?.sendResponse(device,
                    requestId,
                    BluetoothGatt.GATT_SUCCESS,
                    0,
                    returnValue)
            } else {
                Log.w(TAG, "Unknown descriptor read request")
                AppController.instance.mBluetoothGattServer?.sendResponse(device,
                    requestId,
                    BluetoothGatt.GATT_FAILURE,
                    0, null)
            }
        }

        override fun onDescriptorWriteRequest(device: BluetoothDevice, requestId: Int,
                                              descriptor: BluetoothGattDescriptor,
                                              preparedWrite: Boolean, responseNeeded: Boolean,
                                              offset: Int, value: ByteArray) {
            if (TimeProfile.CLIENT_CONFIG == descriptor.uuid) {
                if (Arrays.equals(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE, value)) {
                    Log.d(TAG, "Subscribe device to notifications: $device")
                    registeredDevices.add(device)
                } else if (Arrays.equals(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE, value)) {
                    Log.d(TAG, "Unsubscribe device from notifications: $device")
                    registeredDevices.remove(device)
                }

                if (responseNeeded) {
                    AppController.instance.mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, null)
                }
            } else {
                Log.w(TAG, "Unknown descriptor write request")
                if (responseNeeded) {
                    AppController.instance.mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, 0, null)
                }
            }
        }
    }
}
