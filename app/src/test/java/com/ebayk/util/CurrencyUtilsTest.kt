package com.ebayk.util

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyUtilsTest {
    @Test
    fun format_zero() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 0L)
        assertEquals("0 €", formatted)
    }

    @Test
    fun format_zero_unknown() {
        val formatted = CurrencyUtils.formatCurrency("", 0L)
        assertEquals("0 ?", formatted)
    }

    @Test
    fun format_oneCent() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 1L)
        assertEquals("0,01 €", formatted)
    }

    @Test
    fun format_99Cent() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 99L)
        assertEquals("0,99 €", formatted)
    }

    @Test
    fun format_oneEuro() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 100L)
        assertEquals("1 €", formatted)
    }

    @Test
    fun format_oneEuro_andCent() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 101L)
        assertEquals("1,01 €", formatted)
    }

    @Test
    fun format_bigSum_eur() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 12345123L)
        assertEquals("123 451,23 €", formatted)
    }

    @Test
    fun format_bigSum_usd() {
        val formatted = CurrencyUtils.formatCurrency("USD", 12345123L)
        assertEquals("123 451,23 $", formatted)
    }

    @Test
    fun format_hugeSum() {
        val formatted = CurrencyUtils.formatCurrency("EUR", 1234567890123456789L)
        assertEquals("12 345 678 901 234 567,89 €", formatted)
    }

    @Test
    fun format_unknownCurrency() {
        val formatted = CurrencyUtils.formatCurrency("Kilogram", 12345123L)
        assertEquals("123 451,23 ?", formatted)
    }

    @Test
    fun format_noCurrency() {
        val formatted = CurrencyUtils.formatCurrency("", 12345123L)
        assertEquals("123 451,23 ?", formatted)
    }

    @Test
    fun format_negativeAmount() {
        val formatted = CurrencyUtils.formatCurrency("EUR", -12345123L)
        assertEquals("-123 451,23 €", formatted)
    }
}