package com.example.appmeteo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherAPI {
    @GET("/data/2.5/weather") //lien
    fun getWeatherStops(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): Call<WeatherStop> //les variables lat long apikey

}