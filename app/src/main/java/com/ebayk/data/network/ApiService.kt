package com.ebayk.data.network

import com.ebayk.data.network.response.ad.AdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("ads/{ad_id}")
    suspend fun getAd(
        @Path("ad_id") adId: String
    ) : AdResponse
}