package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ebayk.R
import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.presentation.view.common.compose.constants.IMAGE_SIZE_SMALL
import com.ebayk.presentation.view.common.compose.constants.PADDING_HALF
import com.ebayk.presentation.view.common.compose.constants.PADDING_QUARTER
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.item.BodyText
import com.ebayk.presentation.view.vip.model.VipAd
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun VipGeneralInfo(
    navController: NavController,
    state: PagerState,
    ad: VipAd,
    pictureSliderHeight: Int,
    bigPictureScreenValue: String,
    onAddressClick: (address: Address) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = PADDING_STANDARD.dp)
    ) {
        if (ad.lowResPictures.isNotEmpty()) {
            VipPicturesSlider(
                navController,
                state,
                ad.lowResPictures,
                ad.highResPictures,
                bigPictureScreenValue,
                pictureSliderHeight
            )
        }
        VipTitle(
            ad.title,
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
                .padding(horizontal = PADDING_STANDARD.dp)
        )
        VipTitle(
            ad.priceWithAmount,
            R.color.text_amount,
            modifier = Modifier
                .padding(top = PADDING_QUARTER.dp)
                .padding(horizontal = PADDING_STANDARD.dp)
        )
        VipAddress(ad.address) { onAddressClick(it) }
        DateViewsId(
            ad,
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
        )
    }
}

@Composable
private fun DateViewsId(ad: VipAd, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = PADDING_STANDARD.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            modifier = Modifier
                .size(IMAGE_SIZE_SMALL.dp)
        )
        BodyText(
            text = ad.postedDate,
            colorRes = R.color.text_secondary,
            modifier = Modifier
                .padding(start = PADDING_HALF.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_visits),
            contentDescription = null,
            modifier = Modifier
                .padding(start = PADDING_STANDARD.dp)
                .size(IMAGE_SIZE_SMALL.dp)
        )
        BodyText(
            text = ad.visits.toString(),
            colorRes = R.color.text_secondary,
            modifier = Modifier
                .padding(start = PADDING_HALF.dp)
        )

        BodyText(
            text = stringResource(id = R.string.vip_ad_id_template, ad.id),
            colorRes = R.color.text_secondary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
        )
    }
}