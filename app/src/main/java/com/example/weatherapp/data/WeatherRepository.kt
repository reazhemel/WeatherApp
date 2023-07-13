package com.example.weatherapp.data

import com.example.weatherapp.data.model.WeatherApiResponse
import com.example.weatherapp.util.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
   private val weatherService: WeatherService
) {
    fun getWeatherData(): Flow<WeatherApiResponse> = flow {
        val weatherApiResult = weatherService.getWeatherData(
            lat = 23.68,
            lon = 90.35,
            cnt = 50,
            unit = "metric",
            apiKey = AppConstants.API_KEY
        )

        if(weatherApiResult.isSuccessful){
            weatherApiResult.body()?.let { weatherApiResponse ->
                emit(weatherApiResponse)
            }
        }
    }

}