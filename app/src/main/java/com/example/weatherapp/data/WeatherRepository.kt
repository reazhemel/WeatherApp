package com.example.weatherapp.data

import com.example.weatherapp.data.model.WeatherApiResponse
import com.example.weatherapp.data.model.WeatherCurrentLocationData
import com.example.weatherapp.util.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService
) {
    fun getWeatherData(lat: Double, lon: Double): Flow<WeatherApiResponse> = flow {
        val weatherApiResult = weatherService.getWeatherData(
            lat = lat,
            lon = lon,
            cnt = 50,
            unit = "metric",
            apiKey = AppConstants.API_KEY
        )

        if (weatherApiResult.isSuccessful) {
            weatherApiResult.body()?.let { weatherApiResponse ->
                emit(weatherApiResponse)
            }
        }
    }

    fun getWeatherDataCurrentLocation(
        lat: Double,
        lon: Double
    ): Flow<WeatherCurrentLocationData> = flow {
        val currentLocationWeather = weatherService.getWeatherDataCurrentLocation(
            lat = lat,
            lon = lon
        )

        if (currentLocationWeather.isSuccessful) {
            currentLocationWeather.body()?.let {
                emit(it)
            }
        }
    }
}