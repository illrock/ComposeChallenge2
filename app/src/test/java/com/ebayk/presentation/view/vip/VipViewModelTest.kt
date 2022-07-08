package com.ebayk.presentation.view.vip

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ebayk.data.network.response.ApiResult
import com.ebayk.data.network.response.ad.AdResponse
import com.ebayk.data.repository.AdRepository
import com.ebayk.presentation.view.util.ViewModelResult
import com.ebayk.testutil.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VipViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val adRepository = mockk<AdRepository> {
        coEvery { getAd(any()) } returns ApiResult.Success(AdResponse(AD_ID))
    }

    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testCoroutineScheduler)

    private lateinit var vm: VipViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = VipViewModel(adRepository, testDispatcher)
    }

    @Test
    fun test_success() = runTest(testDispatcher) {
        vm.loadAd(AD_ID)
        val resultLoading = vm.result.getOrAwaitValue()
        assertTrue(resultLoading is ViewModelResult.Loading)

        testCoroutineScheduler.runCurrent()

        val resultSuccess = vm.result.getOrAwaitValue()
        assertTrue(resultSuccess is ViewModelResult.Success)

        val data = (resultSuccess as ViewModelResult.Success).data
        assertEquals(AD_ID, data.id)

        coVerify(exactly = 1) { adRepository.getAd(AD_ID) }
    }

    @Test
    fun test_error() = runTest(testDispatcher) {
        val expectedException = Exception("Gargantua")
        coEvery { adRepository.getAd(any()) } returns ApiResult.Error(expectedException)

        vm.loadAd(AD_ID)
        val resultLoading = vm.result.getOrAwaitValue()
        assertTrue(resultLoading is ViewModelResult.Loading)

        testCoroutineScheduler.runCurrent()

        val resultError = vm.result.getOrAwaitValue()
        assertTrue(resultError is ViewModelResult.Error)

        val data = resultError as ViewModelResult.Error
        assertEquals(null, data.errorRes)
        assertEquals(expectedException.message, data.errorMessage)
        assertEquals(com.ebayk.R.drawable.ic_mood_bad, data.iconRes)

        coVerify(exactly = 1) { adRepository.getAd(AD_ID) }
    }

    private companion object {
        const val AD_ID = "ThreeTwoOne"
    }
}