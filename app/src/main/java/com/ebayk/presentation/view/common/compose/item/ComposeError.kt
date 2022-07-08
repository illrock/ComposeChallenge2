package com.ebayk.presentation.view.common.compose.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.IMAGE_SIZE_BIG
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.util.ViewModelResult
import com.ebayk.presentation.view.vip.compose.item.VipTitle

@Composable
internal fun ComposeError(error: ViewModelResult.Error, onClick: (() -> Unit)? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            }
            .padding(PADDING_STANDARD.dp)
    ) {
        Image(
            painter = painterResource(id = error.iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.colorAccent)),
            modifier = Modifier
                .size(IMAGE_SIZE_BIG.dp)
        )

        val message = error.errorMessage
            ?: error.errorRes?.let { stringResource(id = it) }
                .orEmpty()
        VipTitle(
            title = message,
            textAlign = TextAlign.Center
        )
        onClick?.let {
            Text(
                text = stringResource(id = R.string.error_tap_to_retry),
                textAlign = TextAlign.Center
            )
        }
    }
}