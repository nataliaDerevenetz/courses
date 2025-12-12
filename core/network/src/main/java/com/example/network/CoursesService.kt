package com.example.network

import com.example.network.models.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface CoursesService {
    @GET
    @Streaming
    suspend fun downloadFile(@Url url: String): Response<BaseResponse>
}