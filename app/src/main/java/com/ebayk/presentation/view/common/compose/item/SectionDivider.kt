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
import com.ebayk.presentation.view.common.compose.constants.DIVIDER_SECTION_HEIGHT

@Composable
internal fun SectionDivider(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .height(DIVIDER_SECTION_HEIGHT.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.section_divider_color))
    )
}