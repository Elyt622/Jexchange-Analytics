package com.explwa.jexchange.app.module.mytxs.utils

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun fromBigIntegerToBigDecimal(bigIntegerStr: String?, decimals: Int?) : BigDecimal {

        val bigInteger = BigInteger(bigIntegerStr!!)
        return BigDecimal(bigInteger, decimals!!).setScale(2, RoundingMode.DOWN)
    }

    fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE)
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

}