package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getHourlyWeather(
        latitude: Float,
        longitude: Float,
        hourly: String,
        daily: String,
        timezone: String,
        start_date: String,
        end_date: String,
        timeFormat: String

    ): WeatherDto {
       return api.getHourlyWeather(latitude, longitude, hourly, daily, timezone, start_date, end_date,timeFormat,true)
    }

    override suspend fun getWeather(
        latitude: Float,
        longitude: Float,
        daily: String,
        timezone: String,
        start_date: String,
        end_date: String,
        timeFormat: String
    ): WeatherDto {
        return api.getWeather(latitude, longitude, daily, timezone, start_date, end_date,timeFormat)
    }

}