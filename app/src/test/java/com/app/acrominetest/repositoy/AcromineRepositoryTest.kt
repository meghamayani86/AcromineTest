package com.app.acrominetest.repositoy

import com.app.acrominetest.repository.AcromineRepository
import com.app.acrominetest.retrofit.AcromineAPI
import com.app.acrominetest.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class AcromineRepositoryTest {

    @Mock
    lateinit var acromineApi: AcromineAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

    }

    @Test
    fun test_fetchAbbreviation_EmptyList() = runTest {
        Mockito.`when`(acromineApi.fetchAcromine("NASA")).thenReturn(Response.success(emptyList()))
        val repository = AcromineRepository(acromineApi)
        val result = repository.fetchAcromine("NASA")
        Assert.assertEquals(false, result is NetworkResult.Success)


    }

    @Test
    fun test_fetchAbbreviation_EmptyResult() = runTest {
        Mockito.`when`(acromineApi.fetchAcromine("")).thenReturn(Response.success(emptyList()))
        val repository = AcromineRepository(acromineApi)
        val result = repository.fetchAcromine("")
        Assert.assertEquals(false, result is NetworkResult.Success)


    }



    @Test
    fun test_fetchAbbreviation_ExpectedError() = runTest {
        Mockito.`when`(acromineApi.fetchAcromine("NASA"))
            .thenReturn(Response.error(401, "Unauthorized".toResponseBody()))
        val repository = AcromineRepository(acromineApi)
        val result = repository.fetchAcromine("NASA")
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something Went Wrong!!", result?.message)

    }
}