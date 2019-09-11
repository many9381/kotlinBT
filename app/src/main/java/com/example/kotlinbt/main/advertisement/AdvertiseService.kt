package com.example.kotlinbt.main.advertisement

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.BluetoothLeAdvertiser
import android.os.Handler
import java.util.concurrent.TimeUnit
import android.widget.Toast
import android.R
import android.app.Notification
import android.app.NotificationManager
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Context

import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.app.PendingIntent
import com.example.kotlinbt.main.MainActivity
import android.util.Log
import android.app.NotificationChannel
import android.bluetooth.*
import android.text.format.DateFormat
import java.util.*


class AdvertiseService : Service() {

    private val FOREGROUND_NOTIFICATION_ID : Int = 1
    var running : Boolean = false

    val ADVERTISING_FAILED = "com.example.android.bluetoothadvertisements.advertising_failed"
    val ADVERTISING_FAILED_EXTRA_CODE = "failureCode"
    val ADVERTISING_TIMED_OUT = 6

    private var mBluetoothLeAdvertiser: BluetoothLeAdvertiser? = null
    private var mAdvertiseCallback: AdvertiseCallback? = null
    private var mBluetoothManager: BluetoothManager? = null
    private lateinit var mHandler: Handler

    private var timeoutRunnable: Runnable? = null


