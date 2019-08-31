package com.weather.forecast.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForeCastDay(
    @SerializedName("date")
    @Expose
    var date: String,

    @SerializedName("date_epoch")
    @Expose
    var dateEpoch: Int,

    @SerializedName("day")
    @Expose
    var day: Day

)