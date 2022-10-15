package com.explwa.jexchange.app.module.staking.utils

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode


object Utils {

    fun fromBigIntegerToBigDecimal(bigIntegerStr: String?, decimals: Int?) : BigDecimal {
        val bigInteger = BigInteger(bigIntegerStr!!)
        return BigDecimal(bigInteger, decimals!!).setScale(2, RoundingMode.DOWN)
    }

}