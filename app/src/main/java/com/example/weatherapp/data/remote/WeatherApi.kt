package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.WeatherDto
import com.google.android.material.timepicker.TimeFormat
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun getHourlyWeather(
        @Query("latitude") latitude : Float,
        @Query("longitude") longitude : Float,
        @Query("hourly" , encoded = true) hourly : String,
        @Query("daily" , encoded = true) daily : String,
        @Query("timezone") timezone : String,
        @Query("start_date") start_date : String,
        @Query("end_date") end_date : String,
        @Query("timeformat") timeFormat: String,
        @Query("current_weather") boolean: Boolean
    ) : WeatherDto

    @GET("/v1/forecast")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
     fun getHourlyWeatherObserver(
        @Query("latitude") latitude : Float,
        @Query("longitude") longitude : Float,
        @Query("hourly" , encoded = true) hourly : String,
        @Query("daily" , encoded = true) daily : String,
        @Query("timezone") timezone : String,
        @Query("start_date") start_date : String,
        @Query("end_date") end_date : String,
        @Query("timeformat") timeFormat: String,
        @Query("current_weather") boolean: Boolean
    ) : Observable<WeatherDto>

    @GET("/v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude : Float,
        @Query("longitude") longitude : Float,
        @Query("daily") daily : String,
        @Query("timezone") timezone : String,
        @Query("start_date") start_date : String,
        @Query("end_date") end_date : String,
        @Query("timeformat") timeFormat: String
    ) : WeatherDto


}