package com.example.weatherapp.ui.citylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.CityWeatherInfo

class CityWeatherAdapter(
    private val onCityClick: (CityWeatherInfo) -> Unit
) : RecyclerView.Adapter<CityWeatherAdapter.WeatherViewHolder>() {

    private var cityWeatherInfoList: List<CityWeatherInfo> = emptyList()

    inner class WeatherViewHolder(weatherView: View) : RecyclerView.ViewHolder(weatherView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityWeatherInfoList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val cityWeather = cityWeatherInfoList[position]
        holder.itemView.findViewById<TextView>(R.id.city_name_text_view).text = cityWeather.name
        holder.itemView.findViewById<TextView>(R.id.weather_status_text_view).text = cityWeather.weather.first().description
        holder.itemView.findViewById<TextView>(R.id.city_temperature_text_view).text =
            holder.itemView.context.getString(R.string.temperature,cityWeather.main.temp.toString())

        holder.itemView.setOnClickListener {
            onCityClick(cityWeather)
        }
    }

    fun submitCityWeatherList(cityWeatherList: List<CityWeatherInfo>){
        this.cityWeatherInfoList = cityWeatherList
        notifyItemRangeChanged(0, cityWeatherList.size)
    }
}