package com.ebayk.presentation.view.vip

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ebayk.data.network.response.ad.model.Address
import com.ebayk.presentation.view.common.compose.constants.PADDING_BOTTOM_EXTRA
import com.ebayk.presentation.view.common.compose.item.ComposeError
import com.ebayk.presentation.view.common.compose.item.ComposeLoading
import com.ebayk.presentation.view.util.ViewModelResult
import com.ebayk.presentation.view.vip.compose.item.*
import com.ebayk.presentation.view.vip.model.VipAd
import com.ebayk.util.openBrowser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VipFragment : Fragment() {
    private val vm: VipViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Screen(vm)
            }
            if (savedInstanceState == null) loadData()
        }
    }

    @Composable
    private fun Screen(vm: VipViewModel) {
        MaterialTheme {
            val result: ViewModelResult<VipAd> by vm.result.observeAsState(ViewModelResult.Loading)
            when (result) {
                is ViewModelResult.Loading -> ComposeLoading()
                is ViewModelResult.Success -> SuccessHost((result as ViewModelResult.Success).data)
                is ViewModelResult.Error -> ComposeError(result as ViewModelResult.Error) {
                    loadData()
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
                VipBigPicture(pictureUrl, navController)
            }
        }
    }

    @Composable
    private fun Success(navController: NavController, ad: VipAd) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            VipGeneralInfo(
                navController,
                ad,
                PICTURES_SLIDER_HEIGHT,
                SuccessScreen.VIP_BIG_PICTURE.value,
                onAddressClick = { openGoogleMaps(it) },
                onShareClick = { shareBigImage(it) }
            )
            VipDetails(ad.attributes)
            VipFeatures(ad.features, FEATURES_COLUMNS_COUNT)
            VipDocuments(ad.documents) {
                requireContext().openBrowser(it)
            }
            VipDescription(ad.description)
            Spacer(
                modifier = Modifier
                    .height(PADDING_BOTTOM_EXTRA.dp)
            )
        }
    }

    private fun loadData() {
        vm.loadAd(AD_ID)
    }

    private fun getNavVipBigPictureUrl() = SuccessScreen.VIP_BIG_PICTURE.value + "/{" + COMPOSE_NAV_ARG_PICTURE_URL + "}"

    private fun openGoogleMaps(address: Address) {
        val stringAddress = listOf(
            address.street,
            address.city,
            address.zipCode
        ).filter { it.isNotBlank() }
            .joinToString(separator = ", ")
        val encodedAddress = Uri.encode(stringAddress)

        val geoUri = Uri.parse("geo:${address.latitude},${address.longitude}?q=$encodedAddress")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    private fun shareBigImage(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private enum class SuccessScreen(val value: String) {
        VIP("vip"),
        VIP_BIG_PICTURE("vipBigPicture")
    }

    companion object {
        const val COMPOSE_NAV_ARG_PICTURE_URL = "pictureUrl"

        private const val AD_ID = 1118635128L

        private const val PICTURES_SLIDER_HEIGHT = 300
        private const val FEATURES_COLUMNS_COUNT = 2
    }
}