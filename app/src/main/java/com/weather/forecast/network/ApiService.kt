package com.weather.forecast.network

import com.weather.forecast.data.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json")
    suspend fun getWeatherForeCastAsync(
        @Query("key") apiKey: String, @Query("q") query: String, @Query(
            "days"
        ) days: String
    ): Response<BaseResponse>

}