package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherService
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherService{
        return retrofit.create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository = WeatherRepository(weatherService)
}