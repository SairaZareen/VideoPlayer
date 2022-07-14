package com.silverorange.videoplayer.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class VideoAPIServiceTest {
    private lateinit var service:VideoAPIService
    private lateinit var server:MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoAPIService::class.java)
    }

    private fun enqueueMockResponse(fileName:String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }
    @Test
    fun getVideos_sendRequest_recievedExpected(){
        runBlocking {
            enqueueMockResponse("videoresponse.json")
            val responseBody = service.getVideos()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
        }
    }
    @After
    fun tearDown() {
        server.shutdown()
    }
}