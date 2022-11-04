package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Weather

data class WeatherDto(
    val daily: DailyDto,
    val hourly: HourlyDto,
    val current_weather : CurrentWeatherDto,
    val latitude: Float,
    val longitude: Float,
    val timezone: String
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        daily.toDaily(),
        hourly.toHourly(),
        current_weather.toCurrentWeather(),
        latitude, longitude, timezone
    )
}