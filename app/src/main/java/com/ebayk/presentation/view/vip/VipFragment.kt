package com.ebayk.presentation.view.vip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ebayk.data.network.response.ad.AdResponse
import com.ebayk.presentation.view.compose.*
import com.ebayk.presentation.view.compose.item.Title
import com.ebayk.presentation.view.util.ViewModelResult
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
@OptIn(ExperimentalPagerApi::class)
class VipFragment : Fragment() {
    private val vm: VipViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                VipScreen(vm)
            }
        }
    }

    @Composable
    private fun VipScreen(vm: VipViewModel) {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val result: ViewModelResult<AdResponse>? by vm.result.observeAsState()
                when (result) {
                    is ViewModelResult.Loading -> VipLoading()
                    is ViewModelResult.Success -> VipSuccess((result as ViewModelResult.Success).data)
                    is ViewModelResult.Error -> VipError()
                    else -> throw IllegalStateException("Unexpected ViewModelResult")
                }
            }
        }
    }

    @Composable
    private fun VipSuccess(response: AdResponse) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = SuccessScreen.VIP.value) {
            composable(SuccessScreen.VIP.value) { VipSuccessContent(navController, response) }
            composable(getNavVipBigPictureUrl()) { backStackEntry ->
                val pictureUrl = backStackEntry.arguments?.getString(COMPOSE_NAV_ARG_PICTURE_URL)
                VipBigPicture(pictureUrl)
            }
        }
    }

    @Composable
    private fun VipSuccessContent(navController: NavController, response: AdResponse) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            val state = rememberPagerState()
            PicturesSlider(navController, state, response.pictures)
            Title(response.title)
        }
    }

    @Composable
    private fun VipBigPicture(pictureTemplateUrl: String?) {
        val bigPictureUrl = pictureTemplateUrl?.toHighResUrl()
        AsyncImage(
            model = bigPictureUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun VipLoading() {
        //todo show progress
    }

    @Composable
    private fun VipError() {
        //todo show error (custom or from back-end)
    }

    @Composable
    private fun PicturesSlider(navController: NavController, state: PagerState, pictureTemplates: List<String>) {
        val previewPictureUrl = remember { mutableStateOf("") }
        val previewPictures = pictureTemplates.map { it.toLowResUrl() }
        Box(contentAlignment = Alignment.TopCenter) {
            HorizontalPager(
                state = state,
                count = previewPictures.size,
                modifier = Modifier
                    .height(PREVIEW_PICTURE_HEIGHT.dp)
                    .fillMaxWidth()
            ) { pageIndex ->
                previewPictureUrl.value = previewPictures[pageIndex]
                val encodedPictureTemplateUrl = URLEncoder.encode(pictureTemplates[pageIndex], StandardCharsets.UTF_8.toString())
                val clickDestination = "${SuccessScreen.VIP_BIG_PICTURE.value}/$encodedPictureTemplateUrl"
                AsyncImage(
                    model = previewPictureUrl.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate(clickDestination) }
                )
            }
            PicturesPageIndicator(
                state,
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = PADDING_STANDARD.dp, bottom = PADDING_STANDARD.dp)
            )
        }
    }

    @Composable
    private fun PicturesPageIndicator(state: PagerState, modifier: Modifier) {
        Surface(
            color = colorResource(id = com.ebayk.R.color.indicator_background),
            modifier = modifier
                .clip(RoundedCornerShape(SHAPE_ROUND_RADIUS.dp))
        ) {
            Text(
                text = getPicturesPageIndicatorText(state.currentPage, state.pageCount),
                fontFamily = FontFamily(Font(com.ebayk.R.font.roboto_medium, FontWeight.Medium)),
                fontSize = TEXT_SIZE_INDICATOR.sp,
                color = colorResource(id = com.ebayk.R.color.text_indicator),
                modifier = Modifier.padding(PADDING_HALF.dp)
            )
        }
    }

    private fun getPicturesPageIndicatorText(currentPage: Int, pageCount: Int) = getString(
        com.ebayk.R.string.vip_pictures_page_indicator_template,
        currentPage + 1,
        pageCount
    )

    private fun String.toLowResUrl() = replace(IMAGE_ID_PLACEHOLDER, IMAGE_ID_VALUE_LOW_RES)
    private fun String.toHighResUrl() = replace(IMAGE_ID_PLACEHOLDER, IMAGE_ID_VALUE_HIGH_RES)

    private fun getNavVipBigPictureUrl() = SuccessScreen.VIP_BIG_PICTURE.value + "/{" + COMPOSE_NAV_ARG_PICTURE_URL + "}"

    private enum class SuccessScreen(val value: String) {
        VIP("vip"),
        VIP_BIG_PICTURE("vipBigPicture")
    }

    companion object {
        const val COMPOSE_NAV_ARG_PICTURE_URL = "pictureUrl"
        const val IMAGE_ID_PLACEHOLDER = "{imageId}"
        const val IMAGE_ID_VALUE_LOW_RES = "1"
        const val IMAGE_ID_VALUE_HIGH_RES = "57"

        const val PREVIEW_PICTURE_HEIGHT = 300
    }
}