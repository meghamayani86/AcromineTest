package com.app.acrominetest

import com.app.acrominetest.retrofit.AcromineAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AcromineAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var acromineAPI: AcromineAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        acromineAPI = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AcromineAPI::class.java)
    }

    @Test
    fun testFetchAbbreviation() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)
        val response = acromineAPI.fetchAcromine("NASA")
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()?.isEmpty())
    }

    @Test
    fun testFetchAbbreviation_returnData() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)
        val response = acromineAPI.fetchAcromine("NASA")
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body()?.isEmpty())
        Assert.assertEquals(1, response.body()?.size)
    }
    @Test
    fun testFetchAbbreviation_Error() = runTest {
        val mockResponse = MockResponse()

        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong!!")
        mockWebServer.enqueue(mockResponse)
        val response = acromineAPI.fetchAcromine("NASA")
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}