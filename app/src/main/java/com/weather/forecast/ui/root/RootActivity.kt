package com.weather.forecast.ui.root

import android.os.Bundle
import androidx.lifecycle.Observer
import com.weather.forecast.R
import com.weather.forecast.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RootActivity : BaseActivity() {

    private val rootViewModel: RootViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        rootViewModel.getTriggerEventToView().observe(this, Observer {

        })
    }

}