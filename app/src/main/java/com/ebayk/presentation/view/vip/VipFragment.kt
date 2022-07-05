package com.ebayk.presentation.view.vip

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ebayk.R
import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.presentation.view.common.compose.constants.PADDING_BOTTOM_EXTRA
import com.ebayk.presentation.view.common.compose.item.SectionDivider
import com.ebayk.presentation.view.util.ViewModelResult
import com.ebayk.presentation.view.vip.compose.item.VipDescription
import com.ebayk.presentation.view.vip.compose.item.VipDetails
import com.ebayk.presentation.view.vip.compose.item.VipDocuments
import com.ebayk.presentation.view.vip.compose.item.VipFeatures
import com.ebayk.presentation.view.vip.compose.item.VipGeneralInfo
import com.ebayk.presentation.view.vip.model.VipAd
import com.ebayk.util.openBrowser
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalPagerApi::class)
class VipFragment : Fragment() {
    private val vm: VipViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Screen(vm)
            }
        }
    }

    @Composable
    private fun Screen(vm: VipViewModel) {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.vip_background)
            ) {
                val result: ViewModelResult<VipAd>? by vm.result.observeAsState()
                when (result) {
                    is ViewModelResult.Loading -> Loading()
                    is ViewModelResult.Success -> SuccessHost((result as ViewModelResult.Success).data)
                    is ViewModelResult.Error -> Error()
                    else -> throw IllegalStateException("Unexpected ViewModelResult")
                }
            }
        }
    }

    @Composable
    private fun SuccessHost(ad: VipAd) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = SuccessScreen.VIP.value) {
            composable(SuccessScreen.VIP.value) { Success(navController, ad) }
            composable(getNavVipBigPictureUrl()) { backStackEntry ->
                val pictureUrl = backStackEntry.arguments?.getString(COMPOSE_NAV_ARG_PICTURE_URL)
                BigPicture(pictureUrl)
            }
        }
    }

    @Composable
    private fun Success(navController: NavController, ad: VipAd) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            val state = rememberPagerState()
            VipGeneralInfo(navController, state, ad, PICTURES_SLIDER_HEIGHT, SuccessScreen.VIP_BIG_PICTURE.value) {
                openGoogleMaps(it)
            }

            if (ad.attributes.isNotEmpty()) {
                SectionDivider()
                VipDetails(ad.attributes)
            }

            if (ad.features.isNotEmpty()) {
                SectionDivider()
                VipFeatures(ad.features, FEATURES_COLUMNS_COUNT)
            }

            if (ad.documents.isNotEmpty()) {
                SectionDivider()
                VipDocuments(ad.documents) {
                    requireContext().openBrowser(it)
                }
            }

            if (ad.description.isNotBlank()) {
                SectionDivider()
                VipDescription(ad.description)
            }

            Spacer(
                modifier = Modifier
                    .height(PADDING_BOTTOM_EXTRA.dp)
            )
        }
    }

    @Composable
    private fun BigPicture(pictureUrl: String?) {
        AsyncImage(
            model = pictureUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun Loading() {
        //todo show progress
    }

    @Composable
    private fun Error() {
        //todo show error (custom or from back-end)
    }

    private fun getNavVipBigPictureUrl() = SuccessScreen.VIP_BIG_PICTURE.value + "/{" + COMPOSE_NAV_ARG_PICTURE_URL + "}"

    private fun openGoogleMaps(address: Address) {
        val encodedAddress = Uri.encode("${address.street}, ${address.city}, ${address.zipCode}")
        val geoUri = Uri.parse("geo:${address.latitude},${address.longitude}?q=$encodedAddress")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    private enum class SuccessScreen(val value: String) {
        VIP("vip"),
        VIP_BIG_PICTURE("vipBigPicture")
    }

    companion object {
        private const val COMPOSE_NAV_ARG_PICTURE_URL = "pictureUrl"

        private const val PICTURES_SLIDER_HEIGHT = 300
        private const val FEATURES_COLUMNS_COUNT = 2
    }
}