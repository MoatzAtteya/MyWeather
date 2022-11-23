package com.example.weatherapp.presentation.day_details.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.get_day_weather.GetDayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DayDetailsViewModel @Inject constructor(
    private val getDayWeather: GetDayWeatherUseCase
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
        getDayWeather.invoke(latitude, longitude, timezone, start_date, end_date)
            .collect { response ->
                when (response) {
                    is Resource.Error -> _getWeatherResponse.value =
                        Resource.Error(response.message!!)
                    is Resource.Loading -> _getWeatherResponse.value = Resource.Loading()
                    is Resource.Success -> {
                        Log.i("Day detail Weather data is: ", response.data.toString())
                        _getWeatherResponse.value =
                            Resource.Success(response.data!!)
                    }
                }
            }
    }

    fun giveDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(cal.time)
    }

    fun convertUnixToTime(time: Long): String {
        val dv: Long =
            java.lang.Long.valueOf(time) * 1000
        val df = Date(dv)
        return SimpleDateFormat("hh:mm aaa").format(df)
    }

    private fun giveCurrentTime(): Int {
        val rightNow = Calendar.getInstance()
        return rightNow.get(Calendar.HOUR_OF_DAY)
    }
    private fun giveHour(time: Long) : Int {
        val dv: Long = java.lang.Long.valueOf(time) * 1000
        val df = Date(dv)
        return df.hours
    }
    fun getSunState(daily: Daily): String {
        val sunrise = giveHour(daily.sunrise[0])
        val sunset = giveHour(daily.sunset[0])
        val currentHour = giveCurrentTime()

        println("hours: $sunrise, $sunset, $currentHour")

        return when (currentHour) {
            in sunrise .. sunrise+1 -> Constants.sunrise_key
            in sunrise+1 until sunset -> Constants.midDay_key
            else -> Constants.sunset_key
        }

    }

}