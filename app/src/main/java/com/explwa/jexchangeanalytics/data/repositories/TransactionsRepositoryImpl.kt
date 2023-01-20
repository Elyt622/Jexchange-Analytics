package com.explwa.jexchangeanalytics.data.repositories

import com.explwa.jexchangeanalytics.data.network.api.ElrondApi
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.models.DomainTransaction
import com.explwa.jexchangeanalytics.domain.models.DomainTransactionPage
import com.explwa.jexchangeanalytics.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : TransactionsRepository {

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