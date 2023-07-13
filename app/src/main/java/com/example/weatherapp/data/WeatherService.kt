package com.example.weatherapp.data

import com.example.weatherapp.data.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("data/2.5/find")
    suspend fun getWeatherData(
     @Query("lat") lat: Double,
     @Query("lon") lon: Double,
     @Query("cnt") cnt: Int,
     @Query("units") unit: String,
     @Query("appid") apiKey: String
    ): Response<WeatherApiResponse>
}