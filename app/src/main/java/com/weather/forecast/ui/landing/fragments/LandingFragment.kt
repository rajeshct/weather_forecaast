package com.weather.forecast.ui.landing.fragments

import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.weather.forecast.R
import com.weather.forecast.databinding.FragmentLandingBinding
import com.weather.forecast.ui.base.BaseFragment
import com.weather.forecast.ui.landing.view_model.LandingViewModel
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.INVALID_ACTION
import com.weather.forecast.utils.SHOW_LOADING
import com.weather.forecast.utils.SLIDE_UP_ANIMATION
import kotlinx.android.synthetic.main.fragment_landing.*
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
        landingViewModel.fetchDataFromServer("Bengaluru")
    }


    private fun observeActivityChanges() {
        rootViewModel.getTriggerEventToView().observe(viewLifecycleOwner, Observer {
            when (it) {
                SHOW_LOADING -> {
                    findNavController().navigate(R.id.action_landingFragment_to_loadingFragment)
                    rootViewModel.setActionForUi(INVALID_ACTION)
                }
            }
        })
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