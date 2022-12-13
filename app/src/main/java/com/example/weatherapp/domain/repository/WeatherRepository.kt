package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.model.Weather
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.http.Query

interface WeatherRepository {

    suspend fun getHourlyWeather(
        latitude: Float,
        longitude: Float,
        hourly: String,
        daily: String,
        timezone: String,
        start_date: String,
        end_date: String,
        timeFormat: String

    ): WeatherDto

     fun getHourlyWeatherObserver(
        latitude: Float,
        longitude: Float,
        hourly: String,
        daily: String,
        timezone: String,
        start_date: String,
        end_date: String,
        timeFormat: String

    ): Observable<WeatherDto>

    suspend fun getWeather(
        latitude: Float,
        longitude: Float,
        daily: String,
        timezone: String,
        start_date: String,
        end_date: String,
        timeFormat: String

    ): WeatherDto

}