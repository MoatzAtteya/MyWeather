package com.example.weatherapp.domain.model

data class Hourly(
    val temperature: List<Double>,
    val time: List<Long>,
    val humidity : List<Int>,
    val precipitation : List<Double>,
    val cloudOver : List<Int>
) :java.io.Serializable
