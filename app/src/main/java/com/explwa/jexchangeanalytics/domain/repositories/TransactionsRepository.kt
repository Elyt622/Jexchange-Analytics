package com.explwa.jexchangeanalytics.domain.repositories

import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.models.DomainTransaction
import com.explwa.jexchangeanalytics.domain.models.DomainTransactionPage
import io.reactivex.rxjava3.core.Single


interface TransactionsRepository {

    fun getTxPage(address: String, token: String, size: Int, from: Int) : Single<DomainTransactionPage>

    fun getMyTokenTransfersCount(address: String, token: String) : Single<Int>

    fun getAllTokensOnJexchange(size: Int) : Single<List<DomainToken>>

    fun getTokensCountOnJexchange() : Single<Int>

    fun getTransactionWithHash(txHash: String) : Single<DomainTransaction>

}