package com.weather.forecast.repository

import com.weather.forecast.BuildConfig
import com.weather.forecast.data.response.BaseResponse
import com.weather.forecast.network.ApiService
import com.weather.forecast.utils.API_DAY
import com.weather.forecast.utils.API_FAILURE
import com.weather.forecast.utils.CustomException

class WeatherRepository(private val serviceApi: ApiService) {

    suspend fun getWeatherInfo(location: String): BaseResponse {
        val response =
            serviceApi.getWeatherForeCastAsync(BuildConfig.WEATHER_API_KEY, location, API_DAY)
        if (response.isSuccessful && response.body() != null) {
            val value = response.body()
            if (value != null) {
                return value
            }
        }
        throw CustomException(API_FAILURE, response.errorBody())
    }

}