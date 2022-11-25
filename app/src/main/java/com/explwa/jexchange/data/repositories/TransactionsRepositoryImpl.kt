package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import com.explwa.jexchange.domain.models.DomainTransaction
import com.explwa.jexchange.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : TransactionsRepository {

    // Get address with username
    override fun getAddressWithUsername(username: String)
    : Single<UsernameResponse> = elrondApi.getAddressWithUsername(username)

    // Get transfers with a token
    override fun getMyTokenTransfers(
        address: String,
        token: String,
        size: Int,
        from: Int
    ): Single<List<DomainTransaction>> = elrondApi.getMyTokenTransfers(address, token, size, from)

    override fun getMyTokenTransfersCount(address: String, token: String)
    : Single<Int> = elrondApi.getMyTokenTransfersCount(address, token)

    override fun getTransactionWithHash(txHash: String)
    : Single<DomainTransaction> = elrondApi.getTransactionWithHash(txHash)

    // Get all token used on Jexchange
    override fun getAllTokensOnJexchange(size: Int)
    : Single<List<TokenResponse>> =
        elrondApi.getAllTokensOnJexchange(size)

    override fun getTokensCountOnJexchange()
    : Single<Int> = elrondApi.getTokensCountOnJexchange()
 }