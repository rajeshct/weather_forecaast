package com.weather.forecast.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class AutoClearValue<T>(fragment: Fragment, var value: T) {
    init {
        val fragmentManager = fragment.fragmentManager
        fragmentManager?.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this)
                }
            }, false
        )
    }

    fun get(): T? {
        return value
    }
}