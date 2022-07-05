package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.item.BodyText
import com.ebayk.presentation.view.common.compose.item.ItemDivider

@Composable
internal fun VipDescription(description: String) {
    Column(
        modifier = Modifier
            .padding(PADDING_STANDARD.dp)
    ) {
        VipTitle(title = stringResource(id = R.string.vip_description_section_header))
        ItemDivider(
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
        )
        BodyText(
            text = description,
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
        )
    }
}