package com.silverorange.videoplayer.data.repository.dataSource

import com.silverorange.videoplayer.data.model.APIResponse
import retrofit2.Response

interface VideoRemoteDataSource {
    suspend fun getVideos():Response<APIResponse>
}