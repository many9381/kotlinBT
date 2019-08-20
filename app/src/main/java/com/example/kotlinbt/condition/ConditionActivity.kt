package com.example.kotlinbt.condition

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinbt.Application.AppController
import com.example.kotlinbt.R

import kotlinx.android.synthetic.main.activity_condition.*

class ConditionActivity : AppCompatActivity() {

    //lateinit var targetSetDialog: DialogTargetList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)

        targetDeviceSet.isChecked = AppController.connectInfo.getBoolean("target_check", false)

        pinPwdSet.isChecked = AppController.connectInfo.getBoolean("pin_check", false)

        val tmp: String = AppController.connectInfo.getString("countNum", "")

        if(tmp == null || tmp.equals("")) {
            deviceCountNum.text = "0"
        }
        else {
            deviceCountNum.text = tmp
        }

        plusBtn.setOnClickListener{setPlustCount()}
        minusBtn.setOnClickListener{setMinusCount()}
    }

    private fun setMinusCount() {

        var num: Int = deviceCountNum.text.toString().toInt()

        if(num < 1) {
            Toast.makeText(applicationContext, "0 이하 불가", Toast.LENGTH_SHORT).show()
        }
        else {
            deviceCountNum.text = (--num).toString()

            AppController.editor.putString("countNum", num.toString())
            AppController.editor.commit()
        }

    }

   private fun setPlustCount() {

       var num: Int = deviceCountNum.text.toString().toInt()
       var max_co: Int = AppController.instance.mDbOpenHelper.DbMainSelect().size

       if(num >= max_co) {
           Toast.makeText(applicationContext, "${max_co} 초과 불가", Toast.LENGTH_SHORT).show()
       }
       else {
           deviceCountNum.text = (++num).toString()

           AppController.editor.putString("countNum", num.toString())
           AppController.editor.commit()
       }

   }

}
