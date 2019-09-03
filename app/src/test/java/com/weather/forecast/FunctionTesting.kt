package com.weather.forecast

import com.weather.forecast.utils.DATE_FORMAT_API
import com.weather.forecast.utils.DATE_FORMAT_UI
import com.weather.forecast.utils.getDayFromDate
import com.weather.forecast.utils.getTemperatureWithAppendCelceius
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FunctionTesting {

    @Test
    fun testCase() {
        assert(getDayFromDate(getTodayDate()) == getCurrentDateInRequiredFormat())
        assert(getTemperatureWithAppendCelceius(24.46432) == "24 C")
    }


    private fun getCurrentDateInRequiredFormat(): String {
        val format2 = SimpleDateFormat(DATE_FORMAT_UI, Locale.ENGLISH)
        return format2.format(Date())
    }

    private fun getTodayDate(): String {
        val format2 = SimpleDateFormat(DATE_FORMAT_API, Locale.ENGLISH)
        return format2.format(Date())
    }

}
