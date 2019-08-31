package com.weather.forecast.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c")
    @Expose
    var maxtempC: Double,

    @SerializedName("maxtemp_f")
    @Expose
    var maxtempF: Double,

    @SerializedName("mintemp_c")
    @Expose
    var mintempC: Double,

    @SerializedName("mintemp_f")
    @Expose
    var mintempF: Double,

    @SerializedName("avgtemp_c")
    @Expose
    var avgtempC: Double,

    @SerializedName("avgtemp_f")
    @Expose
    var avgtempF: Double,

    @SerializedName("maxwind_mph")
    @Expose
    var maxwindMph: Double,

    @SerializedName("maxwind_kph")
    @Expose
    var maxwindKph: Double,

    @SerializedName("totalprecip_mm")
    @Expose
    var totalprecipMm: Double,

    @SerializedName("totalprecip_in")
    @Expose
    var totalprecipIn: Double,

    @SerializedName("avgvis_km")
    @Expose
    var avgvisKm: Double,

    @SerializedName("avgvis_miles")
    @Expose
    var avgvisMiles: Double,

    @SerializedName("avghumidity")
    @Expose
    var avghumidity: Double,

    @SerializedName("uv")
    @Expose
    var uv: Double )
