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

import java.util.UUID

/**
 * This class will store the UUID of the GATT services and characteristics
 */
object UUIDDatabase {
    /**
     * Heart rate related UUID
     */
    val UUID_HEART_RATE_SERVICE = UUID
        .fromString(GattAttributes.HEART_RATE_SERVICE)
    val UUID_HEART_RATE_MEASUREMENT = UUID
        .fromString(GattAttributes.HEART_RATE_MEASUREMENT)
    val UUID_BODY_SENSOR_LOCATION = UUID
        .fromString(GattAttributes.BODY_SENSOR_LOCATION)
    /**
     * Device information related UUID
     */
    val UUID_DEVICE_INFORMATION_SERVICE = UUID
        .fromString(GattAttributes.DEVICE_INFORMATION_SERVICE)
    val UUID_SYSTEM_ID = UUID
        .fromString(GattAttributes.SYSTEM_ID)
    val UUID_MANUFACTURE_NAME_STRING = UUID
        .fromString(GattAttributes.MANUFACTURER_NAME_STRING)
    val UUID_MODEL_NUMBER_STRING = UUID
        .fromString(GattAttributes.MODEL_NUMBER_STRING)
    val UUID_SERIAL_NUMBER_STRING = UUID
        .fromString(GattAttributes.SERIAL_NUMBER_STRING)
    val UUID_HARDWARE_REVISION_STRING = UUID
        .fromString(GattAttributes.HARDWARE_REVISION_STRING)
    val UUID_FIRMWARE_REVISION_STRING = UUID
        .fromString(GattAttributes.FIRMWARE_REVISION_STRING)
    val UUID_SOFTWARE_REVISION_STRING = UUID
        .fromString(GattAttributes.SOFTWARE_REVISION_STRING)
    val UUID_PNP_ID = UUID
        .fromString(GattAttributes.PNP_ID)
    val UUID_IEEE = UUID
        .fromString(GattAttributes.IEEE)

    /**
     * Health thermometer related UUID
     */
    val UUID_HEALTH_THERMOMETER_SERVICE = UUID
        .fromString(GattAttributes.HEALTH_THERMOMETER_SERVICE)
    val UUID_HEALTH_THERMOMETER = UUID
        .fromString(GattAttributes.HEALTH_TEMP_MEASUREMENT)
    val UUID_HEALTH_THERMOMETER_SENSOR_LOCATION = UUID
        .fromString(GattAttributes.TEMPERATURE_TYPE)
    /**
     * Battery Level related uuid
     */
    val UUID_BATTERY_SERVICE = UUID
        .fromString(GattAttributes.BATTERY_SERVICE)
    val UUID_BATTERY_LEVEL = UUID
        .fromString(GattAttributes.BATTERY_LEVEL)

    /**
     * Find me related uuid
     */
    val UUID_IMMEDIATE_ALERT_SERVICE = UUID
        .fromString(GattAttributes.IMMEDIATE_ALERT_SERVICE)
    val UUID_TRANSMISSION_POWER_SERVICE = UUID
        .fromString(GattAttributes.TRANSMISSION_POWER_SERVICE)
    val UUID_ALERT_LEVEL = UUID
        .fromString(GattAttributes.ALERT_LEVEL)
    val UUID_TRANSMISSION_POWER_LEVEL = UUID
        .fromString(GattAttributes.TRANSMISSION_POWER_LEVEL)
    val UUID_LINK_LOSS_SERVICE = UUID
        .fromString(GattAttributes.LINK_LOSS_SERVICE)

