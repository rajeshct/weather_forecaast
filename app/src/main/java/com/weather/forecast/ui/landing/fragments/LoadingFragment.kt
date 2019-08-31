package com.weather.forecast.ui.landing.fragments

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentLoadingBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.ERROR_NO_RESPONSE
import com.weather.forecast.utils.INVALID_ACTION
import com.weather.forecast.utils.REFRESH_UI
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoadingFragment : BaseFragment<FragmentLoadingBinding>() {

    val rootViewModel: RootViewModel by sharedViewModel()

    override fun actionAfterViewCreated() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                REFRESH_UI -> {
                    findNavController().popBackStack(R.id.landingFragment, false)
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
                ERROR_NO_RESPONSE -> {
                    findNavController().navigate(R.id.action_loadingFragment_to_retryFragment)
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_loading
    }

}