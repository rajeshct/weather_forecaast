package com.weather.forecast.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun checkRuntimePermission(permissions: Array<String>, context: Context): Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}
