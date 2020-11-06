package com.aparshikhin.sensors.retrofit2

import com.aparshikhin.sensors.models.CommandDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @Headers("Content-Type: application/json")
    @POST("command")
    fun sendCommand(@Body command: CommandDTO): Call<ResponseBody>
}