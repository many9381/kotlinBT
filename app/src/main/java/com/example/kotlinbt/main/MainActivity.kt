package com.example.kotlinbt.main

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.*
import android.content.Context
import android.content.Intent
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
import java.util.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothManager: BluetoothManager? = null

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

    private val mBluetoothLeScanner: BluetoothLeScanner
        get() {
            val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            mBluetoothManager = bluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            mBluetoothAdapter = bluetoothAdapter
            return bluetoothAdapter.bluetoothLeScanner
        }



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
                        val device = mBluetoothAdapter!!.getRemoteDevice(address)
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
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    Log.d("BTapp_MainActivity", "BT Scan Permission Request")

                } else {

                    // No explanation needed, we can request the permission.

                    var manifast: Array<String> = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN)

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
            reScanBtn.visibility = View.INVISIBLE
        }

    }

    override fun onResume() {
        super.onResume()

        //checkedDevices = DeviceObject()

        //recyclerview add item
        itemDatas = mDbOpenHelper.DbMainSelect()
        mAdapter.renewDatas(itemDatas)

        scanLeDevice(true)


    }

    override fun onPause() {
        super.onPause()
        scanLeDevice(false)
    }

    override fun onStop() {
        super.onStop()
        scanLeDevice(false)
    }

    // onClick Functions
    override fun onClick(v: View) {
        when(v.id) {
            R.id.deviceRegisterArea -> {
                scanLeDevice(false)
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
                mBluetoothLeScanner.stopScan(leScanCallback)
                invalidateOptionsMenu()

                reScanBtn.visibility = View.VISIBLE
            }, SCAN_PERIOD)

            val mScanSettingBuilder = ScanSettings.Builder()
            val filters : List<ScanFilter> = ArrayList<ScanFilter>()
            val scanSettings = mScanSettingBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(0).build()


            mScanning = true
            mBluetoothLeScanner.startScan(filters, scanSettings, leScanCallback)
        } else {
            mScanning = false
            mBluetoothLeScanner.stopScan(leScanCallback)
        }
        invalidateOptionsMenu()
    }




    // Device scan callback.
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            if (mDbOpenHelper.DbFind(device.address) != null) {

                Log.d("LeScanCallback", "UUID : " + device.name)
                Log.d("LeScanCallback", "ADDRESS : " + device.address)
                Log.d("LeScanCallback", "BONDSTATE : " + device.bondState)

                if (device.bondState == BluetoothDevice.BOND_BONDING || device.bondState == BluetoothDevice.BOND_BONDED) {
                    mAdapter.setOnline(device.address)

                } else if (device.bondState == BluetoothDevice.BOND_NONE) {
                    if(pairDevice(device)) {

                        mAdapter.setOnlineCheck(device)
                    }
                }

            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)

            Log.d("test", "onBatchScanResults!!")
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)

            Log.d("LeScan Failed", "Err Code : $errorCode")
        }
    }



    /*{ device, rssi, scanRecord ->
        runOnUiThread {

            if (mDbOpenHelper.DbFind(device.address) != null) {
                //if (mDbOpenHelper.DbFind(device.address) == null) {

                //Log.i("mLeScanCallback", "status : " + device.bondState)

                Log.d("LeScanCallback", "UUID : " + device.name)
                Log.d("LeScanCallback", "ADDRESS : " + device.address)
                Log.d("LeScanCallback", "BONDSTATE : " + device.bondState)


                if (device.bondState == BluetoothDevice.BOND_BONDING || device.bondState == BluetoothDevice.BOND_BONDED) {
                    mAdapter.setOnline(device.address)
                    //checkedDevices.pairedDeviceObject.add(device)

                    /*
                    var flag = true
                    for(dev in AppController.instance.checkedDevice) {
                            if(dev.address == device.address) {
                                flag = false
                                break
                            }
                    }
                    if(flag) {
                        AppController.instance.checkedDevice.add(device)
                    }
                     */


                } else if (device.bondState == BluetoothDevice.BOND_NONE) {
                    if(pairDevice(device)) {
                        //checkedDevices.pairedDeviceObject.add(device)
                        /*
                        var flag = true
                        for(dev in AppController.instance.checkedDevice) {
                            if(dev.address == device.address) {
                                flag = false
                                break
                            }
                        }
                        if(flag) {
                            AppController.instance.checkedDevice.add(device)
                        }

                         */
                        mAdapter.setOnlineCheck(device)
                    }
                }

            }


        }

    }

     */


    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }


    private fun unpairDevice(device: BluetoothDevice) {
        device::class.java.getMethod("removeBond").invoke(device)

    }

    private fun pairDevice(device: BluetoothDevice) : Boolean {
        //Log.i("MainActivity-Pairing", "name : " + device.getName().toString())
        //Log.i("MainActivity-Pairing", "address : " + device.getAddress().toString())

        val bondresult = device.createBond()

        Log.i("MainActivity-Pairing", "Bondresult : " + bondresult)
        return bondresult

    }

    override fun onDestroy() {
        mDbOpenHelper.close()
        super.onDestroy()
    }

}
