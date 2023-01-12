package com.explwa.jexchange.domain.repositories

import com.explwa.jexchange.domain.models.DomainToken
import com.explwa.jexchange.domain.models.DomainTransaction
import com.explwa.jexchange.domain.models.DomainTransactionPage
import com.explwa.jexchange.domain.models.DomainUser
import io.reactivex.rxjava3.core.Single


interface TransactionsRepository {

    fun getAddressWithUsername(username: String) : Single<DomainUser>

    fun getTxPage(address: String, token: String, size: Int, from: Int) : Single<DomainTransactionPage>

    fun getMyTokenTransfersCount(address: String, token: String) : Single<Int>

    fun getAllTokensOnJexchange(size: Int) : Single<List<DomainToken>>

    fun getTokensCountOnJexchange() : Single<Int>

    fun getTransactionWithHash(txHash: String) : Single<DomainTransaction>

}
