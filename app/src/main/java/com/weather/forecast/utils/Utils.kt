package com.weather.forecast.utils

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.SuperscriptSpan
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun getDayFromDate(date: String): String {
    return try {
        val format1 = SimpleDateFormat(DATE_FORMAT_API, Locale.ENGLISH)
        val dt1 = format1.parse(date)
        val format2 = SimpleDateFormat(DATE_FORMAT_UI, Locale.ENGLISH)
        format2.format(dt1)
    } catch (exception: ParseException) {
        ""
    }
}

fun getTemperatureWithCelceius(temp: Double?): SpannableStringBuilder? {
    if (temp == null) {
        return null
    }
    val formattedString = temp.roundToInt().toString() + "o"
    val spannableStringBuilder = SpannableStringBuilder(formattedString)

    spannableStringBuilder.setSpan(
        SuperscriptSpan(),
        formattedString.length - 1,
        formattedString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableStringBuilder.setSpan(
        RelativeSizeSpan(0.5f),
        formattedString.length - 1,
        formattedString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableStringBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        formattedString.length - 1,
        formattedString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableStringBuilder
}


fun getTemperatureWithAppendCelceius(temperature: Double?): String {
    if (temperature == null || temperature.roundToInt() == 0) {
        return ""
    }
    return "${temperature.roundToInt()}  C"
}

@Suppress("deprecation")
fun isInternetConnectionAvailable(context: Context): Boolean {
    var result = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}