package com.silverorange.videoplayer.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.silverorange.videoplayer.data.model.APIResponse
import com.silverorange.videoplayer.data.util.Resource
import com.silverorange.videoplayer.domain.usecase.GetVideosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel(val app: Application, val getVideosUseCase: GetVideosUseCase) : AndroidViewModel(app) {

    val videos : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getVideos() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                videos.postValue(Resource.Loading())
                val apiResult = getVideosUseCase.execute()
                videos.postValue(apiResult)
            } else {
                videos.postValue(Resource.Error("Internet is not available"))
            }
        }catch (e:Exception){
                videos.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


}