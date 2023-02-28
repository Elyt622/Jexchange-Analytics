package com.explwa.jexchangeanalytics.domain.usecases

import android.util.Log
import com.explwa.jexchangeanalytics.app.module.portfolio.utils.Utils
import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

class GetValueFromAccount @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    val utils = Utils
    var sum = BigDecimal("0")

    fun getTotalValue(address: String)
    : Single<BigDecimal> =
        accountRepository.getAllTokens(address)
            .map { tokens ->
                for (token in tokens) {
                    if (token.valueUsd != null) {
                        sum += token.valueUsd!!.toBigDecimal()
                        Log.d("DEBUG", token.identifier + " " + token.valueUsd!!.toString())
                    }
                    else if (token.price != null) {
                        sum += token.balance?.toBigDecimal()!! * utils.fromBigIntegerToBigDecimal(
                            token.price.toString(),
                            token.decimals
                        )
                        Log.d("DEBUG", "2")
                    }
                    else {
                        sum += utils.fromBigIntegerToBigDecimal(token.balance.toString(), token.decimals!!) * accountRepository.getTokenPrice(
                            token.identifier.toString()
                        ).blockingGet().toBigDecimal()
                    }
                }
                sum
            }.single(sum)
            .doOnError { Log.d("DEBUG", it.message.toString()) }

}