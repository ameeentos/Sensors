package com.aparshikhin.sensors.models

import com.google.gson.annotations.SerializedName

data class CommandDTO(
    @SerializedName("command") val command: String?
)