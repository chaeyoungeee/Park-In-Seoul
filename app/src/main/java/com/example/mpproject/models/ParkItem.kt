package com.example.mpproject.models

data class ParkItem(
    val name: String,
    var congestLevel: String,
    var temp: String,
    var minTemp: String,
    var maxTemp: String,
    var code: String,
    var skyStatus:String,
)