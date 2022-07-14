package com.silverorange.videoplayer.data.repository

import com.silverorange.videoplayer.data.model.APIResponse
import com.silverorange.videoplayer.data.repository.dataSource.VideoRemoteDataSource
import com.silverorange.videoplayer.data.util.Resource
import com.silverorange.videoplayer.domain.repository.VideoRepository
import retrofit2.Response

class VideoRepositoryImpl(private val videoRemoteDataSource: VideoRemoteDataSource) :VideoRepository{
    override suspend fun getVideos(): Resource<APIResponse> {
        return responseToResource(videoRemoteDataSource.getVideos())
    }

    private fun responseToResource(response: Response<APIResponse>):Resource<APIResponse>{
        if (response.isSuccessful){
            response.body()?.let { result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}