package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ebayk.R
import com.ebayk.presentation.view.common.compose.constants.*
import com.ebayk.presentation.view.common.compose.item.ComposeError
import com.ebayk.presentation.view.common.compose.item.ComposeLoading
import com.ebayk.util.toViewModelError
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun VipPicturesPager(
    navController: NavController,
    lowResPictures: List<String>,
    highResPictures: List<String>,
    height: Int,
    destinationScreenValue: String,
    onShareClick: (url: String) -> Unit
) {
    if (lowResPictures.isEmpty()) return

    val state = rememberPagerState()
    Box(contentAlignment = Alignment.TopCenter) {
        HorizontalPager(
            state = state,
            count = lowResPictures.size,
            modifier = Modifier
                .height(height.dp)
                .fillMaxWidth()
        ) { pageIndex ->
            val previewPictureUrl = lowResPictures[pageIndex]
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(previewPictureUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = { ComposeLoading() },
                error = {
                    val vmError = it.result.throwable.toViewModelError()
                    ComposeError(vmError)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        val encodedBigPictureUrl = URLEncoder.encode(highResPictures[pageIndex], StandardCharsets.UTF_8.toString())
                        val clickDestination = "$destinationScreenValue/$encodedBigPictureUrl"
                        navController.navigate(clickDestination)
                    }
            )
        }
        VipPicturesPageIndicator(
            state,
            Modifier
                .align(Alignment.BottomEnd)
                .padding(end = PADDING_STANDARD.dp, bottom = PADDING_STANDARD.dp)
        )
        VipPicturesShareButton(
            state,
            highResPictures,
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            onShareClick(it)
        }
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun VipPicturesShareButton(
    state: PagerState,
    highResPictures: List<String>,
    modifier: Modifier = Modifier,
    onShareClick: (url: String) -> Unit
) {
    //The box to position button on the screen
    Box(
        modifier = modifier
            .padding(top = PADDING_DOUBLE.dp)
            .padding(end = PADDING_HALF.dp)
            .wrapContentSize()
    ) {
        //The box to expand click area
        Box(
            modifier = Modifier
                .clickable { onShareClick(highResPictures[state.currentPage]) }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null,
                modifier = Modifier
                    .padding(PADDING_HALF.dp)
                    .size(IMAGE_SIZE_BUTTON.dp)
            )
        }
    }
}