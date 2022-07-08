package com.ebayk.presentation.view.vip.model

import com.ebayk.data.network.response.ad.AdResponse
import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.data.network.response.ad.model.Attribute
import com.ebayk.data.network.response.ad.model.Document
import com.ebayk.util.CurrencyUtils
import com.ebayk.util.DateUtils

data class VipAd(
    // id is usually Long, but I've had string in your response
    val id: String,
    val title: String = "",
    val priceWithAmount: String? = null,
    val visits: Long = -1L,
    val description: String = "",
    val address: Address? = null,
    val postedDate: String? = null,
    val attributes: List<VipAttribute> = listOf(),
    val features: List<String> = listOf(),
    val lowResPictures: List<String> = listOf(),
    val highResPictures: List<String> = listOf(),
    val documents: List<Document> = listOf()
) {
    constructor(adResponse: AdResponse): this(
        adResponse.id,
        adResponse.title,
        adResponse.price?.let { CurrencyUtils.formatCurrency(it.currency, it.amount) },
        adResponse.visits,
        adResponse.description,
        adResponse.address,
        DateUtils.simpleDateFormat(adResponse.postedDateTime),
        adResponse.attributes.map { VipAttribute(it) },
        adResponse.features,
        adResponse.pictures.map { it.toLowResUrl() },
        adResponse.pictures.map { it.toHighResUrl() },
        adResponse.documents
    )

    data class VipAttribute(
        val label: String,
        val value: String
    ) {
        constructor(rawAttribute: Attribute): this(
            rawAttribute.label,
            "${rawAttribute.value} ${rawAttribute.unit.orEmpty()}"
        )
    }

    companion object {
        private const val IMAGE_ID_PLACEHOLDER = "{imageId}"
        private const val IMAGE_ID_VALUE_LOW_RES = "1"
        private const val IMAGE_ID_VALUE_HIGH_RES = "57"

        private fun String.toLowResUrl() = replace(IMAGE_ID_PLACEHOLDER, IMAGE_ID_VALUE_LOW_RES)
        private fun String.toHighResUrl() = replace(IMAGE_ID_PLACEHOLDER, IMAGE_ID_VALUE_HIGH_RES)
    }
}