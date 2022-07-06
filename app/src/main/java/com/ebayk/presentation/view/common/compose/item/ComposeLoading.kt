package com.ebayk.presentation.view.common.compose.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ebayk.presentation.view.common.compose.constants.PROGRESS_SIZE

@Composable
internal fun ComposeLoading() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(PROGRESS_SIZE.dp)
        )
    }
}