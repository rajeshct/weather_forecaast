package com.weather.forecast.ui.landing.fragments

import android.location.Geocoder
import android.location.Location
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
        getBinding().executePendingBindings()
        landingViewModel.setRootViewModel(rootViewModel)
        observeFragmentChanges()
        observeActivityChanges()
        callPermission()
    }

    private fun callPermission() {
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

    override fun onPermissionGranted(requestCode: Int) {
        getUserLocation()
    }

    private fun getUserLocation() {
        (context as BaseActivity).getUserLocation()
    }

    override fun currentLocation(location: Location) {
        landingViewModel.actionAfterHavingLocation(
            location, Geocoder(context, Locale.getDefault())
        )
    }

    private fun observeActivityChanges() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                SHOW_LOADING -> {
                    showLoading()
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
    }

    private fun showLoading() {
        findNavController().navigate(R.id.action_landingFragment_to_loadingFragment)
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