package com.weather.forecast.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("last_updated_epoch")
    @Expose
    var lastUpdatedEpoch: Int,

    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String,

    @SerializedName("temp_c")
    @Expose
    var tempC: Double,

    @SerializedName("temp_f")
    @Expose
    var tempF: Double,

    @SerializedName("is_day")
    @Expose
    var isDay: Int,

    @SerializedName("wind_mph")
    @Expose
    var windMph: Double,

    @SerializedName("wind_kph")
    @Expose
    var windKph: Double,

    @SerializedName("wind_degree")
    @Expose
    var windDegree: Int,

    @SerializedName("wind_dir")
    @Expose
    var windDir: String,

    @SerializedName("pressure_mb")
    @Expose
    var pressureMb: Double,

    @SerializedName("pressure_in")
    @Expose
    var pressureIn: Double,

    @SerializedName("precip_mm")
    @Expose
    var precipMm: Double,

    @SerializedName("precip_in")
    @Expose
    var precipIn: Double,

    @SerializedName("humidity")
    @Expose
    var humidity: Int,

    @SerializedName("cloud")
    @Expose
    var cloud: Int,

    @SerializedName("feelslike_c")
    @Expose
    var feelslikeC: Double,

    @SerializedName("feelslike_f")
    @Expose
    var feelslikeF: Double,

    @SerializedName("vis_km")
    @Expose
    var visKm: Double,

    @SerializedName("vis_miles")
    @Expose
    var visMiles: Double,

    @SerializedName("uv")
    @Expose
    var uv: Double,

    @SerializedName("gust_mph")
    @Expose
    var gustMph: Double,

    @SerializedName("gust_kph")
    @Expose
    var gustKph: Double ) 