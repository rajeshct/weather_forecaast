package com.weather.forecast.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.weather.forecast.BR

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    private lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        this.binding.setVariable(BR.viewModel, getViewModel())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionAfterViewCreated()
    }

    open fun onPermissionGranted(requestCode: Int) {

    }

    open fun showLoading(isImmediateHide: Boolean) {

    }

    abstract fun actionAfterViewCreated()

    abstract fun getLayout(): Int

    fun getBinding(): T {
        return binding
    }

    abstract fun getViewModel(): VM


}