package com.weather.forecast.ui.base

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application)
    , Observable, CoroutineScope {

    private val propertyRegistry = PropertyChangeRegistry()
    private val triggerEventToView = MutableLiveData<Int>()

    private val handler = CoroutineExceptionHandler { _, exception ->
        actionAfterHavingException(exception)
    }

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + handler

    abstract fun actionAfterHavingException(exception: Throwable)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyRegistry.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyRegistry.add(callback)
    }

    protected fun notifyPropertyChanged(id: Int) {
        propertyRegistry.notifyCallbacks(this, id, null)
    }

    protected fun notifyChange() {
        propertyRegistry.notifyCallbacks(this, 0, null)
    }

    fun setActionForUi(action: Int) {
        triggerEventToView.postValue(action)
    }

    fun getTriggerEventToView(): MutableLiveData<Int> {
        return triggerEventToView
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}