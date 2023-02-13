package com.explwa.jexchangeanalytics.data.repositories

import com.explwa.jexchangeanalytics.data.network.api.ElrondApi
import com.explwa.jexchangeanalytics.data.network.api.JexchangeService
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.repositories.TokenRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi,
    private val jexchangeService: JexchangeService
) : TokenRepository {

    override fun getTokenDetails(
        idToken: String
    ) : Single<DomainToken> =
        elrondApi.getTokenDetails(idToken)
            .map { token ->
                if (token.price != null)
                    token.toDomain()
                else {
                    token.price = getTokenPrice(
                        token.identifier.toString()
                    ).blockingGet() // Bad I think but it work for now
                    token.toDomain()
                }
            }

    private fun getTokenPrice(idToken: String)
            : Single<Double> =
        jexchangeService.getPriceForToken(idToken)
            .map { it.rate!! }
}