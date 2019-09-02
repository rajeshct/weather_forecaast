package com.weather.forecast.ui.landing.view_model

import android.app.Application
import com.weather.forecast.ui.base.BaseViewModel
import com.weather.forecast.utils.RETRY

class RetryViewModel(application: Application) : BaseViewModel(application) {
    override fun actionAfterHavingException(exception: Throwable) {

    }

    fun onRetryClick() {
        setActionForUi(RETRY)
    }
}