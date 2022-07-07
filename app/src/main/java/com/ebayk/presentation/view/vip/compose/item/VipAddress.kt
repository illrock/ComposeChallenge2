package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.item.BodyText

@Composable
internal fun VipAddress(address: Address?, onClick:(address: Address) -> Unit) {
    address?.let {
        BodyText(
            text = "${it.street}, ${it.zipCode} ${it.city}",
            colorRes = R.color.text_secondary,
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
                .padding(horizontal = PADDING_STANDARD.dp)
                .clickable { onClick(it) }
        )
    }
}