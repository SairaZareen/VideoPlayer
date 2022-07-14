package com.silverorange.videoplayer.data.api

import com.silverorange.videoplayer.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET

interface VideoAPIService {
    @GET("/videos")
    suspend fun getVideos():Response<APIResponse>
}