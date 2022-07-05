package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.PADDING_MEDIUM
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.item.BodyText
import com.ebayk.presentation.view.common.compose.item.ItemDivider
import com.ebayk.presentation.view.vip.model.VipAd

@Composable
internal fun VipDetails(details: List<VipAd.VipAttribute>) {
    Column(
        modifier = Modifier
            .padding(PADDING_STANDARD.dp)
    ) {
        VipTitle(title = stringResource(id = R.string.vip_details_section_header))
        ItemDivider(
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
        )
        details.forEachIndexed { index, attribute ->
            VipDetailsItem(attribute)
            if (index != details.lastIndex) ItemDivider()
        }
    }
}

@Composable
private fun VipDetailsItem(attribute: VipAd.VipAttribute) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = PADDING_MEDIUM.dp)
    ) {
        BodyText(
            text = attribute.label,
            modifier = Modifier
                .weight(1f)
        )
        BodyText(
            text = attribute.value,
            colorRes = R.color.text_secondary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
        )
    }
}