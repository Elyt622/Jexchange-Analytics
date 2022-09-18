package com.explwa.jexchange.app.domain.repositories

import com.explwa.jexchange.data.response.transactions.TransactionsResponse
import com.explwa.jexchange.data.response.username.UsernameResponse
import io.reactivex.rxjava3.core.Single


interface TransactionsRepository {

    fun getAddressWithUsername(username: String) : Single<UsernameResponse>

    fun getTransactions(senderAddress: String) : Single<List<TransactionsResponse>>

    fun getJexTransactions() : Single<List<TransactionsResponse>>

    fun getMyJexTransfers() : Single<List<TransactionsResponse>>

}