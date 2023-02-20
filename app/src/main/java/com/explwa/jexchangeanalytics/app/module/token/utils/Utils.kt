package com.explwa.jexchangeanalytics.app.module.token.utils

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

    fun formatAddress(address: String) : String{
        val stringBuilder = StringBuilder()
        stringBuilder.append(address.subSequence(0..6))
        stringBuilder.append("...")
        stringBuilder.append(address.subSequence(address.length-6 until address.length))
        return stringBuilder.toString()
    }

    fun formatBooleanProperties(
        property: String
    ) : String = if (property == "true") "Yes" else "No"

    fun getSortNameToken(
        identifierToken: String
    ) : String =
        identifierToken
            .substringBefore("-")

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