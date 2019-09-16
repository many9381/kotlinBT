package com.example.kotlinbt.main.presenter

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothDevice.BOND_BONDING
import android.bluetooth.BluetoothDevice.BOND_NONE
import android.content.Context
import com.example.kotlinbt.R
import com.example.kotlinbt.database.ItemData
import kotlinx.android.synthetic.main.main_list_item.view.*

/**
 * Created by kyounghyun on 2017. 1. 15..
 */

class MainAdapter(var itemDatas: ArrayList<ItemData> , val context: Context, val btnlistener: BtnClickListener) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    open interface BtnClickListener {
        fun deleteModule(position: Int)
    }

    companion object {
        var onRemoveClickListener: BtnClickListener? = null
    }



    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val idText = itemView.idNum
        val nameText = itemView.moduleName
        val numText = itemView.moduleNum
        val statusText = itemView.moduleStatus
        val removeBtn = itemView.removeBtn


        init {
            onRemoveClickListener = btnlistener
            removeBtn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if(onRemoveClickListener != null) {
                        onRemoveClickListener?.deleteModule(position)
                    }
                }
            })
            //removeBtn.setOnClickListener(remove())
        }

        fun bind(itemData: ItemData) {

            idText?.text = (itemData.Id + 1).toString()
            nameText?.text = itemData.identName
            numText?.text = itemData.identNum
            statusText?.text = itemData.status
        }

        private fun remove(): (View) -> Unit = {
            layoutPosition.also { currentPosition ->


                itemDatas.removeAt(currentPosition)

                notifyDataSetChanged()
            }
        }
    }

    fun renewDatas(itemDatas: ArrayList<ItemData>) {
        this.itemDatas = itemDatas
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, null)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.bind(itemDatas[position])
    }

    override fun getItemCount(): Int {
        if (itemDatas == null) {
            return 0
        }

        return itemDatas!!.size

        // Add extra view to show the footer view
    }

    fun setOnlineCheck(device: BluetoothDevice) {

        Log.i("myTag", ">>> BOND_BONDED : $BOND_BONDED")
        Log.i("myTag", ">>> BOND_BONDING : $BOND_BONDING")
        Log.i("myTag", ">>> BOND_NONE : $BOND_NONE")
        Log.i("myTag", ">>> check : " + device.bondState)

        var index = 0


        for (i in itemDatas.indices) {
            if (itemDatas[i].identNum.equals(device.address)) {
                index = i
            }
        }

        if (device.bondState == BOND_BONDED) {
            itemDatas[index].status = "online"
            notifyDataSetChanged()

        } else if (device.bondState == BOND_BONDING) {
            itemDatas[index].status = "online"
            notifyDataSetChanged()
        } else {
            itemDatas[index].status = "offline"
            notifyDataSetChanged()

        }

    }


    fun setOnline(address: String) {
        var index = 0
        for (i in itemDatas.indices) {
            if (itemDatas[i].identNum.equals(address)) {
                index = i
            }
        }

        itemDatas[index].status = "online"
        notifyDataSetChanged()

    }

    fun setOffline() {

        for (i in itemDatas.indices) {
            if (itemDatas[i].status.equals("checking")) {
                itemDatas[i].status = "offline"
                notifyDataSetChanged()
            }

        }
    }
}
