package com.explwa.jexchange.domain.repositories

import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import io.reactivex.rxjava3.core.Single


interface TransactionsRepository {

    fun getAddressWithUsername(username: String) : Single<UsernameResponse>

    fun getTransactions(senderAddress: String) : Single<List<TransactionsResponse>>

    fun getJexTransactions() : Single<List<TransactionsResponse>>

    fun getMyTokenTransfers(address: String, size: Int) : Single<List<TransactionsResponse>>

    fun getMyTokenTransfersCount(address: String) : Single<Int>

    fun getAllTokensOnJexchange(size: Int) : Single<List<TokenResponse>>

    fun getTokensCountOnJexchange() : Single<Int>
}
