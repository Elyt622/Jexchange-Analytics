package com.explwa.jexchange.app.module.marketplace.utils

import java.lang.StringBuilder
import java.math.RoundingMode

object Utils {

    fun toFormatString(bigNumberStr: String?) : String {
        return if (bigNumberStr != "null") {
            autoScaleBigDecimal(bigNumberStr)
        } else {
            ""
        }
    }

    private fun autoScaleBigDecimal(bigNumberStr: String?) : String {
        val stringBuilder = StringBuilder().append("0.00")
        var scale = 2

        return if (toBigDecimalString(bigNumberStr, 2, false) != stringBuilder.toString()) {
            toBigDecimalString(bigNumberStr, 2, true)
        } else {
            while(toBigDecimalString(bigNumberStr, scale, false) == stringBuilder.toString()) {
                stringBuilder.append("0")
                ++scale
            }
            toBigDecimalString(bigNumberStr, ++scale, false)
        }
    }


    private fun toBigDecimalString(string: String?, scale: Int, stripTrailingZeros: Boolean) : String {
        return if (stripTrailingZeros)
            string?.toBigDecimal()?.setScale(scale, RoundingMode.DOWN)?.stripTrailingZeros().toString()
        else
            string?.toBigDecimal()?.setScale(scale, RoundingMode.DOWN).toString()
    }

}