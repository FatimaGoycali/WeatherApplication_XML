package com.example.weatherapplication_xml.data.repository


import com.example.weatherapplication_xml.data.mappers.toWeatherInfo
import com.example.weatherapplication_xml.data.remote.WeatherApi
import com.example.weatherapplication_xml.domain.repository.WeatherRepository
import com.example.weatherapplication_xml.domain.util.Resource
import com.example.weatherapplication_xml.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )


        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "A unknown error occurred.")
        }
    }
}