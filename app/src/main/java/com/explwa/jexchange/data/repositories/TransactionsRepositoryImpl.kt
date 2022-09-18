package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.app.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.data.response.transactions.TransactionsResponse
import com.explwa.jexchange.data.response.username.UsernameResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : TransactionsRepository {

    override fun getAddressWithUsername(username: String): Single<UsernameResponse> {
        return elrondApi.getAddressWithUsername(username)
    }

    override fun getTransactions(senderAddress: String): Single<List<TransactionsResponse>> {
        return elrondApi.getTransactions(senderAddress)
    }

    override fun getJexTransactions(): Single<List<TransactionsResponse>> {
        return elrondApi.getJexTransactions()
    }

    override fun getMyJexTransfers(): Single<List<TransactionsResponse>> {
        return elrondApi.getMyJexTransfers()
    }

}