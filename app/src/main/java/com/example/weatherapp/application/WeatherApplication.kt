package com.example.weatherapp.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import com.example.weatherapp.util.AppConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication: Application () {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                AppConstants.NOTIFICATION_CHANNEL_ID,
                "Weather App Notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        Log.d("asd", "createNotificationChannel: ")
    }
}