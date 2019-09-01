package com.weather.forecast.ui.landing.fragments

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentRetryBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.INVALID_ACTION
import com.weather.forecast.utils.RETRY
import com.weather.forecast.utils.isInternetConnectionAvailable
import kotlinx.android.synthetic.main.fragment_retry.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RetryFragment : BaseFragment<FragmentRetryBinding>() {

    private val rootViewModel: RootViewModel by sharedViewModel()

    override fun actionAfterViewCreated() {
        getBinding().rootViewModel = rootViewModel
        getBinding().executePendingBindings()
        observeActivityChange()
    }

    private fun observeActivityChange() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                RETRY -> {
                    context?.let {
                        if (isInternetConnectionAvailable(it)) {
                            findNavController().navigate(R.id.action_retryFragment_to_landingFragment)
                            rootViewModel.setActionForUi(INVALID_ACTION)
                        } else {
                            Snackbar.make(
                                constraint_layout,
                                R.string.error_no_internet,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }


    override fun getLayout(): Int {
        return R.layout.fragment_retry
    }

}