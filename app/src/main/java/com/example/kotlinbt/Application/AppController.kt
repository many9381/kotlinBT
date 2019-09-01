package com.example.kotlinbt.Application

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.SharedPreferences
import com.example.kotlinbt.database.DbOpenHelper
import java.sql.SQLException

class AppController : Application() {


    lateinit var mDbOpenHelper: DbOpenHelper
    lateinit var checkedBLE: ArrayList<BluetoothDevice>


    override fun onCreate() {
        super.onCreate()

        AppController.instance = this
        this.buildDB()

        connectInfo = getSharedPreferences("iot_info", 0)
        editor = connectInfo.edit()
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