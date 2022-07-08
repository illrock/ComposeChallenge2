package com.ebayk.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

object CurrencyUtils {
    private const val CURRENCY_SYMBOL_UNKNOWN = '?'

    fun formatCurrency(currency: String, amount: Long): String {
        val bigDecimal = BigDecimal(amount)
            .divide(BigDecimal(100))
            .setScale(2, RoundingMode.HALF_UP)
        val formattedAmount = NumberFormat.getNumberInstance().format(bigDecimal)

        val currencySymbol = try {
            Currency.getInstance(currency).symbol
        } catch (e: IllegalArgumentException) {
            // I thought about maybe returning Currency.getInstance(Locale.getDefault())
            // But it could lead to unreal prices.
            // Maybe in case of incorrect currency we should hide the whole price.

            // Send analytics here too.
            e.print()
            CURRENCY_SYMBOL_UNKNOWN
        }

        return "$formattedAmount $currencySymbol"
    }
}