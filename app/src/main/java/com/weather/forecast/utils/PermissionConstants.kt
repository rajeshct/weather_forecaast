package com.weather.forecast.utils

import android.Manifest

object PermissionConstants {
    val PERMISSION_LOCATION_PARAM = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}