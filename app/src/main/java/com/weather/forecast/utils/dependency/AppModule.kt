package com.weather.forecast.utils.dependency

import android.util.Log
import com.weather.forecast.BuildConfig
import com.weather.forecast.network.ApiService
import com.weather.forecast.repository.WeatherRepository
import com.weather.forecast.ui.landing.view_model.LandingViewModel
import com.weather.forecast.ui.root.RootViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteDataSourceModule = module {


    // Getting landing view model
    viewModel {
        RootViewModel(androidApplication())
    }

    // Getting debug interceptor
    factory<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("API TAG", it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Getting Ok-http client
    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(get())
            .build()
    }

    // Getting retrofit client
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(ApiService::class.java) }

    // Getting landing view model
    viewModel {
        LandingViewModel(androidApplication(), get())
    }

    // Getting repository instance
    single {
        WeatherRepository(get())
    }

}

/**
 * App Components
 */
val appComponent: List<Module> = listOf(remoteDataSourceModule)