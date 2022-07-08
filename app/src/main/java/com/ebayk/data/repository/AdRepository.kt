package com.ebayk.data.repository

import com.ebayk.data.network.ApiService
import com.ebayk.data.network.response.ApiResult
import com.ebayk.data.network.response.ad.AdResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class AdRepository @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getAd(id: String): ApiResult<AdResponse> = withContext(dispatcher) {
        getFromNetwork(id)
    }

    private suspend fun getFromNetwork(id: String): ApiResult<AdResponse> = try {
        val response = apiService.getAd(id)
        ApiResult.Success(response)
    } catch (e: Exception) {
        ApiResult.Error(e)
    }
}