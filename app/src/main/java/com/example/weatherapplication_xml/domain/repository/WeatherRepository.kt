package com.example.weatherapplication_xml.domain.repository

import com.example.weatherapplication_xml.domain.util.Resource
import com.example.weatherapplication_xml.domain.weather.WeatherInfo

interface WeatherRepository{
    suspend fun getWeatherData(lat :Double, long:Double) : Resource<WeatherInfo>
}