package com.example.weatherapp.common

object Constants {

    const val hourly="temperature_2m,relativehumidity_2m,precipitation,cloudcover"
    const val daily="temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,showers_sum,snowfall_sum,windspeed_10m_max"
    //const val detail_day_daily="temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,showers_sum,snowfall_sum,windspeed_10m_max"

    const val timeFormat = "unixtime"
    const val base_url = "https://api.open-meteo.com/"

    const val rainy_key = "rainy"
    const val sunny_key = "sunny"
    const val cloudy_key = "cloudy"
    const val night_key = "night"
    const val cloudy_sunny_key = "cloud_with_sun"
    const val snow_key = "snow"

    const val sunset_key ="sunset"
    const val sunrise_key ="sunrise"
    const val midDay_key ="midDay"
}