    /**
     * CapSense related uuid
     */
    val UUID_CAPSENSE_SERVICE = UUID
        .fromString(GattAttributes.CAPSENSE_SERVICE)
    val UUID_CAPSENSE_SERVICE_CUSTOM = UUID
        .fromString(GattAttributes.CAPSENSE_SERVICE_CUSTOM)
    val UUID_CAPSENSE_PROXIMITY = UUID
        .fromString(GattAttributes.CAPSENSE_PROXIMITY)
    val UUID_CAPSENSE_SLIDER = UUID
        .fromString(GattAttributes.CAPSENSE_SLIDER)
    val UUID_CAPSENSE_BUTTONS = UUID
        .fromString(GattAttributes.CAPSENSE_BUTTONS)
    val UUID_CAPSENSE_PROXIMITY_CUSTOM = UUID
        .fromString(GattAttributes.CAPSENSE_PROXIMITY_CUSTOM)
    val UUID_CAPSENSE_SLIDER_CUSTOM = UUID
        .fromString(GattAttributes.CAPSENSE_SLIDER_CUSTOM)
    val UUID_CAPSENSE_BUTTONS_CUSTOM = UUID
        .fromString(GattAttributes.CAPSENSE_BUTTONS_CUSTOM)
    /**
     * RGB LED related uuid
     */
    val UUID_RGB_LED_SERVICE = UUID
        .fromString(GattAttributes.RGB_LED_SERVICE)
    val UUID_RGB_LED = UUID
        .fromString(GattAttributes.RGB_LED)
    val UUID_RGB_LED_SERVICE_CUSTOM = UUID
        .fromString(GattAttributes.RGB_LED_SERVICE_CUSTOM)
    val UUID_RGB_LED_CUSTOM = UUID
        .fromString(GattAttributes.RGB_LED_CUSTOM)

    /**
     * GlucoseService related uuid
     */
    val UUID_GLUCOSE_MEASUREMENT = UUID
        .fromString(GattAttributes.GLUCOSE_MEASUREMENT)
    val UUID_GLUCOSE_SERVICE = UUID
        .fromString(GattAttributes.GLUCOSE_SERVICE)
    val UUID_GLUCOSE_MEASUREMENT_CONTEXT = UUID
        .fromString(GattAttributes.GLUCOSE_MEASUREMENT_CONTEXT)
    val UUID_GLUCOSE_FEATURE = UUID
        .fromString(GattAttributes.GLUCOSE_FEATURE)
    val UUID_RECORD_ACCESS_CONTROL_POINT = UUID
        .fromString(GattAttributes.RECORD_ACCESS_CONTROL_POINT)
    /**
     * Blood pressure related uuid
     */
    val UUID_BLOOD_PRESSURE_SERVICE = UUID
        .fromString(GattAttributes.BLOOD_PRESSURE_SERVICE)
    val UUID_BLOOD_PRESSURE_MEASUREMENT = UUID
        .fromString(GattAttributes.BLOOD_PRESSURE_MEASUREMENT)
    val UUID_BLOOD_INTERMEDIATE_CUFF_PRESSURE = UUID
        .fromString(GattAttributes.BLOOD_INTERMEDIATE_CUFF_PRESSURE)
    val UUID_BLOOD_PRESSURE_FEATURE = UUID
        .fromString(GattAttributes.BLOOD_PRESSURE_FEATURE)
    /**
     * Running Speed & Cadence related uuid
     */
    val UUID_RSC_MEASURE = UUID
        .fromString(GattAttributes.RSC_MEASUREMENT)
    val UUID_RSC_SERVICE = UUID
        .fromString(GattAttributes.RSC_SERVICE)
    val UUID_RSC_FEATURE = UUID
        .fromString(GattAttributes.RSC_FEATURE)
    val UUID_SC_CONTROL_POINT = UUID
        .fromString(GattAttributes.SC_CONTROL_POINT)
    val UUID_SC_SENSOR_LOCATION = UUID
        .fromString(GattAttributes.SC_SENSOR_LOCATION)


    /**
     * Cycling Speed & Cadence related uuid
     */
    val UUID_CSC_SERVICE = UUID
        .fromString(GattAttributes.CSC_SERVICE)
    val UUID_CSC_MEASURE = UUID
        .fromString(GattAttributes.CSC_MEASUREMENT)
    val UUID_CSC_FEATURE = UUID
        .fromString(GattAttributes.CSC_FEATURE)

