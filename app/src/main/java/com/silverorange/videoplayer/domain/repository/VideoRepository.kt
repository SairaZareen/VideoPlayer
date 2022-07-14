package com.silverorange.videoplayer.domain.repository

import com.silverorange.videoplayer.data.model.APIResponse
import com.silverorange.videoplayer.data.util.Resource

interface VideoRepository {

    suspend fun getVideos() : Resource<APIResponse>
}