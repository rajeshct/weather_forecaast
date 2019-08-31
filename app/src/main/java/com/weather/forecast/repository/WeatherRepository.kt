package com.weather.forecast.repository

import com.weather.forecast.BuildConfig
import com.weather.forecast.data.response.BaseResponse
import com.weather.forecast.network.ApiService
import retrofit2.Response

class WeatherRepository(private val serviceApi: ApiService) {

    suspend fun getWeatherInfo(location: String): Response<BaseResponse> {
        return serviceApi.getWeatherForeCastAsync(BuildConfig.WEATHER_API_KEY, location, "4")
    }

}