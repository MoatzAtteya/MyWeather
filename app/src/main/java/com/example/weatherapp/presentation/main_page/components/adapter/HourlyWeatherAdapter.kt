package com.example.weatherapp.presentation.main_page.components.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import com.example.weatherapp.databinding.HourlyWeatherItemBinding
import com.example.weatherapp.domain.model.Hourly
import java.util.*
import kotlin.math.roundToInt

class HourlyWeatherAdapter(val fragment: Fragment, val temp: IntArray) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var weather: Hourly

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HourlyWeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_weather_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HourlyWeatherViewHolder) {


            val weatherType = getWeatherType(weather, position)

            holder.binding.apply {
                val temp = buildString {
                    append(weather.temperature[position].roundToInt())
                    append(" \u2103")
                }
                hourWeatherTempValue.text = temp

                //converting weather unixTime to hours number.
                val dv: Long = java.lang.Long.valueOf(weather.time[position]) * 1000
                val df = Date(dv)
                val time = buildString {
                    append(df.hours)
                    append(":00")
                }
                hourWeatherTime.text = time

                Glide.with(fragment.requireContext())
                    .load(R.drawable.cloudy)
                    .into(hourWeatherIv)


                val rightNow = Calendar.getInstance()
                val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY)

                if (df.hours == currentHour)
                    hourlyWeatherCv.setCardBackgroundColor(Color.parseColor("#1b86e6"))
                else
                    hourlyWeatherCv.setCardBackgroundColor(Color.parseColor("#12122b"))

                when (weatherType) {
                    Constants.sunny_key -> {
                        Glide.with(fragment.requireContext())
                            .load(R.drawable.sunny)
                            .into(hourWeatherIv)
                    }
                    Constants.night_key -> {
                        Glide.with(fragment.requireContext())
                            .load(R.drawable.night)
                            .into(hourWeatherIv)
                    }
                    Constants.cloudy_key -> {
                        Glide.with(fragment.requireContext())
                            .load(R.drawable.cloudy)
                            .into(hourWeatherIv)
                    }
                    Constants.cloudy_sunny_key -> {
                        Glide.with(fragment.requireContext())
                            .load(R.drawable.sun_cloud)
                            .into(hourWeatherIv)
                    }
                    Constants.rainy_key -> {
                        Glide.with(fragment.requireContext())
                            .load(R.drawable.rainy)
                            .into(hourWeatherIv)
                    }
                    Constants.snow_key -> {
                        Glide.with(fragment.requireContext())
                            .load(R.drawable.snow)
                            .into(hourWeatherIv)
                    }
                }


            }
        }
    }

    private fun getWeatherType(weather: Hourly, position: Int): String {
        val currentHour = convertUnixToHours(weather.time[position])
        val currentTemp = weather.temperature[position]
        val currentPrecipitation =
            weather.precipitation[position]
        val currentCloudOver = weather.cloudOver[position]
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
            Constants.cloudy_sunny_key
        } else {
            weatherType = if (currentTemp < 10)
                Constants.snow_key
            else if (currentHour in 6..18) {
                Constants.sunny_key
            } else
                Constants.night_key
        }
        return weatherType
    }

    override fun getItemCount(): Int {
        return temp.size
    }

    fun setHourlyWeather(weather: Hourly) {
        this.weather = weather
    }

    private fun convertUnixToHours(time: Long): Int {
        val dv: Long =
            java.lang.Long.valueOf(time) * 1000
        val df = Date(dv)
        return df.hours
    }

    private class HourlyWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = HourlyWeatherItemBinding.bind(view)
    }
}