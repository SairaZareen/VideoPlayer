package com.silverorange.videoplayer.domain.usecase

import com.silverorange.videoplayer.data.model.APIResponse
import com.silverorange.videoplayer.data.util.Resource
import com.silverorange.videoplayer.domain.repository.VideoRepository

class GetVideosUseCase(private val videoRepository : VideoRepository) {

        suspend fun execute() : Resource<APIResponse>{
            return videoRepository.getVideos()
        }
}