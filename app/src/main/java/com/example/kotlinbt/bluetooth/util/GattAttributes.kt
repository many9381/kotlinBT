package com.example.kotlinbt.bluetooth.util

import java.util.*

/*
 * Copyright Cypress Semiconductor Corporation, 2014-2015 All rights reserved.
 *
 * This software, associated documentation and materials ("Software") is
 * owned by Cypress Semiconductor Corporation ("Cypress") and is
 * protected by and subject to worldwide patent protection (UnitedStates and foreign), United States copyright laws and international
 * treaty provisions. Therefore, unless otherwise specified in a separate license agreement between you and Cypress, this Software
 * must be treated like any other copyrighted material. Reproduction,
 * modification, translation, compilation, or representation of this
 * Software in any other form (e.g., paper, magnetic, optical, silicon)
 * is prohibited without Cypress's express written permission.
 *
 * Disclaimer: THIS SOFTWARE IS PROVIDED AS-IS, WITH NO WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,
 * NONINFRINGEMENT, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE. Cypress reserves the right to make changes
 * to the Software without notice. Cypress does not assume any liability
 * arising out of the application or use of Software or any product or
 * circuit described in the Software. Cypress does not authorize its
 * products for use as critical components in any products where a
 * malfunction or failure may reasonably be expected to result in
 * significant injury or death ("High Risk Product"). By including
 * Cypress's product in a High Risk Product, the manufacturer of such
 * system or application assumes all risk of such use and in doing so
 * indemnifies Cypress against all liability.
 *
 * Use of this Software may be limited by and subject to the applicable
 * Cypress software license agreement.
 *
 *
 */

import com.example.kotlinbt.R

import java.util.HashMap
import java.util.UUID

/**
 * This class includes a subset of standard GATT attributes and carousel image
 * mapping
 */
object GattAttributes {

