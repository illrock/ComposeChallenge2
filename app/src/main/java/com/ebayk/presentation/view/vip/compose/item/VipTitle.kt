package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.TEXT_SIZE_HEADER

@Composable
internal fun VipTitle(
    title: String?,
    colorRes: Int = R.color.text_main,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    title?.takeIf { it.isNotBlank() }?.let {
        Text(
            text = it,
            fontFamily = FontFamily(Font(R.font.roboto_medium, FontWeight.Medium)),
            fontSize = TEXT_SIZE_HEADER.sp,
            color = colorResource(id = colorRes),
            textAlign = textAlign,
            modifier = modifier
        )
    }
}