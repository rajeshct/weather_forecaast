package com.weather.forecast.ui.root

import android.app.Application
import android.location.Location
import com.weather.forecast.ui.base.BaseViewModel

class RootViewModel(application: Application) : BaseViewModel(application) {

    private var location: Location? = null

    override fun actionAfterHavingException(exception: Throwable) {

    }

    fun setLocation(location: Location?) {
        this.location = location
    }

    fun getCurrentLocation(): Location? {
        return location
    }
}