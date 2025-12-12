package com.example.network.models

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("courses")
    val courses: List<CourseResponse>
)
