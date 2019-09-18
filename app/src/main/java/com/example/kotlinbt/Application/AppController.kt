package com.example.kotlinbt.Application

import android.app.Application
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.content.SharedPreferences
import com.example.kotlinbt.database.DbOpenHelper
import java.sql.SQLException

class AppController : Application() {


    lateinit var mDbOpenHelper: DbOpenHelper
    lateinit var checkedBLE: ArrayList<BluetoothDevice>


    lateinit var mBluetoothAdapter: BluetoothAdapter
    lateinit var mBluetoothManager: BluetoothManager
    lateinit var mBluetoothLeScanner: BluetoothLeScanner
    lateinit var requestedGATT: BluetoothGatt
    var mBluetoothGattServer : BluetoothGattServer? = null



    var requestedDevice : BluetoothDevice? = null

    override fun onCreate() {
        super.onCreate()

        AppController.instance = this
        this.buildDB()

        connectInfo = getSharedPreferences("iot_info", 0)
        editor = connectInfo.edit()


        mBluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        mBluetoothLeScanner = mBluetoothAdapter.bluetoothLeScanner


    }


    fun buildDB() {
        mDbOpenHelper = DbOpenHelper(this)
        try {
            mDbOpenHelper.open()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    // sharing instance
    companion object {

        lateinit var instance: AppController
        lateinit var connectInfo: SharedPreferences
        lateinit var editor: SharedPreferences.Editor

        // Make DB

    }


}