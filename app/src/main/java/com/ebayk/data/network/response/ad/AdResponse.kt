package com.ebayk.data.network.response.ad

import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.data.network.response.ad.model.Attribute
import com.ebayk.data.network.response.ad.model.Document
import com.ebayk.data.network.response.ad.model.Price
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdResponse(
    val id: String,
    val title: String = "",
    val price: Price? = null,
    val visits: Long = -1L,
    // Just an example of nullable field
    val address: Address? = null,
    @Json(name = "posted-date-time")
    val postedDateTime: String = "",
    val description: String = "",
    val attributes: List<Attribute> = listOf(),
    val features: List<String> = listOf(),
    val pictures: List<String> = listOf(),
    val documents: List<Document> = listOf()
)