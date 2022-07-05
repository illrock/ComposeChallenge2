package com.ebayk.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ebayk.BuildConfig

fun Context.openBrowser(url: String) {
    if (url.isNotBlank()) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
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