    /**
     * Barometer related uuid
     */
    val UUID_BAROMETER_SERVICE = UUID
        .fromString(GattAttributes.BAROMETER_SERVICE)
    val UUID_BAROMETER_DIGITAL_SENSOR = UUID
        .fromString(GattAttributes.BAROMETER_DIGITAL_SENSOR)
    val UUID_BAROMETER_SENSOR_SCAN_INTERVAL = UUID
        .fromString(GattAttributes.BAROMETER_SENSOR_SCAN_INTERVAL)
    val UUID_BAROMETER_THRESHOLD_FOR_INDICATION = UUID
        .fromString(GattAttributes.BAROMETER_THRESHOLD_FOR_INDICATION)
    val UUID_BAROMETER_DATA_ACCUMULATION = UUID
        .fromString(GattAttributes.BAROMETER_DATA_ACCUMULATION)
    val UUID_BAROMETER_READING = UUID
        .fromString(GattAttributes.BAROMETER_READING)
    /**
     * Accelerometer related uuid
     */
    val UUID_ACCELEROMETER_SERVICE = UUID
        .fromString(GattAttributes.ACCELEROMETER_SERVICE)
    val UUID_ACCELEROMETER_ANALOG_SENSOR = UUID
        .fromString(GattAttributes.ACCELEROMETER_ANALOG_SENSOR)
    val UUID_ACCELEROMETER_DATA_ACCUMULATION = UUID
        .fromString(GattAttributes.ACCELEROMETER_DATA_ACCUMULATION)
    val UUID_ACCELEROMETER_READING_X = UUID
        .fromString(GattAttributes.ACCELEROMETER_READING_X)
    val UUID_ACCELEROMETER_READING_Y = UUID
        .fromString(GattAttributes.ACCELEROMETER_READING_Y)
    val UUID_ACCELEROMETER_READING_Z = UUID
        .fromString(GattAttributes.ACCELEROMETER_READING_Z)
    val UUID_ACCELEROMETER_SENSOR_SCAN_INTERVAL = UUID
        .fromString(GattAttributes.ACCELEROMETER_SENSOR_SCAN_INTERVAL)
    /**
     * Analog temperature  related uuid
     */
    val UUID_ANALOG_TEMPERATURE_SERVICE = UUID
        .fromString(GattAttributes.ANALOG_TEMPERATURE_SERVICE)
    val UUID_TEMPERATURE_ANALOG_SENSOR = UUID
        .fromString(GattAttributes.TEMPERATURE_ANALOG_SENSOR)
    val UUID_TEMPERATURE_READING = UUID
        .fromString(GattAttributes.TEMPERATURE_READING)
    val UUID_TEMPERATURE_SENSOR_SCAN_INTERVAL = UUID
        .fromString(GattAttributes.TEMPERATURE_SENSOR_SCAN_INTERVAL)

    /**
     * RDK related UUID
     */
    val UUID_REP0RT = UUID
        .fromString(GattAttributes.REP0RT)

    /**
     * OTA related UUID
     */
    val UUID_OTA_UPDATE_SERVICE = UUID
        .fromString(GattAttributes.OTA_UPDATE_SERVICE)
    val UUID_OTA_UPDATE_CHARACTERISTIC = UUID
        .fromString(GattAttributes.OTA_CHARACTERISTIC)

    /**
     * Descriptor UUID
     */
    val UUID_CLIENT_CHARACTERISTIC_CONFIG = UUID
        .fromString(GattAttributes.CLIENT_CHARACTERISTIC_CONFIG)
    val UUID_CHARACTERISTIC_EXTENDED_PROPERTIES = UUID
        .fromString(GattAttributes.CHARACTERISTIC_EXTENDED_PROPERTIES)
    val UUID_CHARACTERISTIC_USER_DESCRIPTION = UUID
        .fromString(GattAttributes.CHARACTERISTIC_USER_DESCRIPTION)
    val UUID_SERVER_CHARACTERISTIC_CONFIGURATION = UUID
        .fromString(GattAttributes.SERVER_CHARACTERISTIC_CONFIGURATION)
    val UUID_REPORT_REFERENCE = UUID
        .fromString(GattAttributes.REPORT_REFERENCE)
    val UUID_CHARACTERISTIC_PRESENTATION_FORMAT = UUID
        .fromString(GattAttributes.CHARACTERISTIC_PRESENTATION_FORMAT)

