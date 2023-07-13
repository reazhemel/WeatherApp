package com.example.weatherapp.ui.citylist

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.work.*
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCityListBinding
import com.example.weatherapp.ui.citylist.notificationworker.NotificationWorker
import com.example.weatherapp.ui.weatherdetails.CityWeatherDetails
import com.example.weatherapp.util.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CityListFragment : Fragment() {

    private lateinit var notification: Notification
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
            }
            else -> {
            }
        }
    }

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

        notificationManager = NotificationManagerCompat.from(requireContext())

        binding.weatherTitle.setOnClickListener {
            Log.d(TAG, "onCreateView: ")
            showNotification()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        initialiseWorker()
        createNotification()
        showNotification()

    }

    private fun initialiseWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
        val weatherRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints.build())
                .build()

        val workManager = WorkManager
            .getInstance(requireContext())
        workManager.enqueueUniquePeriodicWork(
            "WeatherLocation",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            weatherRequest
        )
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
    private fun showNotification() {
        Log.d("asd", "showNotification: ")

        notificationManager.notify(
            AppConstants.NOTIFICATION_ID,
            notification
        )
    }

    private fun createNotification() {
        notification = NotificationCompat.Builder(
            requireContext(),
            AppConstants.NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_sunny)
            .setContentTitle("WeatherApp")
            .setContentText("Current Temperature is 25c")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

}