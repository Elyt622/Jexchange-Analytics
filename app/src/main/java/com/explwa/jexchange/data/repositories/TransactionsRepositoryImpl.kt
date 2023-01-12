package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.domain.models.DomainToken
import com.explwa.jexchange.domain.models.DomainTransaction
import com.explwa.jexchange.domain.models.DomainTransactionPage
import com.explwa.jexchange.domain.models.DomainUser
import com.explwa.jexchange.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : TransactionsRepository {

    // Get address with username
    override fun getAddressWithUsername(username: String)
    : Single<DomainUser> =
        elrondApi.getAddressWithUsername(username)
            .map { it.toDomain() }

    // Get transfers with a token
    override fun getTxPage(
        address: String,
        token: String,
        size: Int,
        from: Int
    ): Single<DomainTransactionPage> =
        elrondApi.getMyTokenTransfers(
            address,
            token,
            size,
            from
        )
            .toObservable()
            .flatMapIterable { it }
            .map {
                it.toDomain()
            }.toList()
            .map {
                DomainTransactionPage(
                    it,
                    true
                )
            }

    override fun getMyTokenTransfersCount(address: String, token: String)
    : Single<Int> = elrondApi.getMyTokenTransfersCount(address, token)

    override fun getTransactionWithHash(txHash: String)
    : Single<DomainTransaction> =
        elrondApi.getTransactionWithHash(txHash)
            .map { it.toDomain() }

    // Get all token used on Jexchange
    override fun getAllTokensOnJexchange(size: Int)
    : Single<List<DomainToken>> =
        elrondApi.getAllTokensOnJexchange(size)
            .toObservable()
            .flatMapIterable { it }
            .map { it.toDomain() }
            .toList()

    override fun getTokensCountOnJexchange()
    : Single<Int> = elrondApi.getTokensCountOnJexchange()
 }