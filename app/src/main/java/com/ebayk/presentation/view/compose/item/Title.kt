package com.ebayk.presentation.view.compose.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ebayk.R
import com.ebayk.presentation.view.compose.PADDING_STANDARD
import com.ebayk.presentation.view.compose.TEXT_SIZE_HEADER

@Composable
internal fun Title(title: String) {
    Text(
        text = title,
        fontFamily = FontFamily(Font(R.font.roboto_medium, FontWeight.Medium)),
        fontSize = TEXT_SIZE_HEADER.sp,
        color = colorResource(id = R.color.text_main),
        modifier = Modifier
            .padding(horizontal = PADDING_STANDARD.dp)
            .padding(top = PADDING_STANDARD.dp)
    )
}