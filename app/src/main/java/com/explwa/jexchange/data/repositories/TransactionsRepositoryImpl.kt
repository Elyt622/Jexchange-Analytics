package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
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

    override fun getMyTokenTransfers(address: String, size: Int): Single<List<TransactionsResponse>> {
        return elrondApi.getMyTokenTransfers(address, size)
    }

    override fun getMyTokenTransfersCount(address: String): Single<Int> {
        return elrondApi.getMyTokenTransfersCount(address)
    }

    override fun getAllTokensOnJexchange(size: Int): Single<List<TokenResponse>> {
        return elrondApi.getAllTokensOnJexchange(size)
    }

    override fun getTokensCountOnJexchange(): Single<Int> {
        return elrondApi.getTokensCountOnJexchange()
    }

}