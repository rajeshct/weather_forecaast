package com.weather.forecast.ui.landing.fragments

import android.location.Geocoder
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentLandingBinding
import com.weather.forecast.ui.base.BaseActivity
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.landing.view_model.LandingViewModel
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.*
import kotlinx.android.synthetic.main.fragment_landing.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LandingFragment : BaseFragment<FragmentLandingBinding, LandingViewModel>() {

    private val landingViewModel: LandingViewModel by viewModel()

    private val rootViewModel: RootViewModel by sharedViewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_landing
    }

    override fun getViewModel(): LandingViewModel {
        return landingViewModel
    }

    override fun actionAfterViewCreated() {
        landingViewModel.setRootViewModel(rootViewModel)
        observeFragmentChanges()
        observeActivityChanges()
        callPermission()
    }

    private fun callPermission() {
        if (landingViewModel.getLocation() == null) {
            context?.let {
                if (isInternetConnectionAvailable(it)) {
                    (it as BaseActivity).requestPermission(
                        PermissionConstants.PERMISSION_LOCATION_PARAM,
                        PERMISSION_LOCATION
                        , false
                    )
                } else {
                    findNavController().navigate(R.id.action_landingFragment_to_retryFragment)
                }
            }
        }
    }

    override fun onPermissionGranted(requestCode: Int) {
        getUserLocation()
    }

    private fun getUserLocation() {
        (context as BaseActivity).getUserLocation()
    }

    private fun observeActivityChanges() {
        activity?.let { it ->
            rootViewModel.getTriggerEventToView().observe(it, Observer { action ->
                when (action) {
                    LOCATION_FETCHED -> {
                        rootViewModel.getCurrentLocation()?.let { it ->
                            landingViewModel.actionAfterHavingLocation(
                                it, Geocoder(context, Locale.getDefault())
                            )
                            rootViewModel.setActionForUi(INVALID_ACTION)
                        }
                    }
                }
            })
        }

        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            if (it == SHOW_LOADING) {
                showLoading(false)
                rootViewModel.setActionForUi(INVALID_ACTION)
            }
        })

    }

    override fun showLoading(isImmediateHide: Boolean) {
        if (findNavController().currentDestination?.id != R.id.loadingFragment) {
            val action = LandingFragmentDirections.actionLandingFragmentToLoadingFragment()
            action.isHide = isImmediateHide
            findNavController().navigate(action)
        }
    }

    private fun slideUpAnimation() {
        val changeBounds = androidx.transition.ChangeBounds()
        changeBounds.duration = 1000
        androidx.transition.TransitionManager.beginDelayedTransition(
            constraint_layout, changeBounds
        )
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraint_layout)
        constraintSet.connect(
            R.id.card_view,
            ConstraintSet.TOP,
            R.id.tv_location,
            ConstraintSet.BOTTOM,
            resources.getDimension(R.dimen.margin_from_location).toInt()
        )
        constraintSet.applyTo(constraint_layout)
    }

    private fun observeFragmentChanges() {
        landingViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                SLIDE_UP_ANIMATION -> {
                    slideUpAnimation()
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
    }

}