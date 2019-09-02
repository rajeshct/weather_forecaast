package com.weather.forecast.ui.landing.fragments

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentRetryBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.landing.view_model.RetryViewModel
import com.weather.forecast.utils.RETRY
import com.weather.forecast.utils.isInternetConnectionAvailable
import kotlinx.android.synthetic.main.fragment_retry.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetryFragment : BaseFragment<FragmentRetryBinding, RetryViewModel>() {

    private val retryViewModel: RetryViewModel by viewModel()

    override fun actionAfterViewCreated() {
        observeChange()
    }

    private fun observeChange() {
        retryViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                RETRY -> {
                    context?.let {
                        if (isInternetConnectionAvailable(it)) {
                            openLandingScreen()
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

    private fun openLandingScreen() {
        findNavController().popBackStack()
    }

    override fun getViewModel(): RetryViewModel {
        return retryViewModel
    }

    override fun getLayout(): Int {
        return R.layout.fragment_retry
    }
}