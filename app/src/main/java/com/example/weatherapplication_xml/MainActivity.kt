package com.example.weatherapplication_xml

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication_xml.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var weatherForecastAdapter: WeatherForecastAdapter? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var hourlyData: ArrayList<String>

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        weatherForecastAdapter = WeatherForecastAdapter()

        binding.recyclerView.adapter = weatherForecastAdapter

        lifecycleScope.launch {
            viewModel.state.collect {
                initCardView(it)

                initRecyclerView(it)

            }
        }
    }


    fun initCardView(state: WeatherState) {
        state.weatherInfo?.currentWeatherData?.let { data ->
            val todayText = "Today ${
                data.time.format(
                    DateTimeFormatter.ofPattern("HH:mm")
                )
            }"

            binding.todayText.text = todayText
            binding.icon.setImageResource(data.weatherType.iconRes)
            val temperature = "${data.temperatureCelsius.toString()}°C"
            binding.temperature.text = temperature
            binding.weatherType.text = data.weatherType.weatherDesc
            val pressure = "${data.pressure.roundToInt().toString()}hpa"
            binding.pressure.text = pressure
            val humidity = "${data.humidity.roundToInt().toString()}%"
            binding.humidity.text = humidity
            val windSpeed = "${data.windSpeed.roundToInt().toString()}km/h"
            binding.windSpeed.text = windSpeed

        }

    }

    fun initRecyclerView(state: WeatherState) {
        state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->

            weatherForecastAdapter?.emit(data)

        }
    }


}



