package com.example.weatherapp.presentation.main_page.components.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyWeatherItemBinding
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.presentation.main_page.components.ui.TodayWeatherFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyWeatherAdapter(val fragment: TodayWeatherFragment, val maxTemp: List<Double>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var weather: Daily

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DailyWeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.daily_weather_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currTemp = maxTemp[position]
        if (holder is DailyWeatherViewHolder) {
            val dv: Long =
                java.lang.Long.valueOf(weather.time[position]) * 1000
            val df = Date(dv)
            val day = SimpleDateFormat("EEEE").format(df)
            val date = SimpleDateFormat("MMM, yyyy").format(df)

            val maxTemp = buildString {
                append(weather.maxTemperatures[position].roundToInt())
                append(" \u2103")
            }
            val minTemp = buildString {
                append(weather.minTemperatures[position].roundToInt())
                append(" \u2103")
            }
            val windSpeed = weather.maxWindSpeed[position]

            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            val today = sdf.format(d)


            holder.binding.apply {
                dailyDayTv.text = day
                dailyDateTv.text = date
                dailyMaxTemp.text = maxTemp
                dailyMinTemp.text = minTemp
                if (windSpeed.toInt() in 15..30) {
                    Glide.with(fragment.requireContext()).load(R.drawable.sun_cloud)
                        .into(dailyWeatherIv)
                } else if (windSpeed.toInt() > 30) {
                    Glide.with(fragment.requireContext()).load(R.drawable.cloudy)
                        .into(dailyWeatherIv)
                } else
                    Glide.with(fragment.requireContext()).load(R.drawable.sunny)
                        .into(dailyWeatherIv)

                if (today == day)
                    dailyWeatherCv.setCardBackgroundColor(Color.parseColor("#1b86e6"))
                else
                    dailyWeatherCv.setCardBackgroundColor(Color.parseColor("#12122b"))

            }

        }
    }

    override fun getItemCount(): Int {
        return maxTemp.size
    }

    fun setDailyWeather(weather: Daily) {
        this.weather = weather
    }

    private class DailyWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DailyWeatherItemBinding.bind(view)
    }
}