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
    val title: String,
    val price: Price,
    val visits: Long,
    val address: Address,
    //todo adapter format 2021-10-08T08:01:00.000+0100
    @Json(name = "posted-date-time")
    val postedDateTime: String,
    val description: String,
    val attributes: List<Attribute>,
    val features: List<String>,
    val pictures: List<String>,
    val documents: List<Document>
)