    /**
     * GATT related UUID
     */
    val UUID_GENERIC_ACCESS_SERVICE = UUID
        .fromString(GattAttributes.GENERIC_ACCESS_SERVICE)
    val UUID_GENERIC_ATTRIBUTE_SERVICE = UUID
        .fromString(GattAttributes.GENERIC_ATTRIBUTE_SERVICE)

    /**
     * HID UUID
     */
    val UUID_HID_SERVICE = UUID
        .fromString(GattAttributes.HUMAN_INTERFACE_DEVICE_SERVICE)
    val UUID_PROTOCOL_MODE = UUID
        .fromString(GattAttributes.PROTOCOL_MODE)
    val UUID_REPORT = UUID
        .fromString(GattAttributes.REP0RT)
    val UUID_REPORT_MAP = UUID
        .fromString(GattAttributes.REPORT_MAP)
    val UUID_BOOT_KEYBOARD_INPUT_REPORT = UUID
        .fromString(GattAttributes.BOOT_KEYBOARD_INPUT_REPORT)
    val UUID_BOOT_KEYBOARD_OUTPUT_REPORT = UUID
        .fromString(GattAttributes.BOOT_KEYBOARD_OUTPUT_REPORT)
    val UUID_BOOT_MOUSE_INPUT_REPORT = UUID
        .fromString(GattAttributes.BOOT_MOUSE_INPUT_REPORT)
    val UUID_HID_CONTROL_POINT = UUID
        .fromString(GattAttributes.HID_CONTROL_POINT)
    val UUID_HID_INFORMATION = UUID
        .fromString(GattAttributes.HID_INFORMATION)
    val UUID_OTA_CHARACTERISTIC = UUID
        .fromString(GattAttributes.OTA_CHARACTERISTIC)

    /**
     * Alert Notification UUID
     */
    val UUID_ALERT_NOTIFICATION_SERVICE = UUID
        .fromString(GattAttributes.ALERT_NOTIFICATION_SERVICE)
    /**
     * Unused Service UUID's
     */
    val UUID_BODY_COMPOSITION_SERVICE = UUID
        .fromString(GattAttributes.BODY_COMPOSITION_SERVICE)
    val UUID_BOND_MANAGEMENT_SERVICE = UUID
        .fromString(GattAttributes.BOND_MANAGEMENT_SERVICE)
    val UUID_CONTINUOUS_GLUCOSE_MONITORING_SERVICE = UUID
        .fromString(GattAttributes.CONTINUOUS_GLUCOSE_MONITORING_SERVICE)
    val UUID_CURRENT_TIME_SERVICE = UUID
        .fromString(GattAttributes.CURRENT_TIME_SERVICE)
    val UUID_CYCLING_POWER_SERVICE = UUID
        .fromString(GattAttributes.CYCLING_POWER_SERVICE)
    val UUID_ENVIRONMENTAL_SENSING_SERVICE = UUID
        .fromString(GattAttributes.ENVIRONMENTAL_SENSING_SERVICE)
    val UUID_LOCATION_NAVIGATION_SERVICE = UUID
        .fromString(GattAttributes.LOCATION_NAVIGATION_SERVICE)
    val UUID_NEXT_DST_CHANGE_SERVICE = UUID
        .fromString(GattAttributes.NEXT_DST_CHANGE_SERVICE)
    val UUID_PHONE_ALERT_STATUS_SERVICE = UUID
        .fromString(GattAttributes.PHONE_ALERT_STATUS_SERVICE)
    val UUID_REFERENCE_TIME_UPDATE_SERVICE = UUID
        .fromString(GattAttributes.REFERENCE_TIME_UPDATE_SERVICE)
    val UUID_SCAN_PARAMETERS_SERVICE = UUID
        .fromString(GattAttributes.SCAN_PARAMETERS_SERVICE)
    val UUID_USER_DATA_SERVICE = UUID
        .fromString(GattAttributes.USER_DATA_SERVICE)
    val UUID_WEIGHT_SCALE_SERVICE = UUID
        .fromString(GattAttributes.WEIGHT_SCALE_SERVICE)
    val UUID_HEART_RATE_CONTROL_POINT = UUID
        .fromString(GattAttributes.HEART_RATE_CONTROL_POINT)
    val UUID_TEMPERATURE_INTERMEDIATE = UUID
        .fromString(GattAttributes.TEMPERATURE_INTERMEDIATE)
    val UUID_TEMPERATURE_MEASUREMENT_INTERVAL = UUID
        .fromString(GattAttributes.TEMPERATURE_MEASUREMENT_INTERVAL)


