package com.ebayk.data.repository

import com.ebayk.data.network.ApiService
import com.ebayk.data.network.response.ApiResult
import com.ebayk.data.network.response.ad.AdResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AdRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()

    private val apiService = mockk<ApiService> {
        coEvery { getAd(any()) } returns AdResponse(AD_ID)
    }

    private lateinit var repository: AdRepository

    @Before
    fun setUp() {
        repository = AdRepository(apiService, testDispatcher)
    }

    @Test
    fun getAd_success() = runTest(testDispatcher) {
        val result = repository.getAd(AD_ID)

        assertTrue(result is ApiResult.Success)
        val data = (result as ApiResult.Success).data
        assertEquals(AD_ID, data.id)

        coVerify(exactly = 1) { apiService.getAd(AD_ID) }
    }

    @Test
    fun getAd_error() = runTest(testDispatcher) {
        val expectedException = Exception("Hello there")
        coEvery { apiService.getAd(any()) } throws expectedException

        val result = repository.getAd(AD_ID)

        assertTrue(result is ApiResult.Error)
        val actualException = (result as ApiResult.Error).exception
        assertEquals(expectedException, actualException)

        coVerify(exactly = 1) { apiService.getAd(AD_ID) }
    }

    private companion object {
        const val AD_ID = "sampleId123"
    }
}