package com.explwa.jexchangeanalytics.data.repositories

import com.explwa.jexchangeanalytics.data.network.api.ElrondApi
import com.explwa.jexchangeanalytics.data.network.api.JexchangeService
import com.explwa.jexchangeanalytics.domain.models.DomainTokenPage
import com.explwa.jexchangeanalytics.domain.models.DomainTransaction
import com.explwa.jexchangeanalytics.domain.models.DomainTransactionPage
import com.explwa.jexchangeanalytics.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi,
    private val jexchangeService: JexchangeService
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

    override fun getMyTokenTransfersCount(
        address: String,
        token: String
    ): Single<Int> = elrondApi.getMyTokenTransfersCount(address, token)

    override fun getTransactionWithHash(txHash: String)
    : Single<DomainTransaction> =
        elrondApi.getTransactionWithHash(txHash)
            .map { it.toDomain() }

    override fun getTokenPrice(idToken: String)
    : Single<Double> =
        jexchangeService.getPriceForToken(idToken)
            .map {
                (if (it.rate == null) 0.0 else it.rate)!!
            }


    // Get all token used on Jexchange
    override fun getAllTokensOnJexchange(size: Int, from: Int)
    : Observable<DomainTokenPage> =
        elrondApi.getAllTokensOnJexchange(size, from)
            .concatMapIterable { it }
            .filter { it.identifier != "BUSD-40b57e" && it.identifier != "USDT-f8c08c"}
            .map { token ->
                if (token.price != null)
                    token.toDomain()
                else {
                    token.price = getTokenPrice(
                        token.identifier.toString()
                    ).blockingGet() // Bad I think but it work for now
                    token.toDomain()
                }
            }.toList()
            .toObservable()
            .map {
                if(getTokensCountOnJexchange().blockingGet() % 10 < it.size )
                    DomainTokenPage(
                            it,
                            true
                        )
                    else
                        DomainTokenPage(
                            it,
                            false
                        )
            }

    override fun getTokensCountOnJexchange()
    : Single<Int> = elrondApi.getTokensCountOnJexchange()
 }