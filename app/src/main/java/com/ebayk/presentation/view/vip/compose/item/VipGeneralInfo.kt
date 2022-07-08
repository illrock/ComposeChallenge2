package com.ebayk.presentation.view.vip.compose.item

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@Composable
internal fun VipGeneralInfo(
    navController: NavController,
    ad: VipAd,
    picturesPagerHeight: Int,
    bigPictureScreenValue: String,
    onAddressClick: (address: Address) -> Unit,
    onShareClick: (url: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = PADDING_STANDARD.dp)
    ) {
        VipPicturesPager(
            navController,
            ad.lowResPictures,
            ad.highResPictures,
            picturesPagerHeight,
            bigPictureScreenValue,
            onShareClick
        )
        VipTitle(
            ad.title,
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
                .padding(horizontal = PADDING_STANDARD.dp)
        )

        val price = ad.priceWithAmount
            ?: stringResource(id = R.string.vip_general_info_price_not_available)
        VipTitle(
            price,
            R.color.text_amount,
            modifier = Modifier
                .padding(top = PADDING_QUARTER.dp)
                .padding(horizontal = PADDING_STANDARD.dp)
        )

        VipAddress(ad.address) {
            onAddressClick(it)
        }
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
        ad.postedDate?.let { date ->
            Image(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                modifier = Modifier
                    .size(IMAGE_SIZE_SMALL.dp)
            )
            BodyText(
                text = date,
                colorRes = R.color.text_secondary,
                modifier = Modifier
                    .padding(start = PADDING_HALF.dp)
                    .padding(end = PADDING_STANDARD.dp)
            )
        }

        ad.visits.takeIf { it >= 0 }?.let { visits ->
            Image(
                painter = painterResource(id = R.drawable.ic_visits),
                contentDescription = null,
                modifier = Modifier
                    .size(IMAGE_SIZE_SMALL.dp)
            )
            BodyText(
                text = visits.toString(),
                colorRes = R.color.text_secondary,
                modifier = Modifier
                    .padding(start = PADDING_HALF.dp)
            )
        }

        val context = LocalContext.current
        val copiedMessage = stringResource(id = R.string.vip_id_clipboard_label)
        val toastMessage = stringResource(id = R.string.clipboard_copied_message)
        BodyText(
            text = stringResource(id = R.string.vip_ad_id_template, ad.id),
            colorRes = R.color.text_secondary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(copiedMessage, ad.id)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }
}