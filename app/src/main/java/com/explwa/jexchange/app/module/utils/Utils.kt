package com.explwa.jexchange.app.module.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    fun toStringFormat(string: String, decimals: Int?) : String {
        return BigDecimal(string.toBigInteger(), decimals!!).setScale(2, RoundingMode.DOWN).toString()
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