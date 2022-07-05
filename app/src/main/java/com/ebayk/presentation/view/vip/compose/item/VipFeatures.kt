package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.IMAGE_SIZE_SMALL
import com.ebayk.presentation.view.common.compose.constants.PADDING_HALF
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.item.BodyText
import com.ebayk.presentation.view.common.compose.item.ItemDivider
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
internal fun VipFeatures(features: List<String>, columnsCount: Int) {
    val padding = PADDING_STANDARD
    Column(
        modifier = Modifier
            .padding(padding.dp)
    ) {
        VipTitle(title = stringResource(id = R.string.vip_features_section_header))
        ItemDivider(
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
        )
        VipFeaturesGrid(features, columnsCount, padding)
    }
}

/**
 * LazyVerticalGrid doesn't work inside of scrollable column(like, seriously?..), so here is workaround with FlowRow.
 * Essentially, it works like an auto new line by reaching an end.
 * The bad part is we must exclude padding manually.
 */
@Composable
private fun VipFeaturesGrid(features: List<String>, columnsCount: Int, horizontalPaddingValue: Int) {
    val horizontalPadding = horizontalPaddingValue.dp
    val featureWidth = (LocalConfiguration.current.screenWidthDp.dp - (horizontalPadding * 2)) / columnsCount
    FlowRow(
        mainAxisSize = SizeMode.Expand,
        mainAxisAlignment = FlowMainAxisAlignment.Start
    ) {
        features.forEach { feature ->
            VipFeatureRow(feature = feature, width = featureWidth)
        }
    }
}

@Composable
private fun VipFeatureRow(feature: String, width: Dp) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(width)
            .padding(top = PADDING_HALF.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null,
            modifier = Modifier
                .size(IMAGE_SIZE_SMALL.dp)
        )
        BodyText(
            text = feature,
            modifier = Modifier
                .weight(1f)
                .padding(start = PADDING_HALF.dp)
        )
    }
}