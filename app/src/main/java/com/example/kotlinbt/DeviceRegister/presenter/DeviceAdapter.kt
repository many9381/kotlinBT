package com.example.kotlinbt.DeviceRegister.presenter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kotlinbt.R

import java.util.ArrayList

/**
 * Created by kh on 2016. 10. 19..
 */
class DeviceAdapter//생성자
    (ctx: Context) : BaseAdapter() {


    private val mLeDevices: ArrayList<BluetoothDevice>
    private val mInflator: LayoutInflater


    init {
        mLeDevices = ArrayList()
        mInflator = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    fun addDevice(device: BluetoothDevice) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device)
        }
    }

    fun getDevice(position: Int): BluetoothDevice {
        return mLeDevices[position]
    }

    fun clear() {
        mLeDevices.clear()
    }

    override fun getCount(): Int {
        return mLeDevices.size
    }

    override fun getItem(i: Int): Any {
        return mLeDevices[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: ViewHolder
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.ble_search_list_item, null)
            viewHolder = ViewHolder()
            viewHolder.deviceAddress = view!!.findViewById(R.id.device_address) as TextView
            viewHolder.deviceName = view.findViewById(R.id.device_name) as TextView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val device = mLeDevices[i]
        val deviceName = device.name
        if (deviceName != null && deviceName.length > 0)
            viewHolder.deviceName!!.text = deviceName
        else
            viewHolder.deviceName!!.text = "UnKnown"
        viewHolder.deviceAddress!!.text = device.address

        return view
    }

    internal class ViewHolder {
        var deviceName: TextView? = null
        var deviceAddress: TextView? = null
    }
}
