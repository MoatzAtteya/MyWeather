package com.example.weatherapp.common

import com.example.weatherapp.domain.model.Country

object Constants {

    const val hourly = "temperature_2m,relativehumidity_2m,precipitation,cloudcover"
    const val daily =
        "temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,showers_sum,snowfall_sum,windspeed_10m_max"
    //const val detail_day_daily="temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,showers_sum,snowfall_sum,windspeed_10m_max"

    const val timeFormat = "unixtime"
    const val base_url = "https://api.open-meteo.com/"

    const val rainy_key = "rainy"
    const val sunny_key = "sunny"
    const val cloudy_key = "cloudy"
    const val night_key = "night"
    const val cloudy_sunny_key = "cloud_with_sun"
    const val snow_key = "snow"

    const val sunset_key = "sunset"
    const val sunrise_key = "sunrise"
    const val midDay_key = "midDay"

    val cityList = mutableListOf(
        Country("Berlin", "Europe" , 52.5235,13.4115 ),
        Country("Paris", "Europe", 48.8567 ,2.3510),
        Country("London", "Europe", 51.5002 ,-0.1262),
        Country("Madrid", "Europe", 40.4167 ,-3.7033),
        Country("Vienna", "Europe", 48.2092 ,16.3728),
        Country("Moscow", "Europe", 55.7558 ,37.6176),
        Country("Sofia", "Europe", 42.7105 ,23.3238),
        Country("Rome", "Europe", 41.8955 ,12.4823),
        Country("Dublin", "Europe", 53.3441 ,-6.2675),
        Country("Amsterdam", "Europe", 52.3738 ,4.8910),
        Country("Oslo", "Europe", 59.9138 ,10.7387),
        Country("Kiev", "Europe", 50.4422 ,30.5367),
        Country("Washington", "America", 38.8921 ,-77.0241),
        Country("New York", "America", 40.71 ,-74.01),
        Country("Los Angelos", "America", 34.05 ,-118.24),
        Country("Chicago", "America", 41.85 ,-87.65),
        Country("Houston", "America", 29.76 ,-95.36),
        Country("Algiers", "Africa", 36.7755 ,3.0597),
        Country("Cairo", "Africa", 30.0571 ,31.2272),
        Country("Luanda", "Africa", -8.8159 ,13.2306),
        Country("Nairobi", "Africa", -1.2762 ,36.7965),
        Country("Abu Dhabi", "Asia", 24.4764 ,54.3705),
    )


}