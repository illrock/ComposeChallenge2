package com.ebayk.presentation.view.vip.model

import com.ebayk.data.network.response.ad.AdResponse
import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.data.network.response.ad.model.Attribute
import com.ebayk.data.network.response.ad.model.Document
import com.ebayk.util.CurrencyUtils
import com.ebayk.util.DateUtils

data class VipAd(
    val id: String,
    val title: String,
    val priceWithAmount: String,
    val visits: Long,
    val address: Address,
    val postedDate: String,
    val description: String,
    val attributes: List<VipAttribute>,
    val features: List<String>,
    val lowResPictures: List<String>,
    val highResPictures: List<String>,
    val documents: List<Document>
) {
    constructor(adResponse: AdResponse): this(
        adResponse.id,
        adResponse.title,
        CurrencyUtils.formatCurrency(adResponse.price.currency, adResponse.price.amount),
        adResponse.visits,
        adResponse.address,
        DateUtils.simpleDateFormat(adResponse.postedDateTime),
        adResponse.description,
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