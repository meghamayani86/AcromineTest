package com.app.acrominetest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.acrominetest.getOrAwaitValue
import com.app.acrominetest.repository.AcromineRepository
import com.app.acrominetest.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AcromineViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: AcromineRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_fetchAbbreviation() = runTest {
        Mockito.`when`(repository.fetchAcromine("NASA"))
            .thenReturn(NetworkResult.Success(emptyList()))
        val acromineViewModel = AcromineViewModel(repository)
        acromineViewModel.fetchAbbreviation("NASA")
        with(testDispatcher) {
            acromineViewModel.fetchAbbreviation("NASA")
            scheduler.advanceUntilIdle()
        }
        val result = acromineViewModel.acromineLiveData.getOrAwaitValue()
        Assert.assertEquals(0, result.data?.size)

    }

    @Test
    fun test_fetchAbbreviation_expectedError() = runTest {
        Mockito.`when`(repository.fetchAcromine("NASA"))
            .thenReturn(NetworkResult.Error(""))
        val acromineViewModel = AcromineViewModel(repository)
        acromineViewModel.fetchAbbreviation("NASA")
        with(testDispatcher) {
            acromineViewModel.fetchAbbreviation("NASA")
            scheduler.advanceUntilIdle()
        }
        val result = acromineViewModel.acromineLiveData.getOrAwaitValue()
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("", result.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}