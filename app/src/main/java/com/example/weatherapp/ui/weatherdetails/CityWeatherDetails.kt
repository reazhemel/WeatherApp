package com.example.weatherapp.ui.weatherdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityWeatherDetails(
    val name: String,
    val weatherStatus: String,
    val temperature: Double,
    val latitude: Double,
    val longitude: Double,
    val humidity: Int,
    val windSpeed: Double,
    val maxTemp: Double,
    val minTemp: Double
): Parcelable