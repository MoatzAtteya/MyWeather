package com.example.weatherapp.domain.model

data class Daily(
    val precipitation: List<Double>,
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val maxTemperatures: List<Double>,
    val minTemperatures: List<Double>,
    val time: List<Long>,
    val maxWindSpeed: List<Double>,
    val rainSum: List<Double>,
    val showerSum: List<Double>,
    val snowSum: List<Double>
) : java.io.Serializable
