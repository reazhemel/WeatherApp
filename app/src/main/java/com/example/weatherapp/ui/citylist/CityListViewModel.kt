package com.example.weatherapp.ui.citylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.model.CityWeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _cityWeatherInfoState = MutableStateFlow<List<CityWeatherInfo>>(emptyList())
    val cityWeatherInfoState = _cityWeatherInfoState.asStateFlow()

    init {
        getCityWeatherInfoList()
    }

    private fun getCityWeatherInfoList(){
        viewModelScope.launch {
            weatherRepository.getWeatherData().collect { weatherApiResponse ->
                Log.d("asd", "apiResponse: $weatherApiResponse")
                _cityWeatherInfoState.value = weatherApiResponse.list
            }
        }
    }
}