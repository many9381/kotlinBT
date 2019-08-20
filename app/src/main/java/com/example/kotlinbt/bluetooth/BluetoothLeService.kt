package com.example.kotlinbt.bluetooth

import android.app.Service
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.os.LocaleList

import com.example.kotlinbt.bluetooth.util.GattAttributes
import java.util.UUID

class BluetoothLeService : Service() {

    private var mBluetoothManager: BluetoothManager? = null
    private var mBluetoothDeviceAddress: String? = null
    private var mConnectionState = STATE_DISCONNECTED


    private var DeviceAddress: String? = null

    private val mNotifyCharacteristic: BluetoothGattCharacteristic? = null


    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val intentAction: String
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED
                mConnectionState = STATE_CONNECTED
                broadcastUpdate(intentAction)
                Log.i("myTestLog", "Connected to GATT server.")
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt!!.discoverServices())


            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED
                mConnectionState = STATE_DISCONNECTED
                Log.i("myTestLog", "Disconnected from GATT server.")
                Log.i("myTag", "Disconnected")
                broadcastUpdate(intentAction)
            }

        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED)

                mBluetoothGatt!!.readRemoteRssi()


            } else {
                Log.w(TAG, "onServicesDiscovered received: $status")
            }
        }


        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val serviceUUID = characteristic.service.uuid.toString()
            val serviceName = GattAttributes.lookupUUID(characteristic.service.uuid, serviceUUID)

            val characteristicUUID = characteristic.uuid.toString()
            val characteristicName = GattAttributes.lookupUUID(characteristic.uuid, characteristicUUID)

            if (status == BluetoothGatt.GATT_SUCCESS) {

                Log.i("myTag_", "---Write GATT_SUCCESS")
                //timeStamp("OTA WRITE RESPONSE TIMESTAMP ");

            } else {

                Log.i("myTag_", "onCharacteristicWrite GATT_Error")
                //                Intent intent = new Intent(ACTION_GATT_CHARACTERISTIC_ERROR);
                //                intent.putExtra(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE, "" + status);
                //
            }

            //            boolean isExitBootloaderCmd = false;
            //            synchronized (mGattCallback) {
            //                isExitBootloaderCmd = mOtaExitBootloaderCmdInProgress;
            //                if (mOtaExitBootloaderCmdInProgress)
            //                    mOtaExitBootloaderCmdInProgress = false;
            //            }
            //
            //            if (isExitBootloaderCmd)
            //                onOtaExitBootloaderComplete(status);
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {

            mBluetoothGatt!!.readCharacteristic(characteristic)


            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic)
                val data = characteristic.value


                Log.i("myTag_", "---Read---GATT_SUCCESS")

            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic)

            //            Log.i("myTag_", "---onCharacteristicChanged---GATT_SUCCESS");

            val data = characteristic.value
        }


    }

    private val mBinder = LocalBinder()

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after `BluetoothGatt#discoverServices()` completes successfully.
     *
     * @return A `List` of supported services.
     */
    val supportedGattServices: List<BluetoothGattService>?
        get() = if (mBluetoothGatt == null) null else mBluetoothGatt!!.services

    private fun broadcastUpdate(action: String) {
        val intent = Intent(action)
        sendBroadcast(intent)
    }

    private fun broadcastUpdate(
        action: String,
        characteristic: BluetoothGattCharacteristic
    ) {


        Log.i("myTag_", action)

        val intent = Intent(action)

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        if (UUID_RGB_CHARACTERISTIC_CONFIG == characteristic.uuid) {
            val flag = characteristic.properties
            var format = -1
            if (flag and 0x01 != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16
                Log.d(TAG, "RGB led format UINT16.")
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8
                Log.d(TAG, "RGB led format UINT8.")
            }

            val getValue = characteristic.getIntValue(format, 0)!!
            Log.i("myTag_", String.format("value : %d", getValue))
            intent.putExtra(EXTRA_DATA, getValue.toString())

        } else {
            // For all other profiles, writes the data formatted in HEX.

            Log.i("myTag_", "else")

            val flag = characteristic.properties
            var format = -1
            if (flag and 0x01 != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8
            }
            val getValue = characteristic.getIntValue(format, 0)!!
            Log.i("myTag_", String.format("value : %d", getValue))
            intent.putExtra(EXTRA_DATA, getValue.toString())

            //            final byte[] data = characteristic.getValue();
            //            if (data != null && data.length > 0) {
            //                final StringBuilder stringBuilder = new StringBuilder(data.length);
            //                for(byte byteChar : data)
            //                    stringBuilder.append(String.format("%02X ", byteChar));
            //                intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            //            }
        }

        sendBroadcast(intent)
    }



    inner class LocalBinder : Binder() {
        fun getService() : BluetoothLeService? {
            return this@BluetoothLeService
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close()
        return super.onUnbind(intent)
    }

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    fun initialize(): Boolean {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.")
                return false
            }
        }

        mBluetoothAdapter = mBluetoothManager!!.adapter
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.")
            return false
        }

        return true
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * `BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)`
     * callback.
     */
    fun connect(address: String?): Boolean {

        DeviceAddress = address

        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.")
            return false
        }

        //        // Previously connected device.  Try to reconnect.
        //        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
        //                && mBluetoothGatt != null) {
        //            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
        //            if (mBluetoothGatt.connect()) {
        //                mConnectionState = STATE_CONNECTING;
        //                return true;
        //            } else {
        //                return false;
        //            }
        //        }

        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.")
            return false
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback)
        Log.d(TAG, "Trying to create a new connection.")
        mBluetoothDeviceAddress = address
        mConnectionState = STATE_CONNECTING
        return true
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * `BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)`
     * callback.
     */
    fun disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.disconnect()
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    fun close() {
        if (mBluetoothGatt == null) {
            return
        }
        mBluetoothGatt!!.close()
        mBluetoothGatt = null
    }

    /**
     * Request a read on a given `BluetoothGattCharacteristic`. The read result is reported
     * asynchronously through the `BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)`
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */

    // readCharacteristic함수가 통신
    fun readCharacteristic(characteristic: BluetoothGattCharacteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }

        Log.i("myTag_", "---------read")

        mBluetoothGatt!!.readCharacteristic(characteristic)


    }


    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    fun setCharacteristicNotification(
        characteristic: BluetoothGattCharacteristic,
        enabled: Boolean
    ) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.setCharacteristicNotification(characteristic, enabled)

        // This is specific to SOUND CHARACTERISTIC CONFIG.
        if (UUID_RGB_CHARACTERISTIC_CONFIG == characteristic.uuid) {
            val descriptor = characteristic.getDescriptor(
                UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG)
            )
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            mBluetoothGatt!!.writeDescriptor(descriptor)
        }
    }

    companion object {
        private val TAG = BluetoothLeService::class.java.simpleName
        private var mBluetoothAdapter: BluetoothAdapter? = null
        private var mBluetoothGatt: BluetoothGatt? = null

        private val STATE_DISCONNECTED = 0
        private val STATE_CONNECTING = 1
        private val STATE_CONNECTED = 2

        val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
        val ACTION_GATT_CONNECTING = "com.example.bluetooth.le.ACTION_GATT_CONNECTING"
        val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"
        val ACTION_GATT_DISCONNECTED_CAROUSEL = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED_CAROUSEL"
        val ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED"
        val ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE"
        val ACTION_OTA_DATA_AVAILABLE = "com.cysmart.bluetooth.le.ACTION_OTA_DATA_AVAILABLE"
        val ACTION_GATT_DISCONNECTED_OTA = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED_OTA"
        val ACTION_GATT_CONNECT_OTA = "com.example.bluetooth.le.ACTION_GATT_CONNECT_OTA"
        val ACTION_GATT_SERVICES_DISCOVERED_OTA = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED_OTA"
        val ACTION_GATT_CHARACTERISTIC_ERROR = "com.example.bluetooth.le.ACTION_GATT_CHARACTERISTIC_ERROR"
        val ACTION_GATT_SERVICE_DISCOVERY_UNSUCCESSFUL =
            "com.example.bluetooth.le.ACTION_GATT_SERVICE_DISCOVERY_UNSUCCESSFUL"
        val ACTION_PAIR_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST"
        val ACTION_WRITE_COMPLETED = "android.bluetooth.device.action.ACTION_WRITE_COMPLETED"
        val ACTION_WRITE_FAILED = "android.bluetooth.device.action.ACTION_WRITE_FAILED"
        val ACTION_WRITE_SUCCESS = "android.bluetooth.device.action.ACTION_WRITE_SUCCESS"
        val EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA"

        val ORDER_OPEN = "com.capstone.locker.open"
        val ORDER_CLOSE = "com.capstone.locker.close"


        val UUID_RGB_CHARACTERISTIC_CONFIG = UUID.fromString(SampleGattAttributes.RGB_CHARACTERISTIC_CONFIG)

        val UUID_CAPSENSE_SERVICE = UUID.fromString(GattAttributes.CAPSENSE_SERVICE)
        val UUID_CAPSENSE_SERVICE_CUSTOM = UUID.fromString(GattAttributes.CAPSENSE_SERVICE_CUSTOM)


        /**
         * Request a write on a given `BluetoothGattCharacteristic` for RGB.
         *
         * @param characteristic
         * @param red
         * @param green
         * @param blue
         * @param intensity
         */
        fun writeCharacteristicRGB(
            characteristic: BluetoothGattCharacteristic,
            red: Int,
            green: Int,
            blue: Int,
            intensity: Int
        ) {
            val serviceUUID = characteristic.service.uuid.toString()
            val serviceName = GattAttributes.lookupUUID(characteristic.service.uuid, serviceUUID)

            val characteristicUUID = characteristic.uuid.toString()
            val characteristicName = GattAttributes.lookupUUID(characteristic.uuid, characteristicUUID)
            if (mBluetoothAdapter == null || mBluetoothGatt == null) {
                return
            } else {
                val valueByte = ByteArray(4)
                valueByte[0] = red.toByte()
                valueByte[1] = green.toByte()
                valueByte[2] = blue.toByte()
                valueByte[3] = intensity.toByte()
                characteristic.value = valueByte
                //                String characteristicValue = Utils.ByteArraytoHex(valueByte);
                mBluetoothGatt!!.writeCharacteristic(characteristic)

            }

        }
    }


}
