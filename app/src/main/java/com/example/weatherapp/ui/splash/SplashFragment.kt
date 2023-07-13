package com.example.weatherapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import kotlinx.coroutines.*

class SplashFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scope.launch {
            delay(1000L)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToCityListFragment())
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onPause() {
        scope.cancel()
        super.onPause()
    }

}