    val attributesImageMap = HashMap<UUID, Int>()
    val attributesCapSenseImageMap = HashMap<UUID, Int>()
    val attributesCapSense = HashMap<UUID, String>()
    private val descriptorAttributes = HashMap<String, String>()
    private val attributesUUID = HashMap<UUID, String>()
    private val rdkAttributesUUID = HashMap<Int, String>()
    /**
     * Services
     */
    val HEART_RATE_SERVICE = "0000180d-0000-1000-8000-00805f9b34fb"
    val DEVICE_INFORMATION_SERVICE = "0000180a-0000-1000-8000-00805f9b34fb"
    val HEALTH_THERMOMETER_SERVICE = "00001809-0000-1000-8000-00805f9b34fb"
    val BATTERY_SERVICE = "0000180f-0000-1000-8000-00805f9b34fb"
    val IMMEDIATE_ALERT_SERVICE = "00001802-0000-1000-8000-00805f9b34fb"
    val CAPSENSE_SERVICE = "0000cab5-0000-1000-8000-00805f9b34fb"
    val CAPSENSE_SERVICE_CUSTOM = "0003cab5-0000-1000-8000-00805f9b0131"
    val RGB_LED_SERVICE = "0000cbbb-0000-1000-8000-00805f9b34fb"
    val RGB_LED_SERVICE_CUSTOM = "0003cbbb-0000-1000-8000-00805f9b0131"
    val LINK_LOSS_SERVICE = "00001803-0000-1000-8000-00805f9b34fb"
    val TRANSMISSION_POWER_SERVICE = "00001804-0000-1000-8000-00805f9b34fb"
    val BLOOD_PRESSURE_SERVICE = "00001810-0000-1000-8000-00805f9b34fb"
    val GLUCOSE_SERVICE = "00001808-0000-1000-8000-00805f9b34fb"
    val RSC_SERVICE = "00001814-0000-1000-8000-00805f9b34fb"
    val BAROMETER_SERVICE = "00040001-0000-1000-8000-00805f9b0131"
    val ACCELEROMETER_SERVICE = "00040020-0000-1000-8000-00805f9b0131"
    val ANALOG_TEMPERATURE_SERVICE = "00040030-0000-1000-8000-00805f9b0131"
    val CSC_SERVICE = "00001816-0000-1000-8000-00805f9b34fb"
    val HUMAN_INTERFACE_DEVICE_SERVICE = "00001812-0000-1000-8000-00805f9b34fb"
    val SCAN_PARAMETERS_SERVICE = "00001813-0000-1000-8000-00805f9b34fb"
    // public static final String OTA_UPDATE_SERVICE = "00060000-0000-1000-8000-00805f9b34fb";
    val OTA_UPDATE_SERVICE = "00060000-f8ce-11e4-abf4-0002a5d5c51b"
    /**
     * Unused service UUIDS
     */
    val ALERT_NOTIFICATION_SERVICE = "00001811-0000-1000-8000-00805f9b34fb"
    val BODY_COMPOSITION_SERVICE = "0000181b-0000-1000-8000-00805f9b34fb"
    val BODY_MANAGEMENT_SERVICE = "0000181e-0000-1000-8000-00805f9b34fb"
    val CONTINUOUS_GLUCOSE_MONITORING_SERVICE = "0000181f-0000-1000-8000-00805f9b34fb"
    val CURRENT_TIME_SERVICE = "00001805-0000-1000-8000-00805f9b34fb"
    val CYCLING_POWER_SERVICE = "00001818-0000-1000-8000-00805f9b34fb"
    val ENVIRONMENTAL_SENSING_SERVICE = "0000181a-0000-1000-8000-00805f9b34fb"
    val LOCATION_NAVIGATION_SERVICE = "00001819-0000-1000-8000-00805f9b34fb"
    val NEXT_DST_CHANGE_SERVICE = "00001807-0000-1000-8000-00805f9b34fb"
    val PHONE_ALERT_STATUS_SERVICE = "0000180e-0000-1000-8000-00805f9b34fb"
    val REFERENCE_TIME_UPDATE_SERVICE = "00001806-0000-1000-8000-00805f9b34fb"
    val USER_DATA_SERVICE = "0000181c-0000-1000-8000-00805f9b34fb"
    val WEIGHT_SCALE_SERVICE = "0000181d-0000-1000-8000-00805f9b34fb"
    /**
     * Heart rate characteristics
     */
    val HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb"
    val BODY_SENSOR_LOCATION = "00002a38-0000-1000-8000-00805f9b34fb"
    /**
     * Device information characteristics
     */
    val SYSTEM_ID = "00002a23-0000-1000-8000-00805f9b34fb"
    val MODEL_NUMBER_STRING = "00002a24-0000-1000-8000-00805f9b34fb"
    val SERIAL_NUMBER_STRING = "00002a25-0000-1000-8000-00805f9b34fb"
    val FIRMWARE_REVISION_STRING = "00002a26-0000-1000-8000-00805f9b34fb"
    val HARDWARE_REVISION_STRING = "00002a27-0000-1000-8000-00805f9b34fb"
    val SOFTWARE_REVISION_STRING = "00002a28-0000-1000-8000-00805f9b34fb"
    val MANUFACTURER_NAME_STRING = "00002a29-0000-1000-8000-00805f9b34fb"
    val PNP_ID = "00002a50-0000-1000-8000-00805f9b34fb"
    val IEEE = "00002a2a-0000-1000-8000-00805f9b34fb"
    /**
     * Battery characteristics
     */
    val BATTERY_LEVEL = "00002a19-0000-1000-8000-00805f9b34fb"
    /**
     * Health Thermometer characteristics
     */
    val HEALTH_TEMP_MEASUREMENT = "00002a1c-0000-1000-8000-00805f9b34fb"
    val TEMPERATURE_TYPE = "00002a1d-0000-1000-8000-00805f9b34fb"
    /**
     * Gatt services
     */
    val GENERIC_ACCESS_SERVICE = "00001800-0000-1000-8000-00805f9b34fb"
    val GENERIC_ATTRIBUTE_SERVICE = "00001801-0000-1000-8000-00805f9b34fb"
    /**
     * Find me characteristics
     */
    val ALERT_LEVEL = "00002a06-0000-1000-8000-00805f9b34fb"
    val TRANSMISSION_POWER_LEVEL = "00002a07-0000-1000-8000-00805f9b34fb"
    /**
     * Capsense characteristics
     */
    val CAPSENSE_PROXIMITY = "0000caa1-0000-1000-8000-00805f9b34fb"
    val CAPSENSE_SLIDER = "0000caa2-0000-1000-8000-00805f9b34fb"
    val CAPSENSE_BUTTONS = "0000caa3-0000-1000-8000-00805f9b34fb"
    val CAPSENSE_PROXIMITY_CUSTOM = "0003caa1-0000-1000-8000-00805f9b0131"
    val CAPSENSE_SLIDER_CUSTOM = "0003caa2-0000-1000-8000-00805f9b0131"
    val CAPSENSE_BUTTONS_CUSTOM = "0003caa3-0000-1000-8000-00805f9b0131"
    /**
     * RGB characteristics
     */
    val RGB_LED = "0000cbb1-0000-1000-8000-00805f9b34fb"
    val RGB_LED_CUSTOM = "0003cbb1-0000-1000-8000-00805f9b0131"
    /**
     * Glucose Measurement characteristics
     */
    val GLUCOSE_MEASUREMENT = "00002a18-0000-1000-8000-00805f9b34fb"
    /**
     * Blood Pressure service Characteristics
     */
    val BLOOD_PRESSURE_MEASUREMENT = "00002a35-0000-1000-8000-00805f9b34fb"
    /**
     * Running Speed & Cadence Characteristics
     */
    val RSC_MEASUREMENT = "00002a53-0000-1000-8000-00805f9b34fb"
    /**
     * Cycling Speed & Cadence Characteristics
     */
    val CSC_MEASUREMENT = "00002a5b-0000-1000-8000-00805f9b34fb"
    /**
     * Barometer service characteristics
     */
    val BAROMETER_DIGITAL_SENSOR = "00040002-0000-1000-8000-00805f9b0131"
    val BAROMETER_SENSOR_SCAN_INTERVAL = "00040004-0000-1000-8000-00805f9b0131"
    val BAROMETER_DATA_ACCUMULATION = "00040007-0000-1000-8000-00805f9b0131"
    val BAROMETER_READING = "00040009-0000-1000-8000-00805f9b0131"
    val BAROMETER_THRESHOLD_FOR_INDICATION = "0004000d-0000-1000-8000-00805f9b0131"
    /**
     * Accelerometer service characteristics
     */
    val ACCELEROMETER_ANALOG_SENSOR = "00040021-0000-1000-8000-00805f9b0131"
    val ACCELEROMETER_SENSOR_SCAN_INTERVAL = "00040023-0000-1000-8000-00805f9b0131"
    val ACCELEROMETER_DATA_ACCUMULATION = "00040026-0000-1000-8000-00805f9b0131"
    val ACCELEROMETER_READING_X = "00040028-0000-1000-8000-00805f9b0131"
    val ACCELEROMETER_READING_Y = "0004002b-0000-1000-8000-00805f9b0131"
    val ACCELEROMETER_READING_Z = "0004002d-0000-1000-8000-00805f9b0131"
    /**
     * Analog Temperature service characteristics
     */
    val TEMPERATURE_ANALOG_SENSOR = "00040031-0000-1000-8000-00805f9b0131"
    val TEMPERATURE_SENSOR_SCAN_INTERVAL = "00040032-0000-1000-8000-00805f9b0131"
    val TEMPERATURE_READING = "00040033-0000-1000-8000-00805f9b0131"
    /**
     * HID Characteristics
     */
    val PROTOCOL_MODE = "00002a4e-0000-1000-8000-00805f9b34fb"
    val REP0RT = "00002a4d-0000-1000-8000-00805f9b34fb"
    val REPORT_MAP = "00002a4b-0000-1000-8000-00805f9b34fb"
    val BOOT_KEYBOARD_INPUT_REPORT = "00002a22-0000-1000-8000-00805f9b34fb"
    val BOOT_KEYBOARD_OUTPUT_REPORT = "00002a32-0000-1000-8000-00805f9b34fb"
    val BOOT_MOUSE_INPUT_REPORT = "00002a33-0000-1000-8000-00805f9b34fb"
    val HID_CONTROL_POINT = "00002a4c-0000-1000-8000-00805f9b34fb"
    val HID_INFORMATION = "00002a4a-0000-1000-8000-00805f9b34fb"
    /**
     * OTA Characteristic
     */
    //public static final String OTA_CHARACTERISTIC = "00060001-0000-1000-8000-00805F9B34fb";
    val OTA_CHARACTERISTIC = "00060001-f8ce-11e4-abf4-0002a5d5c51b"
    /**
     * Descriptor UUID's
     */
    val CHARACTERISTIC_EXTENDED_PROPERTIES = "00002900-0000-1000-8000-00805f9b34fb"
    val CHARACTERISTIC_USER_DESCRIPTION = "00002901-0000-1000-8000-00805f9b34fb"
    val CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"
    val SERVER_CHARACTERISTIC_CONFIGURATION = "00002903-0000-1000-8000-00805f9b34fb"
    val CHARACTERISTIC_PRESENTATION_FORMAT = "00002904-0000-1000-8000-00805f9b34fb"
    val CHARACTERISTIC_AGGREGATE_FORMAT = "00002905-0000-1000-8000-00805f9b34fb"
    val VALID_RANGE = "00002906-0000-1000-8000-00805f9b34fb"
    val EXTERNAL_REPORT_REFERENCE = "00002907-0000-1000-8000-00805f9b34fb"
    val REPORT_REFERENCE = "00002908-0000-1000-8000-00805f9b34fb"
    val ENVIRONMENTAL_SENSING_CONFIGURATION = "0000290B-0000-1000-8000-00805f9b34fb"
    val ENVIRONMENTAL_SENSING_MEASUREMENT = "0000290C-0000-1000-8000-00805f9b34fb"
    val ENVIRONMENTAL_SENSING_TRIGGER_SETTING = "0000290D-0000-1000-8000-00805f9b34fb"
    val HEALTH_THERMO_SERVICE = "00001809-0000-1000-8000-00805f9b34fb"
    val BOND_MANAGEMENT_SERVICE = "0000181e-0000-1000-8000-00805f9b34fb"
    val HEART_RATE_CONTROL_POINT = "00002a39-0000-1000-8000-00805f9b34fb"
    val TEMPERATURE_INTERMEDIATE = "00002a1e-0000-1000-8000-00805f9b34fb"
    val TEMPERATURE_MEASUREMENT_INTERVAL = "00002a21-0000-1000-8000-00805f9b34fb"
    val GLUCOSE_MEASUREMENT_CONTEXT = "00002a34-0000-1000-8000-00805f9b34fb"
    val GLUCOSE_FEATURE = "00002a51-0000-1000-8000-00805f9b34fb"
    val RECORD_ACCESS_CONTROL_POINT = "00002a52-0000-1000-8000-00805f9b34fb"
    val BLOOD_INTERMEDIATE_CUFF_PRESSURE = "00002a36-0000-1000-8000-00805f9b34fb"
    val BLOOD_PRESSURE_FEATURE = "00002a49-0000-1000-8000-00805f9b34fb"
    val RSC_FEATURE = "00002a54-0000-1000-8000-00805f9b34fb"
    val SC_SENSOR_LOCATION = "00002a5d-0000-1000-8000-00805f9b34fb"
    val SC_CONTROL_POINT = "00002a55-0000-1000-8000-00805f9b34fb"
    val CSC_FEATURE = "00002a5c-0000-1000-8000-00805f9b34fb"
    /**
     * Unused Service characteristics
     */
    val AEROBIC_HEART_RATE_LOWER_LIMIT = "00002a7e-0000-1000-8000-00805f9b34fb"
    val AEROBIC_HEART_RATE_UPPER_LIMIT = "00002a84-0000-1000-8000-00805f9b34fb"
    val AEROBIC_THRESHOLD = "00002a7f-0000-1000-8000-00805f9b34fb"
    val AGE = "00002a80-0000-1000-8000-00805f9b34fb"
    val ALERT_CATEGORY_ID = "00002a43-0000-1000-8000-00805f9b34fb"
    val ALERT_CATEGORY_ID_BIT_MASK = "00002a42-0000-1000-8000-00805f9b34fb"
    val ALERT_STATUS = "00002a3F-0000-1000-8000-00805f9b34fb"
    val ANAEROBIC_HEART_RATE_LOWER_LIMIT = "00002a81-0000-1000-8000-00805f9b34fb"
    val ANAEROBIC_HEART_RATE_UPPER_LIMIT = "00002a82-0000-1000-8000-00805f9b34fb"
    val ANAEROBIC_THRESHOLD = "00002aA83-0000-1000-8000-00805f9b34fb"
    val APPARENT_WIND_DIRECTION = "00002a73-0000-1000-8000-00805f9b34fb"
    val APPARENT_WIND_SPEED = "00002a72-0000-1000-8000-00805f9b34fb"
    val APPEARANCE = "00002a01-0000-1000-8000-00805f9b34fb"
    val BAROMETRIC_PRESSURE_TREND = "00002aa3-0000-1000-8000-00805f9b34fb"
    val BODY_COMPOSITION_FEATURE = "00002a9B-0000-1000-8000-00805f9b34fb"
    val BODY_COMPOSITION_MEASUREMENT = "00002a9C-0000-1000-8000-00805f9b34fb"
    val BOND_MANAGEMENT_CONTROL_POINT = "00002aa4-0000-1000-8000-00805f9b34fb"
    val BOND_MANAGEMENT_FEATURE = "00002aa5-0000-1000-8000-00805f9b34fb"
    val CENTRAL_ADDRESS_RESOLUTION = "00002aa6-0000-1000-8000-00805f9b34fb"
    val CGM_FEATURE = "00002aa8-0000-1000-8000-00805f9b34fb"
    val CGM_MEASUREMENT = "00002aa7-0000-1000-8000-00805f9b34fb"
    val CGM_SESSION_RUN_TIME = "00002aab-0000-1000-8000-00805f9b34fb"
    val CGM_SESSION_START_TIME = "00002aaa-0000-1000-8000-00805f9b34fb"
    val CGM_SPECIFIC_OPS_CONTROL_POINT = "00002aaC-0000-1000-8000-00805f9b34fb"
    val CGM_STATUS = "00002aa9-0000-1000-8000-00805f9b34fb"
    val CYCLING_POWER_CONTROL_POINT = "00002a66-0000-1000-8000-00805f9b34fb"
    val CYCLING_POWER_FEATURE = "00002a65-0000-1000-8000-00805f9b34fb"
    val CYCLING_POWER_MEASUREMENT = "00002a63-0000-1000-8000-00805f9b34fb"
    val CYCLING_POWER_VECTOR = "00002a64-0000-1000-8000-00805f9b34fb"
    val DATABASE_CHANGE_INCREMENT = "00002a99-0000-1000-8000-00805f9b34fb"
    val DATE_OF_BIRTH = "00002a85-0000-1000-8000-00805f9b0131"
    val DATE_OF_THRESHOLD_ASSESSMENT = "00002a86-0000-1000-8000-00805f9b0131"
    val DATE_TIME = "00002a08-0000-1000-8000-00805f9b34fb"
    val DAY_DATE_TIME = "00002a0a-0000-1000-8000-00805f9b34fb"
    val DAY_OF_WEEK = "00002A09-0000-1000-8000-00805f9b34fb"
    val DESCRIPTOR_VALUE_CHANGED = "00002a7d-0000-1000-8000-00805f9b34fb"
    val DEVICE_NAME = "00002a00-0000-1000-8000-00805f9b34fb"
    val DEW_POINT = "00002a7b-0000-1000-8000-00805f9b34fb"
    val DST_OFFSET = "00002a0d-0000-1000-8000-00805f9b34fb"
    val ELEVATION = "00002a6c-0000-1000-8000-00805f9b34fb"
    val EMAIL_ADDRESS = "00002a87-0000-1000-8000-00805f9b34fb"
    val EXACT_TIME_256 = "00002a0c-0000-1000-8000-00805f9b34fb"
    val FAT_BURN_HEART_RATE_LOWER_LIMIT = "00002a88-0000-1000-8000-00805f9b34fb"
    val FAT_BURN_HEART_RATE_UPPER_LIMIT = "00002a89-0000-1000-8000-00805f9b34fb"
    val FIRSTNAME = "00002a8a-0000-1000-8000-00805f9b34fb"
    val FIVE_ZONE_HEART_RATE_LIMITS = "00002A8b-0000-1000-8000-00805f9b34fb"
    val GENDER = "00002a8c-0000-1000-8000-00805f9b34fb"
    val GUST_FACTOR = "00002a74-0000-1000-8000-00805f9b34fb"
    val HEAT_INDEX = "00002a89-0000-1000-8000-00805f9b34fb"
    val HEIGHT = "00002a8a-0000-1000-8000-00805f9b34fb"
    val HEART_RATE_MAX = "00002a8d-0000-1000-8000-00805f9b34fb"
    val HIP_CIRCUMFERENCE = "00002a8f-0000-1000-8000-00805f9b34fb"
    val HUMIDITY = "00002a6f-0000-1000-8000-00805f9b34fb"
    val INTERMEDIATE_CUFF_PRESSURE = "00002a36-0000-1000-8000-00805f9b34fb"
    val INTERMEDIATE_TEMPERATURE = "00002a1e-0000-1000-8000-00805f9b34fb"
    val IRRADIANCE = "00002a77-0000-1000-8000-00805f9b34fb"
    val LANGUAGE = "00002aa2-0000-1000-8000-00805f9b34fb"
    val LAST_NAME = "00002a90-0000-1000-8000-00805f9b34fb"
    val LN_CONTROL_POINT = "00002a6b-0000-1000-8000-00805f9b34fb"
    val LN_FEATURE = "00002a6a-0000-1000-8000-00805f9b34fb"
    val LOCAL_TIME_INFORMATION = "00002a0f-0000-1000-8000-00805f9b34fb"
    val LOCATION_AND_SPEED = "00002a67-0000-1000-8000-00805f9b34fb"
    val MAGNETIC_DECLINATION = "00002a2c-0000-1000-8000-00805f9b34fb"
    val MAGNETIC_FLUX_DENSITY_2D = "00002aa0-0000-1000-8000-00805f9b34fb"
    val MAGNETIC_FLUX_DENSITY_3D = "00002aa1-0000-1000-8000-00805f9b34fb"
    val MANUFACTURE_NAME_STRING = "00002a29-0000-1000-8000-00805f9b34fb"
    val MAXIMUM_RECOMMENDED_HEART_RATE = "00002a91-0000-1000-8000-00805f9b34fb"
    val MEASUREMENT_INTERVAL = "00002a21-0000-1000-8000-00805f9b34fb"
    val NAVIGATION = "00002a68-0000-1000-8000-00805f9b34fb"
    val NEW_ALERT = "00002a46-0000-1000-8000-00805f9b34fb"
    val PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS = "00002a04-0000-1000-8000-00805f9b34fb"
    val PERIPHERAL_PRIVACY_FLAG = "00002a02-0000-1000-8000-00805f9b34fb"
    val POLLEN_CONCENTRATION = "00002a75-0000-1000-8000-00805f9b34fb"
    val POSITION_QUALITY = "00002a69-0000-1000-8000-00805f9b34fb"
    val PRESSURE = "00002a6d-0000-1000-8000-00805f9b34fb"
    val RAINFALL = "00002a78-0000-1000-8000-00805f9b34fb"
    val RECONNECTION_ADDRESS = "00002a03-0000-1000-8000-00805f9b34fb"
    val REFERNCE_TIME_INFORMATION = "00002a14-0000-1000-8000-00805f9b34fb"
    val RESTING_HEART_RATE = "00002a92-0000-1000-8000-00805f9b34fb"
    val RINGER_CONTROL_POINT = "00002a40-0000-1000-8000-00805f9b34fb"
    val RINGER_SETTING = "00002a41-0000-1000-8000-00805f9b34fb"
    val SCAN_INTERVAL_WINDOW = "00002a4F-0000-1000-8000-00805f9b34fb"
    val SENSOR_LOCATION = "00002a5d-0000-1000-8000-00805f9b34fb"
    val SERVICE_CHANGED = "00002a05-0000-1000-8000-00805f9b34fb"
    val SPORT_TYPE_FOR_AEROBIN_AND_ANAEROBIC_THRESHOLDS = "00002a93-0000-1000-8000-00805f9b34fb"
    val SUPPORTED_NEW_ALERT_CATEGORY = "00002a47-0000-1000-8000-00805f9b34fb"
    val SUPPORTED_UNREAD_ALERT_CATEGORY = "00002a48-0000-1000-8000-00805f9b34fb"
    val TEMPERATURE = "00002a6e-0000-1000-8000-00805f9b34fb"
    val TEMPERATURE_MEASUREMENT = "00002a1c-0000-1000-8000-00805f9b34fb"
    val THREE_ZONE_HEART_RATE_LIMITS = "00002a94-0000-1000-8000-00805f9b34fb"
    val TIME_ACCURACY = "00002a12-0000-1000-8000-00805f9b34fb"
    val TIME_SOURCE = "00002a13-0000-1000-8000-00805f9b34fb"
    val TIME_UPDATE_CONTROL_POINT = "00002a16-0000-1000-8000-00805f9b34fb"
    val TIME_UPDATE_STATE = "00002a17-0000-1000-8000-00805f9b34fb"
    val TIME_WITH_DST = "00002a11-0000-1000-8000-00805f9b34fb"
    val TIME_ZONE = "00002a0e-0000-1000-8000-00805f9b34fb"
    val TRUE_WIND_DIRECTION = "00002a71-0000-1000-8000-00805f9b34fb"
    val TRUE_WIND_SPEED = "00002a70-0000-1000-8000-00805f9b34fb"
    val TWO_ZONE_HEART_RATE = "00002a95-0000-1000-8000-00805f9b34fb"
    val TX_POWER = "00002a07-0000-1000-8000-00805f9b34fb"
    val UNCERTAINITY = "00002ab4-0000-1000-8000-00805f9b34fb"
    val UNREAD_ALERT_STATUS = "00002a45-0000-1000-8000-00805f9b34fb"
    val USER_CONTROL_POINT = "00002a9f-0000-1000-8000-00805f9b34fb"
    val USER_INDEX = "00002a9a-0000-1000-8000-00805f9b34fb"
    val UV_INDEX = "00002a76-0000-1000-8000-00805f9b34fb"
    val VO2_MAX = "00002a96-0000-1000-8000-00805f9b34fb"
    val WAIST_CIRCUMFERENCE = "00002a97-0000-1000-8000-00805f9b34fb"
    val WEIGHT = "00002a98-0000-1000-8000-00805f9b34fb"
    val WEIGHT_SCALE_FEATURE = "00002a9e-0000-1000-8000-00805f9b34fb"
    val WIND_CHILL = "00002a7-0000-1000-8000-00805f9b34fb"


