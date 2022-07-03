package com.ebayk.data.network.interceptor

import android.util.Base64
import com.ebayk.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedRequest = request.newBuilder()
            .header(HEADER_AUTH, createBasicAuth())
            .build()
        return chain.proceed(modifiedRequest)
    }

    private fun createBasicAuth(): String {
        val credentials = "${BuildConfig.API_USERNAME}:${BuildConfig.API_PASSWORD}"
        return "Basic " + Base64.encodeToString(credentials.encodeToByteArray(), Base64.NO_WRAP)
    }

    companion object {
        private const val HEADER_AUTH = "Authorization"
    }
}