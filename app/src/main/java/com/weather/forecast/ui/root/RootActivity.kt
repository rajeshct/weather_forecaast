package com.weather.forecast.ui.root

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.weather.forecast.R
import com.weather.forecast.ui.base.BaseActivity
import com.weather.forecast.ui.landing.fragments.RetryFragment
import com.weather.forecast.utils.LOCATION_FETCHED
import org.koin.androidx.viewmodel.ext.android.viewModel


class RootActivity : BaseActivity() {

    private val rootViewModel: RootViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        rootViewModel.getTriggerEventToView().observe(this, Observer {

        })
    }

    override fun onPermissionDenied(requestCode: Int) {
        if (getActiveFragment() !is RetryFragment) {
            findNavController(R.id.nav_host).navigate(R.id.action_landingFragment_to_retryFragment)
        }
    }

    override fun getActiveFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    override fun setLocation(location: Location) {
        rootViewModel.setLocation(location)
        rootViewModel.setActionForUi(LOCATION_FETCHED)
    }
}