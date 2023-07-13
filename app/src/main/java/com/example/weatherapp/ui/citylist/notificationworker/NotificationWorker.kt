package com.example.weatherapp.ui.citylist.notificationworker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapp.data.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository
) : CoroutineWorker(
    appContext, workerParams
) {
    override suspend fun doWork(): Result {
        val data = weatherRepository.getWeatherDataCurrentLocation(24.5, 90.1)
        data.collect {
            Log.d("TAG", "doWork: $it")
        }
        return Result.success()
    }
}