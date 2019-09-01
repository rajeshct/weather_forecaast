package com.weather.forecast.utils

import okhttp3.ResponseBody

class CustomException(message: String, var errorReason: ResponseBody?) : Exception(message)