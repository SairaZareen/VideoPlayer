package com.silverorange.videoplayer.data.repository.dataSourceImpl

import com.silverorange.videoplayer.data.api.VideoAPIService
import com.silverorange.videoplayer.data.model.APIResponse
import com.silverorange.videoplayer.data.repository.dataSource.VideoRemoteDataSource
import retrofit2.Response

class VideoRemoteDataSourceImpl(private val videoAPIService: VideoAPIService) : VideoRemoteDataSource {
    override suspend fun getVideos(): Response<APIResponse> {
        return videoAPIService.getVideos()
    }
}