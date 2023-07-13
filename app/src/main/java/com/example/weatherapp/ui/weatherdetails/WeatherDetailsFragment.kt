package com.example.weatherapp.ui.weatherdetails

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCityListBinding
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailsFragment : OnMapReadyCallback, Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get()  = _binding!!

    private val args : WeatherDetailsFragmentArgs by navArgs()
    private lateinit var mMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)

        binding.backArrowIv.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.weather_map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
        showCityWeatherDetails()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val latLong = LatLng(args.cityWeatherDetails.latitude,args.cityWeatherDetails.longitude)
        mMap.addMarker(MarkerOptions().position(latLong).title(args.cityWeatherDetails.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong))
    }


    private fun showCityWeatherDetails(){
        binding.weatherDetailsCityName.text = args.cityWeatherDetails.name
        binding.weatherDetailsCloudStatusTv.text = args.cityWeatherDetails.weatherStatus
        binding.weatherDetailsWindSpeedTv.text = getString(R.string.wind_speed, args.cityWeatherDetails.windSpeed.toString())
        binding.weatherDetailsHumidityTv.text = getString(R.string.humidity, args.cityWeatherDetails.humidity.toString())
        binding.weatherDetailsTemperatureTextView.text = getString(R.string.temperature, args.cityWeatherDetails.temperature.toString())
        binding.weatherDetailsMaxTempTv.text = getString(R.string.max_temp, args.cityWeatherDetails.maxTemp.toString())
        binding.weatherDetailsMinTempTv.text = getString(R.string.min_temp, args.cityWeatherDetails.minTemp.toString())
    }
}