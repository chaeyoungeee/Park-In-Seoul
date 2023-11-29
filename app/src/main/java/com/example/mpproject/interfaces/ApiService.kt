package com.example.mpproject.interfaces

import com.github.mikephil.charting.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path


interface ParkApiService {
    @GET("/xml/citydata/1/5/{name}")
    suspend fun getParkData(@Path("name") name: String): retrofit2.Response<ResponseBody>
}