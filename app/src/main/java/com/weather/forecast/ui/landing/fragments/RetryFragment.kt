package com.weather.forecast.ui.landing.fragments

import androidx.lifecycle.Observer
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentRetryBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.INVALID_ACTION
import com.weather.forecast.utils.RETRY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RetryFragment : BaseFragment<FragmentRetryBinding>() {

    private val rootViewModel: RootViewModel by sharedViewModel()

    override fun actionAfterViewCreated() {
        getBinding().rootViewModel = rootViewModel
        getBinding().executePendingBindings()
        observeActivityChange()
    }

    private fun observeActivityChange() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                RETRY -> {
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
    }


    override fun getLayout(): Int {
        return R.layout.fragment_retry
    }

}