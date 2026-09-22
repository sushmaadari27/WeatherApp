package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocation =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

            val coarseLocation =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (fineLocation || coarseLocation) {
                getWeatherFromLocation()
            } else {
                WeatherState.error = "Location permission denied"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherApp()
        }

        checkLocationPermission()
    }

    private fun checkLocationPermission() {

        val fineLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineLocation || coarseLocation) {
            getWeatherFromLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun getWeatherFromLocation() {

        WeatherState.error = ""
        WeatherState.temperature = null

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdateAgeMillis(0)
            .build()

        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            locationRequest,
            cancellationTokenSource.token
        )
            .addOnSuccessListener { location ->

                if (location == null) {
                    WeatherState.error = "Could not get current location"
                    return@addOnSuccessListener
                }

                val latitude = location.latitude
                val longitude = location.longitude

                getWeather(latitude, longitude)
            }
            .addOnFailureListener {
                WeatherState.error = "Could not get current location"
            }
    }

    private fun getWeather(
        latitude: Double,
        longitude: Double
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)

        lifecycleScope.launch {

            try {

                val result = withContext(Dispatchers.IO) {

                    weatherApi.getWeather(
                        latitude = latitude,
                        longitude = longitude,
                        apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                    )
                }

                WeatherState.temperature = result.main.temp

            } catch (e: Exception) {

                WeatherState.error = "Unable to get weather: ${e.message}"
            }
        }
    }
}

object WeatherState {

    var temperature by mutableStateOf<Double?>(null)

    var error by mutableStateOf("")
}

@Composable
fun WeatherApp() {

    val temperature = WeatherState.temperature
    val error = WeatherState.error

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when {

            temperature == null && error.isEmpty() -> {

                CircularProgressIndicator()
            }

            error.isNotEmpty() -> {

                Text(
                    text = error
                )
            }

            else -> {

                Text(
                    text = "${temperature}°C"
                )
            }
        }
    }
}