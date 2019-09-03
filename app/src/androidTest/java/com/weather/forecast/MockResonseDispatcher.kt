package com.weather.forecast

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockResonseDispatcher {
    /**
     * Return ok response from mock server
     */
    inner class RequestDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            if (request.path == "forecast.json") {
                return MockResponse().setResponseCode(200).setBody(getDummyJson())
            }
            return MockResponse().setResponseCode(404)
        }

        private fun getDummyJson(): String {
            return "{\n" +
                    "    \"location\": {\n" +
                    "        \"name\": \"Delhi\",\n" +
                    "        \"region\": \"Delhi\",\n" +
                    "        \"country\": \"India\",\n" +
                    "        \"lat\": 28.67,\n" +
                    "        \"lon\": 77.22,\n" +
                    "        \"tz_id\": \"Asia/Kolkata\",\n" +
                    "        \"localtime_epoch\": 1567130056,\n" +
                    "        \"localtime\": \"2019-08-30 7:24\"\n" +
                    "    },\n" +
                    "    \"current\": {\n" +
                    "        \"last_updated_epoch\": 1567129535,\n" +
                    "        \"last_updated\": \"2019-08-30 07:15\",\n" +
                    "        \"temp_c\": 28.0,\n" +
                    "        \"temp_f\": 82.4,\n" +
                    "        \"is_day\": 1,\n" +
                    "        \"condition\": {\n" +
                    "            \"text\": \"Mist\",\n" +
                    "            \"icon\": \"//cdn.apixu.com/weather/64x64/day/143.png\",\n" +
                    "            \"code\": 1030\n" +
                    "        },\n" +
                    "        \"wind_mph\": 4.3,\n" +
                    "        \"wind_kph\": 6.8,\n" +
                    "        \"wind_degree\": 40,\n" +
                    "        \"wind_dir\": \"NE\",\n" +
                    "        \"pressure_mb\": 1005.0,\n" +
                    "        \"pressure_in\": 30.2,\n" +
                    "        \"precip_mm\": 0.0,\n" +
                    "        \"precip_in\": 0.0,\n" +
                    "        \"humidity\": 79,\n" +
                    "        \"cloud\": 50,\n" +
                    "        \"feelslike_c\": 32.2,\n" +
                    "        \"feelslike_f\": 89.9,\n" +
                    "        \"vis_km\": 3.0,\n" +
                    "        \"vis_miles\": 1.0,\n" +
                    "        \"uv\": 6.0,\n" +
                    "        \"gust_mph\": 4.7,\n" +
                    "        \"gust_kph\": 7.6\n" +
                    "    },\n" +
                    "    \"forecast\": {\n" +
                    "        \"forecastday\": [\n" +
                    "            {\n" +
                    "                \"date\": \"2019-08-30\",\n" +
                    "                \"date_epoch\": 1567123200,\n" +
                    "                \"day\": {\n" +
                    "                    \"maxtemp_c\": 39.3,\n" +
                    "                    \"maxtemp_f\": 102.7,\n" +
                    "                    \"mintemp_c\": 32.4,\n" +
                    "                    \"mintemp_f\": 90.3,\n" +
                    "                    \"avgtemp_c\": 36.1,\n" +
                    "                    \"avgtemp_f\": 97.0,\n" +
                    "                    \"maxwind_mph\": 4.5,\n" +
                    "                    \"maxwind_kph\": 7.2,\n" +
                    "                    \"totalprecip_mm\": 0.1,\n" +
                    "                    \"totalprecip_in\": 0.0,\n" +
                    "                    \"avgvis_km\": 10.0,\n" +
                    "                    \"avgvis_miles\": 6.0,\n" +
                    "                    \"avghumidity\": 46.0,\n" +
                    "                    \"condition\": {\n" +
                    "                        \"text\": \"Partly cloudy\",\n" +
                    "                        \"icon\": \"//cdn.apixu.com/weather/64x64/day/116.png\",\n" +
                    "                        \"code\": 1003\n" +
                    "                    },\n" +
                    "                    \"uv\": 10.0\n" +
                    "                },\n" +
                    "                \"astro\": {\n" +
                    "                    \"sunrise\": \"05:58 AM\",\n" +
                    "                    \"sunset\": \"06:45 PM\",\n" +
                    "                    \"moonrise\": \"05:31 AM\",\n" +
                    "                    \"moonset\": \"07:01 PM\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"date\": \"2019-08-31\",\n" +
                    "                \"date_epoch\": 1567209600,\n" +
                    "                \"day\": {\n" +
                    "                    \"maxtemp_c\": 39.4,\n" +
                    "                    \"maxtemp_f\": 102.9,\n" +
                    "                    \"mintemp_c\": 33.3,\n" +
                    "                    \"mintemp_f\": 91.9,\n" +
                    "                    \"avgtemp_c\": 36.2,\n" +
                    "                    \"avgtemp_f\": 97.2,\n" +
                    "                    \"maxwind_mph\": 8.3,\n" +
                    "                    \"maxwind_kph\": 13.3,\n" +
                    "                    \"totalprecip_mm\": 0.2,\n" +
                    "                    \"totalprecip_in\": 0.01,\n" +
                    "                    \"avgvis_km\": 9.9,\n" +
                    "                    \"avgvis_miles\": 6.0,\n" +
                    "                    \"avghumidity\": 46.0,\n" +
                    "                    \"condition\": {\n" +
                    "                        \"text\": \"Patchy rain possible\",\n" +
                    "                        \"icon\": \"//cdn.apixu.com/weather/64x64/day/176.png\",\n" +
                    "                        \"code\": 1063\n" +
                    "                    },\n" +
                    "                    \"uv\": 9.8\n" +
                    "                },\n" +
                    "                \"astro\": {\n" +
                    "                    \"sunrise\": \"05:58 AM\",\n" +
                    "                    \"sunset\": \"06:44 PM\",\n" +
                    "                    \"moonrise\": \"06:40 AM\",\n" +
                    "                    \"moonset\": \"07:44 PM\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"date\": \"2019-09-01\",\n" +
                    "                \"date_epoch\": 1567296000,\n" +
                    "                \"day\": {\n" +
                    "                    \"maxtemp_c\": 38.8,\n" +
                    "                    \"maxtemp_f\": 101.8,\n" +
                    "                    \"mintemp_c\": 32.5,\n" +
                    "                    \"mintemp_f\": 90.5,\n" +
                    "                    \"avgtemp_c\": 35.1,\n" +
                    "                    \"avgtemp_f\": 95.2,\n" +
                    "                    \"maxwind_mph\": 6.9,\n" +
                    "                    \"maxwind_kph\": 11.2,\n" +
                    "                    \"totalprecip_mm\": 5.4,\n" +
                    "                    \"totalprecip_in\": 0.21,\n" +
                    "                    \"avgvis_km\": 9.5,\n" +
                    "                    \"avgvis_miles\": 5.0,\n" +
                    "                    \"avghumidity\": 52.0,\n" +
                    "                    \"condition\": {\n" +
                    "                        \"text\": \"Moderate or heavy rain shower\",\n" +
                    "                        \"icon\": \"//cdn.apixu.com/weather/64x64/day/356.png\",\n" +
                    "                        \"code\": 1243\n" +
                    "                    },\n" +
                    "                    \"uv\": 8.3\n" +
                    "                },\n" +
                    "                \"astro\": {\n" +
                    "                    \"sunrise\": \"05:59 AM\",\n" +
                    "                    \"sunset\": \"06:43 PM\",\n" +
                    "                    \"moonrise\": \"07:49 AM\",\n" +
                    "                    \"moonset\": \"08:24 PM\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"date\": \"2019-09-02\",\n" +
                    "                \"date_epoch\": 1567382400,\n" +
                    "                \"day\": {\n" +
                    "                    \"maxtemp_c\": 37.9,\n" +
                    "                    \"maxtemp_f\": 100.2,\n" +
                    "                    \"mintemp_c\": 31.1,\n" +
                    "                    \"mintemp_f\": 88.0,\n" +
                    "                    \"avgtemp_c\": 34.1,\n" +
                    "                    \"avgtemp_f\": 93.4,\n" +
                    "                    \"maxwind_mph\": 7.8,\n" +
                    "                    \"maxwind_kph\": 12.6,\n" +
                    "                    \"totalprecip_mm\": 8.0,\n" +
                    "                    \"totalprecip_in\": 0.31,\n" +
                    "                    \"avgvis_km\": 9.0,\n" +
                    "                    \"avgvis_miles\": 5.0,\n" +
                    "                    \"avghumidity\": 56.0,\n" +
                    "                    \"condition\": {\n" +
                    "                        \"text\": \"Moderate or heavy rain shower\",\n" +
                    "                        \"icon\": \"//cdn.apixu.com/weather/64x64/day/356.png\",\n" +
                    "                        \"code\": 1243\n" +
                    "                    },\n" +
                    "                    \"uv\": 7.2\n" +
                    "                },\n" +
                    "                \"astro\": {\n" +
                    "                    \"sunrise\": \"05:59 AM\",\n" +
                    "                    \"sunset\": \"06:42 PM\",\n" +
                    "                    \"moonrise\": \"08:55 AM\",\n" +
                    "                    \"moonset\": \"09:04 PM\"\n" +
                    "                }\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"alert\": {\n" +
                    "        \n" +
                    "    }\n" +
                    "}"
        }

    }

    /**
     * Return error response from mock server
     */
    inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

}