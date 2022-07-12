package com.example.appmeteo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val weatherUrl = "https://api.openweathermap.org"
    private var latitude = ""
    private var longitude = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        System.out.println("test")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {
            location : Location? ->
            if (location != null) {
                Toast.makeText(this@MainActivity, "Latitude:" + location.latitude + "Longitude: " + location.longitude, Toast.LENGTH_LONG).show()
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
            }else{
                Toast.makeText(this@MainActivity, "null", Toast.LENGTH_LONG).show()

            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(weatherUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IWeatherAPI::class.java)
        val weatherStops = service.getWeatherStops(latitude, longitude, apiKey = "60f762fb029c28aae2cb2f6b6189ffdb")

        System.out.println("sheshhhhhhhh" + weatherUrl)
        weatherStops.enqueue(object : Callback<WeatherStop> {
            override fun onResponse(
                call: Call<WeatherStop>,
                response: Response<WeatherStop>
            ) {
                val allWeatherStop = response.body()
                        Log.d("Weather:","Pressure ${allWeatherStop}")
            }

            override fun onFailure(call: Call<WeatherStop>, t: Throwable) {
                Log.e("Weather", "Error : $t")
            }


        })


    }




}