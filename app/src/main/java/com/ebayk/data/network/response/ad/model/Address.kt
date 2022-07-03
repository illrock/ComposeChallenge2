package com.ebayk.data.network.response.ad.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    val street: String,
    val city: String,
    @Json(name = "zip-code")
    val zipCode: String,
    val longitude: Double,
    val latitude: Double
)