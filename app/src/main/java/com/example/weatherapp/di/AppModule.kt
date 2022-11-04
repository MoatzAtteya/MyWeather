package com.example.weatherapp.di

import com.example.weatherapp.common.Constants
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImp
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi() : WeatherApi{
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
         val okHttpClient = OkHttpClient.Builder().addInterceptor(logger)

        return Retrofit.Builder()
            .baseUrl(Constants.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: WeatherApi) : WeatherRepository{
        return WeatherRepositoryImp(api)
    }

}