    private val TIMEOUT = TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES)
    private val TAG = "ADVERTISING"

    private var mBluetoothGattServer : BluetoothGattServer? = null
    private val registeredDevices = mutableSetOf<BluetoothDevice>()


    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")
        return null
    }

    override fun onCreate() {
        running = true
        initialize()
        startAdvertising()
        startServer()
        //setTimeout()
        super.onCreate()


    }

    override fun onDestroy() {
        /**
         * Note that onDestroy is not guaranteed to be called quickly or at all. Services exist at
         * the whim of the system, and onDestroy can be delayed or skipped entirely if memory need
         * is critical.
         */
        running = false
        stopServer()
        stopAdvertising()
        //mHandler.removeCallbacks(timeoutRunnable)
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }

    private fun startServer() {
        mBluetoothGattServer = mBluetoothManager?.openGattServer(this, gattServerCallback)
        mBluetoothGattServer?.addService(TimeProfile.createTimeService())
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
        mBluetoothGattServer?.close()
        Log.d("TAG", "GATT server finished")
    }

    private fun initialize() {

        if (mBluetoothLeAdvertiser == null) {
            mBluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (mBluetoothManager != null) {
                val mBluetoothAdapter = mBluetoothManager?.adapter
                if (mBluetoothAdapter != null) {
                    mBluetoothLeAdvertiser = mBluetoothAdapter.bluetoothLeAdvertiser
                } else {
                    Toast.makeText(this, "bt need", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "bt need", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setTimeout() {
        mHandler = Handler()
        timeoutRunnable = Runnable {
            Log.d("ADVERTISING", "AdvertiserService has reached timeout of $TIMEOUT milliseconds, stopping advertising.")
            sendFailureIntent(ADVERTISING_TIMED_OUT)
            stopSelf()
        }
        mHandler.postDelayed(timeoutRunnable, TIMEOUT)
    }

    /**
     * Starts BLE Advertising.
     */
    private fun startAdvertising() {
        goForeground()

        Log.d("ADVERTISING", "Service: Starting Advertising")

        if (mAdvertiseCallback == null) {
            val settings = buildAdvertiseSettings()




            val data = buildAdvertiseData()
            mAdvertiseCallback = SampleAdvertiseCallback()

            if (mBluetoothLeAdvertiser != null) {
                mBluetoothLeAdvertiser?.startAdvertising(
                    settings, data,
                    mAdvertiseCallback
                )
            }
        }
    }

    private fun stopAdvertising() {
        Log.d("ADVERTISING", "Service: Stopping Advertising")
        if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser?.stopAdvertising(mAdvertiseCallback)
            mAdvertiseCallback = null
        }
    }


    private fun goForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )

        val chan = NotificationChannel("myservice",
            "service noti", NotificationManager.IMPORTANCE_NONE)
        //chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)


        val n = Notification.Builder(this, "myservice")
            .setContentTitle("Advertising device via Bluetooth")
            .setContentText("This device is discoverable to others nearby.")
            //.setSmallIcon(R.drawable.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()



        startForeground(FOREGROUND_NOTIFICATION_ID, n)


    }

    private val gattServerCallback = object : BluetoothGattServerCallback() {

        override fun onConnectionStateChange(device: BluetoothDevice, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "BluetoothDevice CONNECTED: $device")
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "BluetoothDevice DISCONNECTED: $device")
                //Remove device from any active subscriptions
                registeredDevices.remove(device)
            }
        }

        override fun onCharacteristicReadRequest(device: BluetoothDevice, requestId: Int, offset: Int, characteristic: BluetoothGattCharacteristic) {
            val now = System.currentTimeMillis()
            when {
                TimeProfile.CURRENT_TIME == characteristic.uuid -> {
                    Log.i(TAG, "Read CurrentTime")
                    mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, TimeProfile.getExactTime(now, TimeProfile.ADJUST_NONE))
                }
                TimeProfile.LOCAL_TIME_INFO == characteristic.uuid -> {
                    Log.i(TAG, "Read LocalTimeInfo")
                    mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, TimeProfile.getLocalTimeInfo(now))
                }
                else -> {
                    // Invalid characteristic
                    Log.w(TAG, "Invalid Characteristic Read: " + characteristic.uuid)
                    mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, 0, null)
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
                mBluetoothGattServer?.sendResponse(device,
                    requestId,
                    BluetoothGatt.GATT_SUCCESS,
                    0,
                    returnValue)
            } else {
                Log.w(TAG, "Unknown descriptor read request")
                mBluetoothGattServer?.sendResponse(device,
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
                    mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, null)
                }
            } else {
                Log.w(TAG, "Unknown descriptor write request")
                if (responseNeeded) {
                    mBluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, 0, null)
                }
            }
        }
    }




    private fun buildAdvertiseData(): AdvertiseData {

        /**
         * Note: There is a strict limit of 31 Bytes on packets sent over BLE Advertisements.
         * This includes everything put into AdvertiseData including UUIDs, device info, &
         * arbitrary service or manufacturer data.
         * Attempting to send packets over this limit will result in a failure with error code
         * AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE. Catch this error in the
         * onStartFailure() method of an AdvertiseCallback implementation.
         */

        val dataBuilder = AdvertiseData.Builder()
        //dataBuilder.addServiceUuid(Constants.Service_UUID)
        dataBuilder.setIncludeDeviceName(true)

        /* For example - this will cause advertising to fail (exceeds size limit) */
        //String failureData = "asdghkajsghalkxcjhfa;sghtalksjcfhalskfjhasldkjfhdskf";
        //dataBuilder.addServiceData(Constants.Service_UUID, failureData.getBytes());

        return dataBuilder.build()
    }

    /**
     * Returns an AdvertiseSettings object set to use low power (to help preserve battery life)
     * and disable the built-in timeout since this code uses its own timeout runnable.
     */
    private fun buildAdvertiseSettings(): AdvertiseSettings {
        val settingsBuilder = AdvertiseSettings.Builder()
        settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
        settingsBuilder.setConnectable(true)
        settingsBuilder.setTimeout(0)
        return settingsBuilder.build()
    }

    private inner class SampleAdvertiseCallback : AdvertiseCallback() {

        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)

            Log.d("ADVERTISING", "Advertising failed")
            sendFailureIntent(errorCode)
            stopSelf()

        }

        override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
            super.onStartSuccess(settingsInEffect)
            Log.d("ADVERTISING", "Advertising successfully started")
        }
    }

    /**
     * Builds and sends a broadcast intent indicating Advertising has failed. Includes the error
     * code as an extra. This is intended to be picked up by the `AdvertiserFragment`.
     */
    private fun sendFailureIntent(errorCode: Int) {
        val failureIntent = Intent()
        failureIntent.action = ADVERTISING_FAILED
        failureIntent.putExtra(ADVERTISING_FAILED_EXTRA_CODE, errorCode)
        sendBroadcast(failureIntent)
    }


}
