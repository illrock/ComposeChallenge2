package com.ebayk.util

import org.junit.Assert.*
import org.junit.Test

class DateUtilsTest {
    @Test
    fun format_success() {
        val formatted = DateUtils.simpleDateFormat("2021-10-08T08:01:00.000+0100")
        assertEquals("08.10.2021", formatted)
    }

    @Test
    fun format_success_order() {
        val formatted = DateUtils.simpleDateFormat("0001-02-13T00:00:00.000+0000")
        assertEquals("13.02.0001", formatted)
    }

    @Test
    fun format_error_empty() {
        val formatted = DateUtils.simpleDateFormat("")
        assertNull(formatted)
    }

    @Test
    fun format_error_notADate() {
        val formatted = DateUtils.simpleDateFormat("asdfasweqfas")
        assertNull(formatted)
    }

    @Test
    fun format_error_wrongFormat() {
        val formatted = DateUtils.simpleDateFormat("2021-10-08T08:01:00.000")
        assertNull(formatted)
    }
}