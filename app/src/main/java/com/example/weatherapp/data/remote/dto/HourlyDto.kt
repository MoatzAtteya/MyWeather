package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Hourly

data class HourlyDto(
    val temperature_2m: List<Double>,
    val time: List<Long>,
    val relativehumidity_2m: List<Int>,
    val precipitation: List<Double>,
    val cloudcover: List<Int>
)

fun HourlyDto.toHourly(): Hourly {
    return Hourly(
        temperature_2m, time, relativehumidity_2m, precipitation, cloudcover
    )
}