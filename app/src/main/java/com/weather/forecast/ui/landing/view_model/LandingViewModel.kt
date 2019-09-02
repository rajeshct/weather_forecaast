package com.weather.forecast.ui.landing.view_model

import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.widget.LinearLayout
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.forecast.BR
import com.weather.forecast.data.LandingData
import com.weather.forecast.data.response.BaseResponse
import com.weather.forecast.repository.WeatherRepository
import com.weather.forecast.ui.base.BaseViewModel
import com.weather.forecast.ui.landing.adapter.LandingAdapter
import com.weather.forecast.ui.root.RootViewModel
import com.weather.forecast.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@BindingAdapter("setAdapter")
fun setLandingAdapter(recyclerView: RecyclerView, listingData: List<LandingData>?) {
    if (listingData == null) {
        return
    }
    if (recyclerView.adapter == null) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = LandingAdapter(listingData)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                LinearLayout.VERTICAL
            )
        )
    } else {
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

class LandingViewModel(application: Application, private val weatherRepository: WeatherRepository) :
    BaseViewModel(application) {
    private var location: Location? = null
    private val listingData: MutableList<LandingData> = mutableListOf()

    private lateinit var rootViewModel: RootViewModel


    @Bindable
    private
    var currentTemperature: Double? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentTemperature)
        }

    @Bindable
    private
    var currentLocation: String? = null
        set(value) {
            if (!value.equals("")) {
                field = value
                notifyPropertyChanged(BR.currentLocation)
            }
        }

    private fun fetchDataFromServer(location: String) {
        if (listingData.isEmpty()) {
            rootViewModel.setActionForUi(SHOW_LOADING)
            launch(coroutineContext) {
                val baseResponse = weatherRepository.getWeatherInfo(location)
                currentTemperature = baseResponse.current.feelslikeC
                currentLocation = baseResponse.location.region
                updateAdapter(baseResponse)
                notifyChange()
                rootViewModel.setActionForUi(REFRESH_UI)
                delay(1000)
                setActionForUi(SLIDE_UP_ANIMATION)
            }
        }
    }

    private suspend fun updateAdapter(body: BaseResponse) {
        withContext(Dispatchers.IO) {
            val foreCastDay = body.forecast.forecastday
            for (item in foreCastDay) {
                listingData.add(
                    LandingData(
                        getDayFromDate(item.date),
                        item.day.avgtempC
                    )
                )
            }
        }
    }

    override fun actionAfterHavingException(exception: Throwable) {
        rootViewModel.setActionForUi(ERROR_NO_RESPONSE)
    }

    fun getListingData(): List<LandingData> {
        return listingData
    }

    fun getCurrentLocation() = currentLocation

    fun getCurrentTemperature() = currentTemperature

    fun setRootViewModel(rootViewModel: RootViewModel) {
        this.rootViewModel = rootViewModel
    }

    fun actionAfterHavingLocation(location: Location, geocoder: Geocoder) {
        if (this.location == null) {
            fetchDataFromServer("${location.latitude},${location.longitude}")
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    currentLocation = addresses[0].locality
                }
            } catch (e: Exception) {
                // No action required here
            }
        }
        this.location = location
    }

    fun getLocation(): Location? {
        return location
    }

}