    /**
     * Unused Characteristic UUID's
     */
    val UUID_AEROBIC_HEART_RATE_LOWER_LIMIT = UUID
        .fromString(GattAttributes.AEROBIC_HEART_RATE_LOWER_LIMIT)
    val UUID_AEROBIC_HEART_RATE_UPPER_LIMIT = UUID
        .fromString(GattAttributes.AEROBIC_HEART_RATE_UPPER_LIMIT)
    val UUID_AGE = UUID
        .fromString(GattAttributes.AGE)
    val UUID_ALERT_CATEGORY_ID = UUID
        .fromString(GattAttributes.ALERT_CATEGORY_ID)
    val UUID_ALERT_CATEGORY_ID_BIT_MASK = UUID
        .fromString(GattAttributes.ALERT_CATEGORY_ID_BIT_MASK)
    val UUID_ALERT_STATUS = UUID
        .fromString(GattAttributes.ALERT_STATUS)
    val UUID_ANAEROBIC_HEART_RATE_LOWER_LIMIT = UUID
        .fromString(GattAttributes.ANAEROBIC_HEART_RATE_LOWER_LIMIT)
    val UUID_ANAEROBIC_HEART_RATE_UPPER_LIMIT = UUID
        .fromString(GattAttributes.ANAEROBIC_HEART_RATE_UPPER_LIMIT)
    val UUID_ANAEROBIC_THRESHOLD = UUID
        .fromString(GattAttributes.ANAEROBIC_THRESHOLD)
    val UUID_APPARENT_WIND_DIRECTION = UUID
        .fromString(GattAttributes.APPARENT_WIND_DIRECTION)
    val UUID_APPARENT_WIND_SPEED = UUID
        .fromString(GattAttributes.APPARENT_WIND_SPEED)
    val UUID_APPEARANCE = UUID
        .fromString(GattAttributes.APPEARANCE)
    val UUID_BAROMETRIC_PRESSURE_TREND = UUID
        .fromString(GattAttributes.BAROMETRIC_PRESSURE_TREND)
    val UUID_BODY_COMPOSITION_FEATURE = UUID
        .fromString(GattAttributes.BODY_COMPOSITION_FEATURE)
    val UUID_BODY_COMPOSITION_MEASUREMENT = UUID
        .fromString(GattAttributes.BODY_COMPOSITION_MEASUREMENT)
    val UUID_BOND_MANAGEMENT_CONTROL_POINT = UUID
        .fromString(GattAttributes.BOND_MANAGEMENT_CONTROL_POINT)
    val UUID_BOND_MANAGEMENT_FEATURE = UUID
        .fromString(GattAttributes.BOND_MANAGEMENT_FEATURE)
    val UUID_CGM_FEATURE = UUID
        .fromString(GattAttributes.CGM_FEATURE)
    val UUID_CENTRAL_ADDRESS_RESOLUTION = UUID
        .fromString(GattAttributes.CENTRAL_ADDRESS_RESOLUTION)
    val UUID_FIRSTNAME = UUID
        .fromString(GattAttributes.FIRSTNAME)
    val UUID_GUST_FACTOR = UUID
        .fromString(GattAttributes.GUST_FACTOR)
    val UUID_CGM_MEASUREMENT = UUID
        .fromString(GattAttributes.CGM_MEASUREMENT)
    val UUID_CGM_SESSION_RUN_TIME = UUID
        .fromString(GattAttributes.CGM_SESSION_RUN_TIME)
    val UUID_CGM_SESSION_START_TIME = UUID
        .fromString(GattAttributes.CGM_SESSION_START_TIME)
    val UUID_CGM_SPECIFIC_OPS_CONTROL_POINT = UUID
        .fromString(GattAttributes.CGM_SPECIFIC_OPS_CONTROL_POINT)
    val UUID_CGM_STATUS = UUID
        .fromString(GattAttributes.CGM_STATUS)
    val UUID_CYCLING_POWER_CONTROL_POINT = UUID
        .fromString(GattAttributes.CYCLING_POWER_CONTROL_POINT)
    val UUID_CYCLING_POWER_VECTOR = UUID
        .fromString(GattAttributes.CYCLING_POWER_VECTOR)
    val UUID_CYCLING_POWER_FEATURE = UUID
        .fromString(GattAttributes.CYCLING_POWER_FEATURE)
    val UUID_CYCLING_POWER_MEASUREMENT = UUID
        .fromString(GattAttributes.CYCLING_POWER_MEASUREMENT)
    val UUID_DATABASE_CHANGE_INCREMENT = UUID
        .fromString(GattAttributes.DATABASE_CHANGE_INCREMENT)
    val UUID_DATE_OF_BIRTH = UUID
        .fromString(GattAttributes.DATE_OF_BIRTH)
    val UUID_DATE_OF_THRESHOLD_ASSESSMENT = UUID
        .fromString(GattAttributes.DATE_OF_THRESHOLD_ASSESSMENT)
    val UUID_DATE_TIME = UUID
        .fromString(GattAttributes.DATE_TIME)
    val UUID_DAY_DATE_TIME = UUID
        .fromString(GattAttributes.DAY_DATE_TIME)
    val UUID_DAY_OF_WEEK = UUID
        .fromString(GattAttributes.DAY_OF_WEEK)
    val UUID_DESCRIPTOR_VALUE_CHANGED = UUID
        .fromString(GattAttributes.DESCRIPTOR_VALUE_CHANGED)
    val UUID_DEVICE_NAME = UUID
        .fromString(GattAttributes.DEVICE_NAME)
    val UUID_DEW_POINT = UUID
        .fromString(GattAttributes.DEW_POINT)
    val UUID_DST_OFFSET = UUID
        .fromString(GattAttributes.DST_OFFSET)
    val UUID_ELEVATION = UUID
        .fromString(GattAttributes.ELEVATION)
    val UUID_EMAIL_ADDRESS = UUID
        .fromString(GattAttributes.EMAIL_ADDRESS)
    val UUID_EXACT_TIME_256 = UUID
        .fromString(GattAttributes.EXACT_TIME_256)
    val UUID_FAT_BURN_HEART_RATE_LOWER_LIMIT = UUID
        .fromString(GattAttributes.FAT_BURN_HEART_RATE_LOWER_LIMIT)
    val UUID_FAT_BURN_HEART_RATE_UPPER_LIMIT = UUID
        .fromString(GattAttributes.FAT_BURN_HEART_RATE_UPPER_LIMIT)
    val UUID_FIVE_ZONE_HEART_RATE_LIMITS = UUID
        .fromString(GattAttributes.FIVE_ZONE_HEART_RATE_LIMITS)
    val UUID_GENDER = UUID
        .fromString(GattAttributes.GENDER)
    val UUID_HEART_RATE_MAX = UUID
        .fromString(GattAttributes.HEART_RATE_MAX)
    val UUID_HEAT_INDEX = UUID
        .fromString(GattAttributes.HEAT_INDEX)
    val UUID_HEIGHT = UUID
        .fromString(GattAttributes.HEIGHT)
    val UUID_HIP_CIRCUMFERENCE = UUID
        .fromString(GattAttributes.HIP_CIRCUMFERENCE)
    val UUID_HUMIDITY = UUID
        .fromString(GattAttributes.HUMIDITY)
    val UUID_INTERMEDIATE_CUFF_PRESSURE = UUID
        .fromString(GattAttributes.INTERMEDIATE_CUFF_PRESSURE)
    val UUID_INTERMEDIATE_TEMPERATURE = UUID
        .fromString(GattAttributes.INTERMEDIATE_TEMPERATURE)
    val UUID_IRRADIANCE = UUID
        .fromString(GattAttributes.IRRADIANCE)
    val UUID_LANGUAGE = UUID
        .fromString(GattAttributes.LANGUAGE)
    val UUID_LAST_NAME = UUID
        .fromString(GattAttributes.LAST_NAME)
    val UUID_LN_CONTROL_POINT = UUID
        .fromString(GattAttributes.LN_CONTROL_POINT)
    val UUID_LN_FEATURE = UUID
        .fromString(GattAttributes.LN_FEATURE)
    val UUID_LOCAL_TIME_INFORMATION = UUID
        .fromString(GattAttributes.LOCAL_TIME_INFORMATION)
    val UUID_LOCATION_AND_SPEED = UUID
        .fromString(GattAttributes.LOCATION_AND_SPEED)
    val UUID_MAGNETIC_DECLINATION = UUID
        .fromString(GattAttributes.MAGNETIC_DECLINATION)
    val UUID_MAGNETIC_FLUX_DENSITY_2D = UUID
        .fromString(GattAttributes.MAGNETIC_FLUX_DENSITY_2D)
    val UUID_MAGNETIC_FLUX_DENSITY_3D = UUID
        .fromString(GattAttributes.MAGNETIC_FLUX_DENSITY_3D)
    val UUID_MAXIMUM_RECOMMENDED_HEART_RATE = UUID
        .fromString(GattAttributes.MAXIMUM_RECOMMENDED_HEART_RATE)
    val UUID_MEASUREMENT_INTERVAL = UUID
        .fromString(GattAttributes.MEASUREMENT_INTERVAL)
    val UUID_NEW_ALERT = UUID
        .fromString(GattAttributes.NEW_ALERT)
    val UUID_NAVIGATION = UUID
        .fromString(GattAttributes.NAVIGATION)
    val UUID_PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS = UUID
        .fromString(GattAttributes.PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS)
    val UUID_PERIPHERAL_PRIVACY_FLAG = UUID
        .fromString(GattAttributes.PERIPHERAL_PRIVACY_FLAG)
    val UUID_POLLEN_CONCENTRATION = UUID
        .fromString(GattAttributes.POLLEN_CONCENTRATION)
    val UUID_POSITION_QUALITY = UUID
        .fromString(GattAttributes.POSITION_QUALITY)
    val UUID_PRESSURE = UUID
        .fromString(GattAttributes.PRESSURE)

    // Descriptors UUID's
    val UUID_CHARACTERISTIC_AGGREGATE_FORMAT = UUID
        .fromString(GattAttributes.CHARACTERISTIC_AGGREGATE_FORMAT)
    val UUID_VALID_RANGE = UUID
        .fromString(GattAttributes.VALID_RANGE)
    val UUID_EXTERNAL_REPORT_REFERENCE = UUID
        .fromString(GattAttributes.EXTERNAL_REPORT_REFERENCE)
    val UUID_ENVIRONMENTAL_SENSING_CONFIGURATION = UUID
        .fromString(GattAttributes.ENVIRONMENTAL_SENSING_CONFIGURATION)
    val UUID_ENVIRONMENTAL_SENSING_MEASUREMENT = UUID
        .fromString(GattAttributes.ENVIRONMENTAL_SENSING_MEASUREMENT)
    val UUID_ENVIRONMENTAL_SENSING_TRIGGER_SETTING = UUID
        .fromString(GattAttributes.ENVIRONMENTAL_SENSING_TRIGGER_SETTING)

}
