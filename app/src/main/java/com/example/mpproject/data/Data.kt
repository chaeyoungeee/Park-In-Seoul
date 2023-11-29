package com.example.mpproject.data

import java.io.Serializable

data class DataPopulation(val fcstPopulation: Any?) : Serializable
data class DataWeather(val fcstWeather: Any?) : Serializable
data class DataEvent(val event: Any?) : Serializable