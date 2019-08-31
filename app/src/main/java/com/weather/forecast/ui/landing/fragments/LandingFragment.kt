package com.weather.forecast.ui.landing.fragments

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentLandingBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.landing.view_model.LandingViewModel
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.INVALID_ACTION
import com.weather.forecast.utils.REFRESH_UI
import com.weather.forecast.utils.SHOW_LOADING
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingFragment : BaseFragment<FragmentLandingBinding>() {

    private val landingViewModel: LandingViewModel by viewModel()

    private val rootViewModel: RootViewModel by sharedViewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_landing
    }

    override fun actionAfterViewCreated() {
        getBinding().landingViewModel = landingViewModel
        getBinding().executePendingBindings()
        landingViewModel.setRootViewModel(rootViewModel)
        observeFragmentChanges()
        observeActivityChanges()
    }

    private fun observeActivityChanges() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                REFRESH_UI -> {
                    findNavController().popBackStack()
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
                SHOW_LOADING -> {
                    findNavController().navigate(R.id.action_landingFragment_to_loadingFragment)
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
    }

    private fun observeFragmentChanges() {
        landingViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {

        })
    }

}