    init {

        // Services.
        attributesUUID[UUIDDatabase.UUID_HEART_RATE_SERVICE] = "Heart Rate Service"
        attributesUUID[UUIDDatabase.UUID_HEALTH_THERMOMETER_SERVICE] = "Health Thermometer Service"
        attributesUUID[UUIDDatabase.UUID_GENERIC_ACCESS_SERVICE] = "Generic Access Service"
        attributesUUID[UUIDDatabase.UUID_GENERIC_ATTRIBUTE_SERVICE] = "Generic Attribute Service"
        attributesUUID[UUIDDatabase.UUID_DEVICE_INFORMATION_SERVICE] = "Device Information Service"
        attributesUUID[UUIDDatabase.UUID_BATTERY_SERVICE] = "Battery Service"
        attributesUUID[UUIDDatabase.UUID_IMMEDIATE_ALERT_SERVICE] = "Immediate Alert"
        attributesUUID[UUIDDatabase.UUID_LINK_LOSS_SERVICE] = "Link Loss"
        attributesUUID[UUIDDatabase.UUID_TRANSMISSION_POWER_SERVICE] = "Tx Power"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_SERVICE] = "CapSense Service"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_SERVICE_CUSTOM] = "CapSense Service"
        attributesUUID[UUIDDatabase.UUID_RGB_LED_SERVICE] = "RGB LED Service"
        attributesUUID[UUIDDatabase.UUID_RGB_LED_SERVICE_CUSTOM] = "RGB LED Service"
        attributesUUID[UUIDDatabase.UUID_GLUCOSE_SERVICE] = "Glucose Service"
        attributesUUID[UUIDDatabase.UUID_BLOOD_PRESSURE_SERVICE] = "Blood Pressure Service"
        attributesUUID[UUIDDatabase.UUID_RSC_SERVICE] = "Running Speed & Cadence Service"
        attributesUUID[UUIDDatabase.UUID_BAROMETER_SERVICE] = "Barometer Service"
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_SERVICE] = "Accelerometer Service"
        attributesUUID[UUIDDatabase.UUID_ANALOG_TEMPERATURE_SERVICE] = "Analog Temperature Service"
        attributesUUID[UUIDDatabase.UUID_CSC_SERVICE] = "Cycling Speed & Cadence Service"

        // Unused Services
        attributesUUID[UUIDDatabase.UUID_ALERT_NOTIFICATION_SERVICE] = "Alert notification Service"
        attributesUUID[UUIDDatabase.UUID_BODY_COMPOSITION_SERVICE] = "Body Composition Service"
        attributesUUID[UUIDDatabase.UUID_BOND_MANAGEMENT_SERVICE] = "Bond Management Service"
        attributesUUID[UUIDDatabase.UUID_CONTINUOUS_GLUCOSE_MONITORING_SERVICE] =
            "Continuous Glucose Monitoring Service"
        attributesUUID[UUIDDatabase.UUID_CURRENT_TIME_SERVICE] = "Current Time Service"
        attributesUUID[UUIDDatabase.UUID_CYCLING_POWER_SERVICE] = "Cycling Power Service"
        attributesUUID[UUIDDatabase.UUID_ENVIRONMENTAL_SENSING_SERVICE] = "Environmental Sensing Service"
        attributesUUID[UUIDDatabase.UUID_HID_SERVICE] = "Human Interface Device Service"
        attributesUUID[UUIDDatabase.UUID_LOCATION_NAVIGATION_SERVICE] = "Location and Navigation Service"
        attributesUUID[UUIDDatabase.UUID_NEXT_DST_CHANGE_SERVICE] = "Next DST Change Service"
        attributesUUID[UUIDDatabase.UUID_PHONE_ALERT_STATUS_SERVICE] = "Phone Alert Status Service"
        attributesUUID[UUIDDatabase.UUID_REFERENCE_TIME_UPDATE_SERVICE] = "Reference Time Update Service"
        attributesUUID[UUIDDatabase.UUID_SCAN_PARAMETERS_SERVICE] = "Scan Paramenters Service"
        attributesUUID[UUIDDatabase.UUID_USER_DATA_SERVICE] = "User Data Service"
        attributesUUID[UUIDDatabase.UUID_WEIGHT_SCALE_SERVICE] = "Weight Scale Service"

        // Heart Rate Characteristics.
        attributesUUID[UUIDDatabase.UUID_HEART_RATE_MEASUREMENT] = "Heart Rate Measurement"
        attributesUUID[UUIDDatabase.UUID_BODY_SENSOR_LOCATION] = "Body Sensor Location"
        attributesUUID[UUIDDatabase.UUID_HEART_RATE_CONTROL_POINT] = "Heart Rate Control Point"

        // Health thermometer Characteristics.
        attributesUUID[UUIDDatabase.UUID_HEALTH_THERMOMETER] = "Health Thermometer Measurement"
        attributesUUID[UUIDDatabase.UUID_HEALTH_THERMOMETER_SENSOR_LOCATION] = "Temperature Type"
        attributesUUID[UUIDDatabase.UUID_TEMPERATURE_INTERMEDIATE] = "Intermediate Temperature"
        attributesUUID[UUIDDatabase.UUID_TEMPERATURE_MEASUREMENT_INTERVAL] = "Measurement Interval"

        // Device Information Characteristics
        attributesUUID[UUIDDatabase.UUID_SYSTEM_ID] = "System ID"
        attributesUUID[UUIDDatabase.UUID_MODEL_NUMBER_STRING] = "Model Number String"
        attributesUUID[UUIDDatabase.UUID_SERIAL_NUMBER_STRING] = "Serial Number String"
        attributesUUID[UUIDDatabase.UUID_FIRMWARE_REVISION_STRING] = "Firmware Revision String"
        attributesUUID[UUIDDatabase.UUID_HARDWARE_REVISION_STRING] = "Hardware Revision String"
        attributesUUID[UUIDDatabase.UUID_SOFTWARE_REVISION_STRING] = "Software Revision String"
        attributesUUID[UUIDDatabase.UUID_MANUFACTURE_NAME_STRING] = "Manufacturer Name String"
        attributesUUID[UUIDDatabase.UUID_PNP_ID] = "PnP ID"
        attributesUUID[UUIDDatabase.UUID_IEEE] = "IEEE 11073-20601 Regulatory Certification Data List"

        // Battery service characteristics
        attributesUUID[UUIDDatabase.UUID_BATTERY_LEVEL] = "Battery Level"

        // Find me service characteristics
        attributesUUID[UUIDDatabase.UUID_ALERT_LEVEL] = "Alert Level"
        attributesUUID[UUIDDatabase.UUID_TRANSMISSION_POWER_LEVEL] = "Tx Power Level"

        // Capsense Characteristics
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_BUTTONS] = "CapSense Button"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_PROXIMITY] = "CapSense Proximity"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_SLIDER] = "CapSense Slider"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_BUTTONS_CUSTOM] = "CapSense Button"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_PROXIMITY_CUSTOM] = "CapSense Proximity"
        attributesUUID[UUIDDatabase.UUID_CAPSENSE_SLIDER_CUSTOM] = "CapSense Slider"

        // RGB Characteristics
        attributesUUID[UUIDDatabase.UUID_RGB_LED] = "RGB LED"
        attributesUUID[UUIDDatabase.UUID_RGB_LED_CUSTOM] = "RGB LED"

        // Glucose Characteristics
        attributesUUID[UUIDDatabase.UUID_GLUCOSE_MEASUREMENT] = "Glucose Measurement"
        attributesUUID[UUIDDatabase.UUID_GLUCOSE_MEASUREMENT_CONTEXT] = "Glucose Measurement Context"
        attributesUUID[UUIDDatabase.UUID_GLUCOSE_FEATURE] = "Glucose Feature"
        attributesUUID[UUIDDatabase.UUID_RECORD_ACCESS_CONTROL_POINT] = "Record Access Control Point"

        // Blood pressure service characteristics
        attributesUUID[UUIDDatabase.UUID_BLOOD_INTERMEDIATE_CUFF_PRESSURE] = "Intermediate Cuff Pressure"
        attributesUUID[UUIDDatabase.UUID_BLOOD_PRESSURE_FEATURE] = "Blood Pressure Feature"
        attributesUUID[UUIDDatabase.UUID_BLOOD_PRESSURE_MEASUREMENT] = "Blood Pressure Measurement"

        // Running Speed Characteristics
        attributesUUID[UUIDDatabase.UUID_RSC_MEASURE] = "Running Speed and Cadence Measurement"
        attributesUUID[UUIDDatabase.UUID_RSC_FEATURE] = "Running Speed and Cadence Feature"
        attributesUUID[UUIDDatabase.UUID_SC_CONTROL_POINT] = "Speed and Cadence Control Point"
        attributesUUID[UUIDDatabase.UUID_SC_SENSOR_LOCATION] = "Speed and Cadence Sensor Location"

        // Cycling Speed Characteristics
        attributesUUID[UUIDDatabase.UUID_CSC_MEASURE] = "Cycling Speed and Cadence Measurement"
        attributesUUID[UUIDDatabase.UUID_CSC_FEATURE] = "Cycling Speed and Cadence Feature"


        // SensorHub Characteristics
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_ANALOG_SENSOR] = "Accelerometer Analog Sensor"
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_DATA_ACCUMULATION] = "Accelerometer Data Accumulation"
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_READING_X] = "Accelerometer X Reading"
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_READING_Y] = "Accelerometer Y Reading"
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_READING_Z] = "Accelerometer Z Reading"
        attributesUUID[UUIDDatabase.UUID_ACCELEROMETER_SENSOR_SCAN_INTERVAL] = "Accelerometer Sensor Scan Interval"
        attributesUUID[UUIDDatabase.UUID_BAROMETER_DATA_ACCUMULATION] = "Barometer Data Accumulation"
        attributesUUID[UUIDDatabase.UUID_BAROMETER_DIGITAL_SENSOR] = "Barometer Digital Sensor"
        attributesUUID[UUIDDatabase.UUID_BAROMETER_READING] = "Barometer Reading"
        attributesUUID[UUIDDatabase.UUID_BAROMETER_SENSOR_SCAN_INTERVAL] = "Barometer Sensor Scan Interval"
        attributesUUID[UUIDDatabase.UUID_BAROMETER_THRESHOLD_FOR_INDICATION] = "Barometer Threshold for Indication"
        attributesUUID[UUIDDatabase.UUID_TEMPERATURE_ANALOG_SENSOR] = "Temperature Analog Sensor"
        attributesUUID[UUIDDatabase.UUID_TEMPERATURE_READING] = "Temperature Reading"
        attributesUUID[UUIDDatabase.UUID_TEMPERATURE_SENSOR_SCAN_INTERVAL] = "Temperature Sensor Scan Interval"

        //HID Characteristics
        attributesUUID[UUIDDatabase.UUID_PROTOCOL_MODE] = "Protocol Mode"
        attributesUUID[UUIDDatabase.UUID_REP0RT] = "Report"
        attributesUUID[UUIDDatabase.UUID_REPORT_MAP] = "Report Map"
        attributesUUID[UUIDDatabase.UUID_BOOT_KEYBOARD_INPUT_REPORT] = "Boot Keyboard Input Report"
        attributesUUID[UUIDDatabase.UUID_BOOT_KEYBOARD_OUTPUT_REPORT] = "Boot Keyboard Output Report"
        attributesUUID[UUIDDatabase.UUID_BOOT_MOUSE_INPUT_REPORT] = "Boot Mouse Input Report"
        attributesUUID[UUIDDatabase.UUID_HID_CONTROL_POINT] = "HID Control Point"
        attributesUUID[UUIDDatabase.UUID_HID_INFORMATION] = "HID Information"

        //OTA Characteristics
        attributesUUID[UUIDDatabase.UUID_OTA_UPDATE_SERVICE] = "Bootloader Service"
        attributesUUID[UUIDDatabase.UUID_OTA_CHARACTERISTIC] = "Bootloader Data Characteristic"

        // Unused Characteristics
        attributesUUID[UUIDDatabase.UUID_AEROBIC_HEART_RATE_LOWER_LIMIT] = "Aerobic Heart Rate Lower Limit"
        attributesUUID[UUIDDatabase.UUID_AEROBIC_HEART_RATE_UPPER_LIMIT] = "Aerobic Heart Rate Upper Limit"
        attributesUUID[UUIDDatabase.UUID_AGE] = "Age"
        attributesUUID[UUIDDatabase.UUID_ALERT_CATEGORY_ID] = "Alert Category Id"
        attributesUUID[UUIDDatabase.UUID_ALERT_CATEGORY_ID_BIT_MASK] = "Alert Category_id_Bit_Mask"
        attributesUUID[UUIDDatabase.UUID_ALERT_STATUS] = "Alert_Status"
        attributesUUID[UUIDDatabase.UUID_ANAEROBIC_HEART_RATE_LOWER_LIMIT] = "Anaerobic Heart Rate Lower Limit"
        attributesUUID[UUIDDatabase.UUID_ANAEROBIC_HEART_RATE_UPPER_LIMIT] = "Anaerobic Heart Rate Upper Limit"
        attributesUUID[UUIDDatabase.UUID_ANAEROBIC_THRESHOLD] = "Anaerobic Threshold"
        attributesUUID[UUIDDatabase.UUID_APPARENT_WIND_DIRECTION] = "Apparent Wind Direction"
        attributesUUID[UUIDDatabase.UUID_APPARENT_WIND_SPEED] = "Apparent Wind Speed"
        attributesUUID[UUIDDatabase.UUID_APPEARANCE] = "Appearance"
        attributesUUID[UUIDDatabase.UUID_BAROMETRIC_PRESSURE_TREND] = "Barometric pressure Trend"
        attributesUUID[UUIDDatabase.UUID_BLOOD_PRESSURE_MEASUREMENT] = "Blood Pressure Measurement"
        attributesUUID[UUIDDatabase.UUID_BODY_COMPOSITION_FEATURE] = "Body Composition Feature"
        attributesUUID[UUIDDatabase.UUID_BODY_COMPOSITION_MEASUREMENT] = "Body Composition Measurement"
        attributesUUID[UUIDDatabase.UUID_BOND_MANAGEMENT_CONTROL_POINT] = "Bond Management Control Point"
        attributesUUID[UUIDDatabase.UUID_BOND_MANAGEMENT_FEATURE] = "Bond Management feature"
        attributesUUID[UUIDDatabase.UUID_CGM_FEATURE] = "CGM Feature"
        attributesUUID[UUIDDatabase.UUID_CENTRAL_ADDRESS_RESOLUTION] = "Central Address Resolution"
        attributesUUID[UUIDDatabase.UUID_FIRSTNAME] = "First Name"
        attributesUUID[UUIDDatabase.UUID_GUST_FACTOR] = "Gust Factor"
        attributesUUID[UUIDDatabase.UUID_CGM_MEASUREMENT] = "CGM Measurement"
        attributesUUID[UUIDDatabase.UUID_CGM_SESSION_RUN_TIME] = "CGM Session Run Time"
        attributesUUID[UUIDDatabase.UUID_CGM_SESSION_START_TIME] = "CGM Session Start Time"
        attributesUUID[UUIDDatabase.UUID_CGM_SPECIFIC_OPS_CONTROL_POINT] = "CGM Specific Ops Control Point"
        attributesUUID[UUIDDatabase.UUID_CGM_STATUS] = "CGM Status"
        attributesUUID[UUIDDatabase.UUID_CYCLING_POWER_CONTROL_POINT] = "Cycling Power Control Point"
        attributesUUID[UUIDDatabase.UUID_CYCLING_POWER_VECTOR] = "Cycling Power Vector"
        attributesUUID[UUIDDatabase.UUID_CYCLING_POWER_FEATURE] = "Cycling Power Feature"
        attributesUUID[UUIDDatabase.UUID_CYCLING_POWER_MEASUREMENT] = "Cycling Power Measurement"
        attributesUUID[UUIDDatabase.UUID_DATABASE_CHANGE_INCREMENT] = "Database Change Increment"
        attributesUUID[UUIDDatabase.UUID_DATE_OF_BIRTH] = "Date Of Birth"
        attributesUUID[UUIDDatabase.UUID_DATE_OF_THRESHOLD_ASSESSMENT] = "Date Of Threshold Assessment"
        attributesUUID[UUIDDatabase.UUID_DATE_TIME] = "Date Time"
        attributesUUID[UUIDDatabase.UUID_DAY_DATE_TIME] = "Day Date Time"
        attributesUUID[UUIDDatabase.UUID_DAY_OF_WEEK] = "Day Of Week"
        attributesUUID[UUIDDatabase.UUID_DESCRIPTOR_VALUE_CHANGED] = "Descriptor Value Changed"
        attributesUUID[UUIDDatabase.UUID_DEVICE_NAME] = "Device Name"
        attributesUUID[UUIDDatabase.UUID_DEW_POINT] = "Dew Point"
        attributesUUID[UUIDDatabase.UUID_DST_OFFSET] = "DST Offset"
        attributesUUID[UUIDDatabase.UUID_ELEVATION] = "Elevation"
        attributesUUID[UUIDDatabase.UUID_EMAIL_ADDRESS] = "Email Address"
        attributesUUID[UUIDDatabase.UUID_EXACT_TIME_256] = "Exact Time 256"
        attributesUUID[UUIDDatabase.UUID_FAT_BURN_HEART_RATE_LOWER_LIMIT] = "Fat Burn Heart Rate lower Limit"
        attributesUUID[UUIDDatabase.UUID_FAT_BURN_HEART_RATE_UPPER_LIMIT] = "Fat Burn Heart Rate Upper Limit"
        attributesUUID[UUIDDatabase.UUID_FIRMWARE_REVISION_STRING] = "Firmware Revision String"
        attributesUUID[UUIDDatabase.UUID_FIVE_ZONE_HEART_RATE_LIMITS] = "Five Zone Heart Rate Limits"
        attributesUUID[UUIDDatabase.UUID_MANUFACTURE_NAME_STRING] = "Manufacturer Name String"
        attributesUUID[UUIDDatabase.UUID_GENDER] = "Gender"
        attributesUUID[UUIDDatabase.UUID_GLUCOSE_FEATURE] = "Glucose Feature"
        attributesUUID[UUIDDatabase.UUID_GLUCOSE_MEASUREMENT] = "Glucose Measurement"
        attributesUUID[UUIDDatabase.UUID_HEART_RATE_MAX] = "Heart Rate Max"
        attributesUUID[UUIDDatabase.UUID_HEAT_INDEX] = "Heat Index"
        attributesUUID[UUIDDatabase.UUID_HEIGHT] = "Height"
        attributesUUID[UUIDDatabase.UUID_HIP_CIRCUMFERENCE] = "Hip Circumference"
        attributesUUID[UUIDDatabase.UUID_HUMIDITY] = "Humidity"
        attributesUUID[UUIDDatabase.UUID_INTERMEDIATE_CUFF_PRESSURE] = "Intermediate Cuff Pressure"
        attributesUUID[UUIDDatabase.UUID_INTERMEDIATE_TEMPERATURE] = "Intermediate Temperature"
        attributesUUID[UUIDDatabase.UUID_IRRADIANCE] = "Irradiance"
        attributesUUID[UUIDDatabase.UUID_LANGUAGE] = "Language"
        attributesUUID[UUIDDatabase.UUID_LAST_NAME] = "Last Name"
        attributesUUID[UUIDDatabase.UUID_LN_CONTROL_POINT] = "LN Control Point"
        attributesUUID[UUIDDatabase.UUID_LN_FEATURE] = "LN Feature"
        attributesUUID[UUIDDatabase.UUID_LOCAL_TIME_INFORMATION] = "Local Time Information"
        attributesUUID[UUIDDatabase.UUID_LOCATION_AND_SPEED] = "Location and Speed"
        attributesUUID[UUIDDatabase.UUID_MAGNETIC_DECLINATION] = "Magenetic Declination"
        attributesUUID[UUIDDatabase.UUID_MAGNETIC_FLUX_DENSITY_2D] = "Magentic Flux Density 2D"
        attributesUUID[UUIDDatabase.UUID_MAGNETIC_FLUX_DENSITY_3D] = "Magentic Flux Density 3D"
        attributesUUID[UUIDDatabase.UUID_MAXIMUM_RECOMMENDED_HEART_RATE] = "Maximum Recommended Heart Rate"
        attributesUUID[UUIDDatabase.UUID_MEASUREMENT_INTERVAL] = "Measurement Interval"
        attributesUUID[UUIDDatabase.UUID_MODEL_NUMBER_STRING] = "Model Number String"
        attributesUUID[UUIDDatabase.UUID_NEW_ALERT] = "New Alert"
        attributesUUID[UUIDDatabase.UUID_NAVIGATION] = "Navigation"
        attributesUUID[UUIDDatabase.UUID_PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS] =
            "Peripheral Preferred Connection Parameters"
        attributesUUID[UUIDDatabase.UUID_PERIPHERAL_PRIVACY_FLAG] = "Peripheral Privacy Flag"
        attributesUUID[UUIDDatabase.UUID_POLLEN_CONCENTRATION] = "Pollen Concentration"
        attributesUUID[UUIDDatabase.UUID_POSITION_QUALITY] = "Position Quality"
        attributesUUID[UUIDDatabase.UUID_PRESSURE] = "Pressure"


        // Descriptors
        attributesUUID[UUIDDatabase.UUID_CHARACTERISTIC_EXTENDED_PROPERTIES] = "Characteristic Extended Properties"
        attributesUUID[UUIDDatabase.UUID_CHARACTERISTIC_USER_DESCRIPTION] = "Characteristic User Description"
        attributesUUID[UUIDDatabase.UUID_CLIENT_CHARACTERISTIC_CONFIG] = "Client Characteristic Configuration"
        attributesUUID[UUIDDatabase.UUID_SERVER_CHARACTERISTIC_CONFIGURATION] = "Server Characteristic Configuration"
        attributesUUID[UUIDDatabase.UUID_CHARACTERISTIC_PRESENTATION_FORMAT] = "Characteristic Presentation Format"
        attributesUUID[UUIDDatabase.UUID_CHARACTERISTIC_AGGREGATE_FORMAT] = "Characteristic Aggregate Format"
        attributesUUID[UUIDDatabase.UUID_VALID_RANGE] = "Valid Range"
        attributesUUID[UUIDDatabase.UUID_EXTERNAL_REPORT_REFERENCE] = "External Report Reference"
        attributesUUID[UUIDDatabase.UUID_REPORT_REFERENCE] = "Report Reference"
        attributesUUID[UUIDDatabase.UUID_ENVIRONMENTAL_SENSING_CONFIGURATION] = "Environmental Sensing Configuration"
        attributesUUID[UUIDDatabase.UUID_ENVIRONMENTAL_SENSING_MEASUREMENT] = "Environmental Sensing Measurement"
        attributesUUID[UUIDDatabase.UUID_ENVIRONMENTAL_SENSING_TRIGGER_SETTING] =
            "Environmental Sensing Trigger Setting"

        //RDK Report Attributes
        rdkAttributesUUID[0] = "Report Mouse"
        rdkAttributesUUID[1] = "Report Keyboard"
        rdkAttributesUUID[2] = "Report Multimedia"
        rdkAttributesUUID[3] = "Report Power"
        rdkAttributesUUID[4] = "Report Audio Control"
        rdkAttributesUUID[5] = "Report Audio Data"


        // Capsense Characteristics
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_SERVICE] = "CapSense Services"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_SERVICE_CUSTOM] = "CapSense Services"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_BUTTONS] = "CapSense Button"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_BUTTONS_CUSTOM] = "CapSense Button"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_PROXIMITY] = "CapSense Proximity"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_PROXIMITY_CUSTOM] = "CapSense Proximity"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_SLIDER] = "CapSense Slider"
        attributesCapSense[UUIDDatabase.UUID_CAPSENSE_SLIDER_CUSTOM] = "CapSense Slider"

        /**
         * Descriptor key value mapping
         */

        descriptorAttributes["0"] = "Reserved For Future Use"
        descriptorAttributes["1"] = "Boolean"
        descriptorAttributes["2"] = "unsigned 2-bit integer"
        descriptorAttributes["3"] = "unsigned 4-bit integer"
        descriptorAttributes["4"] = "unsigned 8-bit integer"
        descriptorAttributes["5"] = "unsigned 12-bit integer"
        descriptorAttributes["6"] = "unsigned 16-bit integer"
        descriptorAttributes["7"] = "unsigned 24-bit integer"
        descriptorAttributes["8"] = "unsigned 32-bit integer"
        descriptorAttributes["9"] = "unsigned 48-bit integer"
        descriptorAttributes["10"] = "unsigned 64-bit integer"
        descriptorAttributes["11"] = "unsigned 128-bit integer"
        descriptorAttributes["12"] = "signed 8-bit integer"
        descriptorAttributes["13"] = "signed 12-bit integer"
        descriptorAttributes["14"] = "signed 16-bit integer"
        descriptorAttributes["15"] = "signed 24-bit integer"
        descriptorAttributes["16"] = "signed 32-bit integer"
        descriptorAttributes["17"] = "signed 48-bit integer"
        descriptorAttributes["18"] = "signed 64-bit integer"
        descriptorAttributes["19"] = "signed 128-bit integer"
        descriptorAttributes["20"] = "IEEE-754 32-bit floating point"
        descriptorAttributes["21"] = "IEEE-754 64-bit floating point"
        descriptorAttributes["22"] = "IEEE-11073 16-bit SFLOAT"
        descriptorAttributes["23"] = "IEEE-11073 32-bit FLOAT"
        descriptorAttributes["24"] = "IEEE-20601 format"
        descriptorAttributes["25"] = "UTF-8 string"
        descriptorAttributes["26"] = "UTF-16 string"
        descriptorAttributes["27"] = "Opaque Structure"

    }

    fun lookupUUID(uuid: UUID, defaultName: String): String {
        val name = attributesUUID[uuid]
        return name ?: defaultName
    }

    fun lookupReferenceRDK(instanceid: Int, defaultName: String): String {
        val name = rdkAttributesUUID[instanceid]
        return name ?: defaultName
    }



    fun lookupNameCapSense(uuid: UUID, defaultName: String): String {
        val name = attributesCapSense[uuid]
        return name ?: defaultName
    }


    fun lookCharacteristicPresentationFormat(key: String): String {
        val value = descriptorAttributes[key]
        return value ?: "Reserved"
    }

}