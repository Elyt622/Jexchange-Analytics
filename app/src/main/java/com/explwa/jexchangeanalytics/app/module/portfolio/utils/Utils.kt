package com.explwa.jexchangeanalytics.app.module.portfolio.utils

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

object Utils {

    fun fromBigIntegerToBigDecimal(
        bigIntegerStr: String?,
        decimals: Int?
    ): BigDecimal {
        val bigInteger = BigInteger(bigIntegerStr!!)
        return BigDecimal(
            bigInteger,
            decimals!!
        ).setScale(
            2,
            RoundingMode.UP
        )
    }

    fun BigDecimal.formatDecimalSeparator()
            : String =
        toString()
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

}