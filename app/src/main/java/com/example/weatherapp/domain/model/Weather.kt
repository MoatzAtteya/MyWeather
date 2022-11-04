package com.example.weatherapp.domain.model

import com.example.weatherapp.data.remote.dto.DailyDto

data class Weather(
    val daily: Daily,
    val hourly: Hourly,
    val currentWeather: CurrentWeather,
    val latitude: Float,
    val longitude: Float,
    val timezone: String
) : java.io.Serializable