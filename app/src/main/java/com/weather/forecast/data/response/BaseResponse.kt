package com.weather.forecast.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("location")
    @Expose
    var location: Location,

    @SerializedName("current")
    @Expose
    var current: Current,

    @SerializedName("forecast")
    @Expose
    var forecast: Forecast,

    @SerializedName("alert")
    @Expose
    var alert: Alert
)