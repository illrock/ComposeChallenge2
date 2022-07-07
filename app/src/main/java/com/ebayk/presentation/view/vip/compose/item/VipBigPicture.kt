package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ebayk.presentation.view.common.compose.item.ComposeError
import com.ebayk.presentation.view.common.compose.item.ComposeLoading
import com.ebayk.presentation.view.vip.VipFragment
import com.ebayk.util.toViewModelError
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
internal fun VipBigPicture(pictureUrl: String?, navController: NavController) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pictureUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        loading = { ComposeLoading() },
        error = { showError(pictureUrl, navController , it) },
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
private fun showError(pictureUrl: String?, navController: NavController, error: AsyncImagePainter.State.Error) {
    val vmError = error.result.throwable.toViewModelError()
    ComposeError(vmError) {
        // I'm not really fond with that solution, but this is workaround to retry failed image loading.
        // For some reason, there is still no retry handle in compose image loader.
        // Re-triggering request from rememberAsyncImagePainter() didn't work either.
        // So here is simple revisiting this screen via navController.
        val encodedPictureUrl = URLEncoder.encode(pictureUrl, StandardCharsets.UTF_8.toString())
        val destination = navController.currentDestination?.route
            ?.replace("{${VipFragment.COMPOSE_NAV_ARG_PICTURE_URL}}", encodedPictureUrl)
            .orEmpty()
        navController.popBackStack()
        navController.navigate(destination)
    }
}