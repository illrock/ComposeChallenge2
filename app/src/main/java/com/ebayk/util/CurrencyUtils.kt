package com.ebayk.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

object CurrencyUtils {
    fun formatCurrency(currency: String, amount: Long): String {
        val bigDecimal = BigDecimal(amount)
            .divide(BigDecimal(100))
            .setScale(2, RoundingMode.HALF_UP)
        val formattedAmount = NumberFormat.getNumberInstance().format(bigDecimal)
        val currencySymbol = Currency.getInstance(currency).symbol
        return "$formattedAmount $currencySymbol"
    }
}