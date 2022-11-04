package com.example.weatherapp.presentation.main_page.components.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.databinding.FragmentTodayWeatherBinding
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.main_page.components.adapter.DailyWeatherAdapter
import com.example.weatherapp.presentation.main_page.components.adapter.HourlyWeatherAdapter
import com.example.weatherapp.presentation.main_page.components.viewmodel.TodayWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodayWeatherFragment : Fragment() {
    private lateinit var viewModel: TodayWeatherViewModel
    private lateinit var binding: FragmentTodayWeatherBinding
    private lateinit var weather: Weather

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[TodayWeatherViewModel::class.java]
        binding = FragmentTodayWeatherBinding.inflate(inflater, container, false)

        val lat: Float = 31.18F
        val long: Float = 29.97F
        viewModel.getWeather(
            lat,
            long,
            "Africa/Cairo",
            "2022-11-02",
            "2022-11-06"
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
                        updateUI(weather)
                    }
                }
            }
        }

        binding.viewFullDetailsTv.setOnClickListener {
            findNavController().navigate(R.id.action_todayWeatherFragment_to_dayDetailsFragment)
        }

        return binding.root
    }

    private fun updateUI(weather: Weather) {
        // getting city name from timezone like (Africa/Cairo)
        val city = viewModel.getLocation(weather.timezone)
        binding.locationTv.text = city

        val date = viewModel.convertUnixToDate(weather.currentWeather.time.toLong())
        binding.dateTv.text = date

        //Appending Celsius mark to the temp.
        val temp = buildString {
            append(weather.currentWeather.temperature.toInt())
            append(" \u2103")
        }
        binding.dayTempValueTv.text = temp

        val windSpeed = buildString {
            append(weather.currentWeather.wind_speed)
            append("Km/h")
        }
        binding.dayWindSpeedValueTv.text = windSpeed

        val currentIndex = weather.hourly.time.indexOf(weather.currentWeather.time.toLong())
        val humidity = buildString {
            append(weather.hourly.humidity[currentIndex])
            append('%')
        }
        binding.dayHumidityValueTv.text = humidity

        val weatherType = viewModel.getCurrentTempType(weather)
        updateWeatherIV(weatherType)

        setUpHourlyRecyclerView(weather.hourly)

        setUpDailyRecyclerView(weather.daily)

    }

    private fun setUpDailyRecyclerView(daily: Daily) {
        binding.nextDaysWeatherRv.layoutManager = LinearLayoutManager(requireContext())
        binding.nextDaysWeatherRv.setHasFixedSize(true)
        val adapter = DailyWeatherAdapter(this, daily.maxTemperatures)
        adapter.setDailyWeather(daily)
        binding.nextDaysWeatherRv.adapter = adapter
    }

    private fun setUpHourlyRecyclerView(weather: Hourly) {
        binding.hourlyWeatherRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyWeatherRv.setHasFixedSize(true)
        val adapter = HourlyWeatherAdapter(this, IntArray(24))
        adapter.setHourlyWeather(weather)
        binding.hourlyWeatherRv.adapter = adapter

    }

    private fun updateWeatherIV(weatherType: String) {
        when (weatherType) {
            Constants.sunny_key -> {
                Glide.with(requireContext())
                    .load(R.drawable.sunny)
                    .into(binding.weatherIv)
            }
            Constants.night_key -> {
                Glide.with(requireContext())
                    .load(R.drawable.night)
                    .into(binding.weatherIv)
            }
            Constants.cloudy_key -> {
                Glide.with(requireContext())
                    .load(R.drawable.cloudy)
                    .into(binding.weatherIv)
            }
            Constants.cloudy_sunny_key -> {
                Glide.with(requireContext())
                    .load(R.drawable.sun_cloud)
                    .into(binding.weatherIv)
            }
            Constants.rainy_key -> {
                Glide.with(requireContext())
                    .load(R.drawable.rainy)
                    .into(binding.weatherIv)
            }
            Constants.snow_key -> {
                Glide.with(requireContext())
                    .load(R.drawable.snow)
                    .into(binding.weatherIv)
            }
        }
    }


}