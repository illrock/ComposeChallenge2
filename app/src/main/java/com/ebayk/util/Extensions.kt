package com.ebayk.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ebayk.BuildConfig
import com.ebayk.R
import com.ebayk.presentation.view.util.ViewModelResult
import retrofit2.HttpException
import java.net.UnknownHostException

fun Context.openBrowser(url: String) {
    if (url.isNotBlank()) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } catch (e: Exception) {
            logDebug("Couldn't open browser!")
            e.print()
        }
    }
}

fun Throwable.print() = if (BuildConfig.DEBUG) printStackTrace() else Unit

fun logDebug(message: Any?) {
    if (!BuildConfig.DEBUG) return
    Throwable().stackTrace[1].run {
        val tag = "debugTag"
        val fullMessage = "($fileName:$lineNumber): $message"
        Log.d(tag, fullMessage)
    }
}

fun Throwable.toViewModelError() = when {
    this is UnknownHostException -> ViewModelResult.Error(
        errorRes = R.string.error_connection,
        iconRes = R.drawable.ic_wifi_off
    )
    this is HttpException -> ViewModelResult.Error(
        errorRes = R.string.error_http,
        iconRes = R.drawable.ic_tools
    )
    this.message != null -> ViewModelResult.Error(
        errorMessage = message,
        iconRes = R.drawable.ic_mood_bad
    )
    else -> ViewModelResult.Error(
        errorRes = R.string.error_unknown,
        iconRes = R.drawable.ic_mood_sick
    )
}