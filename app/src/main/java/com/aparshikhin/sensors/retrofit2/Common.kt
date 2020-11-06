package com.aparshikhin.sensors.retrofit2

object Common {
    private const val BASE_URL = "http://192.168.1.108:3000/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}