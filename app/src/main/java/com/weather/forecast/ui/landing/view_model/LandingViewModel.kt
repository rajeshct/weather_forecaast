package com.weather.forecast.ui.landing.view_model

import android.app.Application
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
import com.weather.forecast.utils.ERROR_NO_RESPONSE
import com.weather.forecast.utils.REFRESH_UI
import com.weather.forecast.utils.SHOW_LOADING
import com.weather.forecast.utils.getDayFromDate
import kotlinx.coroutines.Dispatchers
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
            field = value
            notifyPropertyChanged(BR.currentLocation)
        }

    fun fetchDataFromServer(location: String) {
        if (listingData.isEmpty()) {

            rootViewModel.setActionForUi(SHOW_LOADING)

            launch(coroutineContext) {
                val baseResponse = weatherRepository.getWeatherInfo(location)
                currentTemperature = baseResponse.body()?.current?.feelslikeC
                currentLocation = baseResponse.body()?.location?.region
                updateAdapter(baseResponse.body())
                notifyChange()
                rootViewModel.setActionForUi(REFRESH_UI)
            }

        }
    }

    private suspend fun updateAdapter(body: BaseResponse?) {
        withContext(Dispatchers.IO) {
            val foreCastDay = body?.forecast?.forecastday
            if (foreCastDay != null) {
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

}