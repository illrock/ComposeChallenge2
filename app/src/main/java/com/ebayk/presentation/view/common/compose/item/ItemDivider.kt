package com.ebayk.presentation.view.common.compose.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.DIVIDER_ITEM_HEIGHT

@Composable
internal fun ItemDivider(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .height(DIVIDER_ITEM_HEIGHT.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.item_divider_color))
    )
}