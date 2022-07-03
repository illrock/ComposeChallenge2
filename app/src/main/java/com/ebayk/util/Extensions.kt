package com.ebayk.util

import com.ebayk.BuildConfig

fun Throwable.print() = if (BuildConfig.DEBUG) printStackTrace() else Unit