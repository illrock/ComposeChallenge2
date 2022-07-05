package com.ebayk.presentation.view.common.compose.item

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
import com.ebayk.presentation.view.common.compose.constants.TEXT_SIZE_BODY

@Composable
internal fun BodyText(
    text: String,
    colorRes: Int = R.color.text_main,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = colorResource(id = colorRes),
        textAlign = textAlign,
        fontSize = TEXT_SIZE_BODY.sp,
        fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
        modifier = modifier
    )
}