package com.example.weatherapplication_xml.domain.location

import android.location.Location


interface LocationTracker {
    suspend fun getCurrentLocation() : Location?
}