package com.example.weatherapp.ui.citylist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCityListBinding
import com.example.weatherapp.ui.weatherdetails.CityWeatherDetails
import com.example.weatherapp.util.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityListFragment : Fragment() {

    private var _binding: FragmentCityListBinding? = null
    private val binding get() = _binding!!

    private lateinit var cityWeatherAdapter: CityWeatherAdapter

    private val viewModel by viewModels<CityListViewModel>()

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)

        setUpCityWeatherListAdapter()

        observeCityWeatherInfo()

        notificationManager = NotificationManagerCompat.from(requireActivity())

        binding.weatherTitle.setOnClickListener {
            showNotification()
        }

        return binding.root
    }



    private fun setUpCityWeatherListAdapter() {
        cityWeatherAdapter = CityWeatherAdapter(onCityClick = {

            findNavController().navigate(
                CityListFragmentDirections.actionCityListFragmentToWeatherDetailsFragment(
                    CityWeatherDetails(
                        name = it.name,
                        temperature = it.main.temp,
                        weatherStatus = it.weather.first().description,
                        latitude = it.coord.lat,
                        longitude = it.coord.lon,
                        humidity = it.main.humidity,
                        windSpeed = it.wind.speed,
                        maxTemp = it.main.temp_max,
                        minTemp = it.main.temp_min
                    )
                )
            )
        })

        val layoutManager = LinearLayoutManager(requireActivity())

        binding.weatherRecyclerView.layoutManager = layoutManager

        binding.weatherRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(), layoutManager.orientation
            )
        )
        binding.weatherRecyclerView.adapter = cityWeatherAdapter
    }

    private fun observeCityWeatherInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityWeatherInfoState.collect {
                    cityWeatherAdapter.submitCityWeatherList(it)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(){
        Log.d("asd", "showNotification: ")

        val notification = NotificationCompat.Builder(
            requireActivity(),
            AppConstants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("WeatherApp")
            .setContentText("Current Temperature is 25c")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(
            AppConstants.NOTIFICATION_ID,
            notification
        )
    }

}