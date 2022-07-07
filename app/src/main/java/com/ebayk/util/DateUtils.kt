package com.ebayk.util

import java.text.SimpleDateFormat

object DateUtils {
    private const val ISO_DATE_TIMEZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"
    private const val DMY_DATE_FORMAT = "dd.MM.yyyy"

    fun simpleDateFormat(date: String): String? {
        val time = parseIsoDate(date)
        val formatter = SimpleDateFormat(DMY_DATE_FORMAT)
        return time?.let { formatter.format(time) }
    }

    private fun parseIsoDate(date: String): Long? = try {
        SimpleDateFormat(ISO_DATE_TIMEZONE_FORMAT).parse(date)?.time
    } catch (e: Exception) {
        e.print()
        null
    }
}