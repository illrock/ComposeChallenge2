package com.ebayk.data.network.response.ad.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attribute(
    val label: String,
    val value: String,
    val unit: String? = null
)