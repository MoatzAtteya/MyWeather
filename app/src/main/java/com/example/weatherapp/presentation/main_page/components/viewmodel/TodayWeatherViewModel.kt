package com.example.weatherapp.presentation.main_page.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.get_hourly_weather.GetHourlyWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodayWeatherViewModel @Inject constructor(
    private val getHourlyWeatherUseCase: GetHourlyWeatherUseCase
) : ViewModel() {

    private val _getWeatherResponse = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val getWeatherResponse get() = _getWeatherResponse

    fun getWeather(
        latitude: Float,
        longitude: Float,
        timezone: String,
        start_date: String,
        end_date: String,
    ) = viewModelScope.launch {
        getHourlyWeatherUseCase.execute(latitude, longitude, timezone, start_date, end_date)
            .collect { response ->
                when (response) {
                    is Resource.Error -> _getWeatherResponse.value =
                        Resource.Error(response.message!!)
                    is Resource.Loading -> _getWeatherResponse.value = Resource.Loading()
                    is Resource.Success -> {
                        Log.i("Weather data is: ", response.data.toString())
                        _getWeatherResponse.value =
                            Resource.Success(response.data!!)
                    }
                }
            }
    }

    fun getLocation(timezone: String): String {
        val index = timezone.indexOf('/')
        return timezone.substring(index + 1)
    }

    fun convertUnixToDate(time: Long): String {
        val dv: Long =
            java.lang.Long.valueOf(time) * 1000
        val df = Date(dv)
        // MM dd, yyyy hh:mma
        return SimpleDateFormat("MMM dd, yyyy").format(df)
    }

    private fun convertUnixToHours(time: Long): Int {
        val dv: Long =
            java.lang.Long.valueOf(time) * 1000
        val df = Date(dv)
        return df.hours
    }

    fun getCurrentTempType(weather: Weather): String {
        val currentIndex = weather.hourly.time.indexOf(weather.currentWeather.time.toLong())
        val currentHour = convertUnixToHours(weather.currentWeather.time.toLong())
        val currentTemp = weather.hourly.temperature[currentIndex]
        val currentPrecipitation =
            weather.hourly.precipitation[currentIndex]
        val currentCloudOver = weather.hourly.cloudOver[currentIndex]
        var weatherType = ""


        if (currentCloudOver > 50) {
            weatherType = if (currentPrecipitation > 25)
                Constants.rainy_key
            else
                Constants.cloudy_key

        } else if (currentCloudOver in 21..49) {
            weatherType = if (currentPrecipitation > 25)
                Constants.rainy_key
            else {
                if (currentHour in 6..18)
                    Constants.cloudy_sunny_key
                else
                    Constants.night_key
            }
        } else {
            weatherType = if (currentTemp < 10)
                Constants.snow_key
            else if (currentHour in 6..16) {
                Constants.sunny_key
            } else
                Constants.night_key
        }
        return weatherType
    }

}