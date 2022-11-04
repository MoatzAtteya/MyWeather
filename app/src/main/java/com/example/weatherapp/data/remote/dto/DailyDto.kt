package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Weather

data class DailyDto(
    val precipitation_sum: List<Double>,
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<Long>,
    val windspeed_10m_max: List<Double>,
    val snowfall_sum: List<Double>,
    val rain_sum: List<Double>,
    val showers_sum: List<Double>
)

fun DailyDto.toDaily(): Daily {
    return Daily(
        precipitation_sum,
        sunrise,
        sunset,
        maxTemperatures = temperature_2m_max,
        minTemperatures = temperature_2m_min,
        time,
        maxWindSpeed = windspeed_10m_max,
        rain_sum, showers_sum, snowfall_sum

    )
}