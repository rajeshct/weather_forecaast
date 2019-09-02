package com.weather.forecast.ui.base

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.weather.forecast.utils.PERMISSION_LOCATION
import com.weather.forecast.utils.PermissionConstants
import com.weather.forecast.utils.REQUEST_CODE_LOCATION_FAIL
import com.weather.forecast.utils.checkRuntimePermission
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    private val tempLocationRequest: LocationRequest by inject()
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var requestCode = 0
    private var isShowSettingScreen = false
    lateinit var permissionList: Array<String>

    fun requestPermission(
        permission: Array<String>,
        requestCode: Int,
        isShowSettingScreen: Boolean
    ) {
        this.requestCode = requestCode
        this.isShowSettingScreen = isShowSettingScreen
        this.permissionList = permission
        if (checkRuntimePermission(
                PermissionConstants.PERMISSION_LOCATION_PARAM,
                this
            )
        ) {
            onPermissionGranted(requestCode)
        } else {
            ActivityCompat.requestPermissions(this, permission, requestCode)
        }

    }

    open fun onPermissionGranted(requestCode: Int) {
        if (getActiveFragment() is BaseFragment<*, *>) {
            (getActiveFragment() as BaseFragment<*, *>).onPermissionGranted(requestCode)
        }
    }

    private fun setLocation(location: Location) {
        if (getActiveFragment() is BaseFragment<*, *>) {
            (getActiveFragment() as BaseFragment<*, *>).currentLocation(location)
        }
    }

    open fun onPermissionDenied(requestCode: Int) {
    }

    open fun getActiveFragment(): Fragment? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_LOCATION -> {
                var isAllPermissionGranted = true
                var isOpenSettingScreen = false
                for ((key, value) in grantResults.withIndex()) {
                    if (value == PackageManager.PERMISSION_DENIED) {
                        isAllPermissionGranted = false
                        val showRational = shouldShowRequestPermissionRationale(permissions[key])
                        if (!showRational) {
                            isOpenSettingScreen = true
                        }
                    }
                }
                if (isAllPermissionGranted) {
                    onPermissionGranted(requestCode)
                } else {
                    onPermissionDenied(requestCode)
                    if (isOpenSettingScreen && isShowSettingScreen) {
                        startSettingScreen()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::permissionList.isInitialized) {
            if (checkRuntimePermission(permissionList, this)) {
                onPermissionGranted(requestCode)
            }
        }
    }

    private fun startSettingScreen() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            if (p0 != null) {
                setLocation(p0.lastLocation)
                removeLocationUpdate()
            }
        }
    }


    fun getUserLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val builder = LocationSettingsRequest.Builder().addLocationRequest(tempLocationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client?.checkLocationSettings(builder.build())
        task?.addOnSuccessListener {
            requestLocation()
        }
        task?.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    val resolvable = it
                    resolvable.startResolutionForResult(
                        this,
                        REQUEST_CODE_LOCATION_FAIL
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun requestLocation() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
            if (it == null) {
                getUpdatedLocation()
            } else {
                setLocation(it)
                removeLocationUpdate()
            }
        }?.addOnFailureListener {
            getUpdatedLocation()
        }
    }

    private fun getUpdatedLocation() {
        fusedLocationProviderClient?.requestLocationUpdates(
            tempLocationRequest,
            locationCallback,
            null
        )
    }

    private fun removeLocationUpdate() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }
}
