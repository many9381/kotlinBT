package com.example.kotlinbt.DeviceRegister

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.*
import android.bluetooth.BluetoothDevice.*
import android.bluetooth.le.*
import android.content.*
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.util.Log.i
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.kotlinbt.Application.AppController
import com.example.kotlinbt.R
import com.example.kotlinbt.database.DbOpenHelper
import com.example.kotlinbt.DeviceRegister.presenter.DeviceAdapter
import com.example.kotlinbt.database.ItemData
import kotlinx.android.synthetic.main.activity_device_search.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.experimental.and
import android.bluetooth.BluetoothDevice
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class DeviceSearchActivity : AppCompatActivity() {

    lateinit var mDbOpenHelper: DbOpenHelper

    private var mScanning: Boolean = false
    lateinit var mHandler: Handler

    private lateinit var mLedeviceAdapter: DeviceAdapter
    /*
    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private lateinit var mBluetoothManager: BluetoothManager
    private lateinit var mBluetoothLeScanner: BluetoothLeScanner

     */

    private val SCAN_PERIOD: Long = 10000

    private val pairedDevices= mutableListOf<BluetoothGatt>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_search)

        supportActionBar!!.setDisplayShowHomeEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        // ActionBar의 배경색 변경


        //getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(-0xc0ae4b))

        supportActionBar!!.elevation = 0F

        val mInflater: LayoutInflater = LayoutInflater.from(this)
        val mCustomView: View = mInflater.inflate(R.layout.actionbar_layout, null)

        supportActionBar!!.customView = mCustomView
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        // --------------------------------------
        // Actionbar setting

        mHandler = Handler()
        // handler setting


        // check BLE setting
        if (!packageManager!!.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show()
            finish()
        }

        //get DBinstance from AppController
        mDbOpenHelper = AppController.instance.mDbOpenHelper

    }


    // Option Menu setting
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).isVisible = false
            menu.findItem(R.id.menu_scan).isVisible = true
            menu.findItem(R.id.menu_refresh).actionView = null
        } else {
            menu.findItem(R.id.menu_stop).isVisible = true
            menu.findItem(R.id.menu_scan).isVisible = false
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_indeterminate_progress)
        }
        return true
    }

    // Option Menu Click setting
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_scan -> {
                mLedeviceAdapter.clear()
                //mBluetoothAdapter.startDiscovery()
                scanLeDevice(true)
            }
            R.id.menu_stop -> {
                scanLeDevice(false)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()


        mLedeviceAdapter = DeviceAdapter(applicationContext)
        regi_listview.adapter = mLedeviceAdapter


        regi_listview.setOnItemClickListener { parent, view, position, id ->

            val device = mLedeviceAdapter.getDevice(position)


            if (mDbOpenHelper.DbFind(device.address) != null) {
                Toast.makeText(applicationContext, "이미 등록된 장치입니다.", Toast.LENGTH_SHORT).show()
            } else {


                val inputData = ItemData(0, device.name, device.address, 0)
                registerDevice(inputData)
            }

        }


        scanLeDevice(true)
    }

    override fun onPause() {
        super.onPause()

        scanLeDevice(false)
        mLedeviceAdapter.clear()
    }

    override fun onDestroy() {
        super.onDestroy()

    }


    // register Dialog Callback
    fun registerDevice(inputData: ItemData) {

        val builder = AlertDialog.Builder(this@DeviceSearchActivity)

        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("등록되지 않은 장치입니다.")
            .setMessage("등록하시겠습니까?")
            .setCancelable(true)
            .setPositiveButton("확인") { dialog, whichButton ->
                // 확인 버튼 클릭시 설정
                /**
                 * 연결 시도
                 */

                scanLeDevice(false)

                val address = inputData.identNum
                val device = AppController.instance.mBluetoothAdapter.getRemoteDevice(address)


                if(pairDevice(device)) {

                    Toast.makeText(applicationContext, "등록 완료", Toast.LENGTH_SHORT).show()

                }
                else {
                    Toast.makeText(applicationContext, "페어링 실패 다시시도하세요", Toast.LENGTH_SHORT).show()
                }


            }
            .setNegativeButton("취소") { dialog, whichButton ->
                // 취소 버튼 클릭시 설정
                dialog.cancel()
            }

        val dialog = builder.create()    // 알림창 객체 생성
        dialog.show()
    }



    private val STATE_DISCONNECTED = 0
    private val STATE_CONNECTING = 1
    private val STATE_CONNECTED = 2
    val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
    val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"
    val ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED"
    val ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE"
    val EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA"
    private var connectionState = STATE_DISCONNECTED
    private val gattCallback = object : BluetoothGattCallback()  {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val intentAction: String



            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    intentAction = ACTION_GATT_CONNECTED
                    connectionState = STATE_CONNECTED

                    val inputData = ItemData(0, gatt.device.name, gatt.device.address, 0)
                    mDbOpenHelper.DbInsert(inputData)

                    Log.i("GATT", "Connected to GATT server.")

                    pairedDevices.add(gatt)

                    broadcastUpdate(intentAction)
                    gatt.disconnect()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    intentAction = ACTION_GATT_DISCONNECTED
                    connectionState = STATE_DISCONNECTED
                    Log.i("GATT", "Disconnected from GATT server.")
                    gatt.close()

                    broadcastUpdate(intentAction)
                }

            }

        }

        private fun broadcastUpdate(action: String) {
            val intent = Intent(action)
            sendBroadcast(intent)
        }

    }


    fun scanLeDevice(enable: Boolean) {

        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed({
                mScanning = false
                AppController.instance.mBluetoothLeScanner.stopScan(leScanCallback)
                invalidateOptionsMenu()

                // reScanBtn.visibility = View.VISIBLE
            }, SCAN_PERIOD)

            val mScanSettingBuilder = ScanSettings.Builder()
            val filters : List<ScanFilter> = ArrayList<ScanFilter>()
            mScanSettingBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            mScanSettingBuilder.setReportDelay(0)
            mScanSettingBuilder.setLegacy(false)
            //mScanSettingBuilder.setPhy(ScanSettings.PHY_LE_ALL_SUPPORTED)

            val scanSettings = mScanSettingBuilder.build()


            mScanning = true
            AppController.instance.mBluetoothLeScanner.startScan(filters, scanSettings, leScanCallback)
        } else {
            mScanning = false
            AppController.instance.mBluetoothLeScanner.stopScan(leScanCallback)
        }
        invalidateOptionsMenu()
    }

    // Device scan callback.
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val record = result.scanRecord

            if (mDbOpenHelper.DbFind(device.address) == null && device.name != null) {
                //if (mDbOpenHelper.DbFind(device.address) == null) {
                Log.d("Hex", "UUID : " + device.name)
                Log.d("Address", "ADDRESS " + device.address)

                mLedeviceAdapter.addDevice(device)
                mLedeviceAdapter.notifyDataSetChanged()
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

    // Binary to String
    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

    private fun pairDevice(device: BluetoothDevice) : Boolean {
        Log.i("SearchActivity-Pairing", "name : " + device.getName().toString())
        Log.i("SearchActivity-Pairing", "address : " + device.getAddress().toString())


        val bondresult = device.createBond()


        Log.i("SearchActivity-Pairing", "Bondresult : " + bondresult)
        return bondresult

    }


    private fun unpairDevice(device: BluetoothDevice) {
        device::class.java.getMethod("removeBond").invoke(device)

    }

}
