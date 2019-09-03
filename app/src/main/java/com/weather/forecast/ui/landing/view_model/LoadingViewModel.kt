package com.weather.forecast.ui.landing.view_model

import android.app.Application
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.weather.forecast.BR
import com.weather.forecast.R
import com.weather.forecast.ui.base.BaseViewModel


@BindingAdapter("loadAnimation")
fun loadAnimation(imageView: ImageView, isShow: Boolean) {
    if (isShow) {
        val rotation = AnimationUtils.loadAnimation(imageView.context, R.anim.loader)
        imageView.startAnimation(rotation)
    } else {
        imageView.clearAnimation()
    }
}

class LoadingViewModel(application: Application) : BaseViewModel(application) {
    @Bindable
    private var showHideAnimation: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.showHideAnimation)
        }

    override fun actionAfterHavingException(exception: Throwable) {
        // No action required here
    }

    fun isShowHideAnimation() = showHideAnimation
}