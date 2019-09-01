package com.weather.forecast.ui.landing.fragments

import android.os.Handler
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentLoadingBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.landing.view_model.LoadingViewModel
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.ERROR_NO_RESPONSE
import com.weather.forecast.utils.INVALID_ACTION
import com.weather.forecast.utils.REFRESH_UI
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadingFragment : BaseFragment<FragmentLoadingBinding>() {
    private val rootViewModel: RootViewModel by sharedViewModel()
    private val loadingViewModel: LoadingViewModel by viewModel()
    private val handler: Handler by inject()
    private var hideLoading = false
    private var isWaitingTimePassed = false

    private val runnable = Runnable {
        if (hideLoading) {
            closeLoading()
            isWaitingTimePassed = true
        }
    }

    override fun actionAfterViewCreated() {
        getBinding().loadingViewModel = loadingViewModel
        observerRootChanges()
        closeLoaderAfterDelay()
    }

    private fun observerRootChanges() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                REFRESH_UI -> {
                    hideLoading = true
                    if (isWaitingTimePassed) {
                        closeLoading()
                    }
                }
                ERROR_NO_RESPONSE -> {
                    findNavController().navigate(R.id.action_loadingFragment_to_retryFragment)
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
    }


    private fun closeLoading() {
        findNavController().popBackStack()
        rootViewModel.setActionForUi(INVALID_ACTION)
        handler.removeCallbacks(runnable)
    }

    private fun closeLoaderAfterDelay() {
        handler.postDelayed(runnable, 1000L)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_loading
    }

}