package com.example.kotlinbt

import android.app.Instrumentation
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var mDevice : UiDevice

    @Before
    fun initialization() {

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        if(!mDevice.isScreenOn) {
            mDevice.wakeUp()
        }

        mDevice.pressHome()

    }


    @Test
    fun useAppContext() {
        // Context of the app under test.


        val mLauncher = mDevice.launcherPackageName
        assertThat(mLauncher, notNullValue())
        mDevice.wait(Until.hasObject(By.pkg(mLauncher)), 3000)




        val context = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.kotlinbt", context.packageName)

        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)!!
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)   // 5

        // Wait for the app to appear
        mDevice.wait(
            Until.hasObject(By.pkg(context.packageName)), 5000
        )

        val mainCheckedList : UiObject2 = mDevice.findObject(By.res("com.example.kotlinbt:id/mainList"))

        //mainCheckedList.children[1].children

       /*
        if(mainCheckedList.isEnabled) {


        }

        */

    }


}
