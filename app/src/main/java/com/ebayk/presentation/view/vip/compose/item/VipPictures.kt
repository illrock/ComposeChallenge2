package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.PADDING_HALF
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.constants.SHAPE_ROUND_RADIUS
import com.ebayk.presentation.view.common.compose.constants.TEXT_SIZE_INDICATOR
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun VipPicturesSlider(
    navController: NavController,
    lowResPictures: List<String>,
    highResPictures: List<String>,
    destinationScreenValue: String,
    height: Int
) {
    val state = rememberPagerState()
    val previewPictureUrl = remember { mutableStateOf("") }
    Box(contentAlignment = Alignment.TopCenter) {
        HorizontalPager(
            state = state,
            count = lowResPictures.size,
            modifier = Modifier
                .height(height.dp)
                .fillMaxWidth()
        ) { pageIndex ->
            previewPictureUrl.value = lowResPictures[pageIndex]
            val encodedBigPictureUrl = URLEncoder.encode(highResPictures[pageIndex], StandardCharsets.UTF_8.toString())
            val clickDestination = "$destinationScreenValue/$encodedBigPictureUrl"
            AsyncImage(
                model = previewPictureUrl.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate(clickDestination) }
            )
        }
        VipPicturesPageIndicator(
            state,
            Modifier
                .align(Alignment.BottomEnd)
                .padding(end = PADDING_STANDARD.dp, bottom = PADDING_STANDARD.dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun VipPicturesPageIndicator(state: PagerState, modifier: Modifier) {
    Surface(
        color = colorResource(id = R.color.indicator_background),
        modifier = modifier
            .clip(RoundedCornerShape(SHAPE_ROUND_RADIUS.dp))
    ) {
        Text(
            text = stringResource(
                id = R.string.vip_pictures_page_indicator_template,
                state.currentPage + 1,
                state.pageCount
            ),
            fontFamily = FontFamily(Font(R.font.roboto_medium, FontWeight.Medium)),
            fontSize = TEXT_SIZE_INDICATOR.sp,
            color = colorResource(id = R.color.text_indicator),
            modifier = Modifier.padding(PADDING_HALF.dp)
        )
    }
}