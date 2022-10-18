package com.explwa.jexchange.domain.repositories

import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import io.reactivex.rxjava3.core.Single


interface TransactionsRepository {

    fun getAddressWithUsername(username: String) : Single<UsernameResponse>

    fun getMyTokenTransfers(address: String, token: String, size: Int, from: Int) : Single<List<TransactionsResponse>>

    fun getMyTokenTransfersCount(address: String, token: String) : Single<Int>

    fun getAllTokensOnJexchange(size: Int) : Single<List<TokenResponse>>

    fun getTokensCountOnJexchange() : Single<Int>

    fun getTransactionWithHash(txHash: String) : Single<TransactionsResponse>

}
