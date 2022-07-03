package com.ebayk.data.network.response.ad.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
    val currency: String,
    val amount: Long
)