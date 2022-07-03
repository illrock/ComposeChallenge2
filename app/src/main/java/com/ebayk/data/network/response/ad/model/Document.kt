package com.ebayk.data.network.response.ad.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Document(
    val link: String,
    val title: String
)