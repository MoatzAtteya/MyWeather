package com.example.weatherapp.presentation.day_details.components.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.databinding.FragmentDayDetailsBinding
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.main_page.components.adapter.HourlyWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class DayDetailsFragment : Fragment() {


    private lateinit var viewModel: DayDetailsViewModel
    private lateinit var binding: FragmentDayDetailsBinding
    lateinit var weather: Weather

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DayDetailsViewModel::class.java]

        val todayDate = viewModel.giveDate()
        println("today date is:$todayDate")
        viewModel.getWeather(
            31.18F,
            29.97F,
            "Africa/Cairo",
            todayDate.toString(),
            todayDate.toString()
        )
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getWeatherResponse.collect { response ->
                when (response) {
                    is Resource.Error -> {
                        Log.e("getting weather data: ", response.message!!)
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        weather = response.data!!
                        drawSunCurve(weather.daily)
                        updateUI(weather)
                    }
                }
            }
        }



        return binding.root
    }

    private fun updateUI(weather: Weather) {
        val sunRise = viewModel.convertUnixToTime(weather.daily.sunrise[0])
        val sunSet = viewModel.convertUnixToTime(weather.daily.sunset[0])
        binding.sunriseTv.text = sunRise
        binding.sunsetTv.text = sunSet

        drawSunCurve(weather.daily)
        setUpHourlyRecyclerView(weather.hourly)

        val temp = buildString {
            append(weather.currentWeather.temperature.roundToInt())
            append(" \u2103")
        }
        binding.reelFeelTempValue.text = temp

        val windSpeed = buildString {
            append(weather.currentWeather.wind_speed)
            append("km/h")
        }
        binding.dayDetailWindSpeedValue.text = windSpeed

        val precipitation = buildString {
            append(weather.daily.precipitation.max())
            append("%")

        }
        binding.detailsChanceRainValue.text = precipitation

        val humidity = buildString {
            append(weather.hourly.humidity.max())
            append("%")
        }
        binding.dayDetailHumidityValue.text = humidity

        val rain = buildString {
            append(weather.daily.rainSum.max())
            append("mm")
        }
        binding.dayDetailRainValueTv.text = rain
        val shower = buildString {
            append(weather.daily.showerSum.max())
            append("mm")
        }
        binding.dayDetailShowerValueTv.text = shower
        val snow = buildString {
            append(weather.daily.snowSum.max())
            append("mm")
        }
        binding.dayDetailSnowValueTv.text = snow


    }

    private fun drawSunCurve(daily: Daily) {
        when (viewModel.getSunState(daily)) {
            Constants.midDay_key -> {
                Glide.with(requireContext()).load(R.drawable.sun_curve_middle)
                    .into(binding.sunCurveIv)
            }
            Constants.sunrise_key -> {
                Glide.with(requireContext()).load(R.drawable.sun_curve_start)
                    .into(binding.sunCurveIv)
            }
            Constants.sunset_key -> {
                Glide.with(requireContext()).load(R.drawable.sun_curve_end).into(binding.sunCurveIv)
            }
        }
    }


    private fun setUpHourlyRecyclerView(weather: Hourly) {
        binding.dayDetailHourlyWeatherRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.dayDetailHourlyWeatherRv.setHasFixedSize(true)
        val adapter = HourlyWeatherAdapter(this, IntArray(24))
        adapter.setHourlyWeather(weather)
        binding.dayDetailHourlyWeatherRv.adapter = adapter
       // binding.dayDetailHourlyWeatherRv.smoothScrollToPosition(getCurrentWeatherIndex(weather))
        //adapter.notifyDataSetChanged()
    }

    private fun getCurrentWeatherIndex(hourly: Hourly): Int {
        var index = 0
        val rightNow = Calendar.getInstance()
        val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY)

        for (hour in hourly.time) {
            val dv: Long = java.lang.Long.valueOf(hour) * 1000
            val df = Date(dv)
            val hours = df.hours
            if (currentHour == hours)
                index = hourly.time.indexOf(hour)
        }
        return index
    }


}