package com.explwa.jexchangeanalytics.data.repositories

import com.explwa.jexchangeanalytics.data.network.api.ElrondApi
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.repositories.StakingRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StakingRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : StakingRepository {

    override fun getStakingRewards()
    : Single<List<DomainToken>> =
        elrondApi.getAllTokens(
            STAKING_ACCOUNT,
            elrondApi.getTokensCount(STAKING_ACCOUNT).blockingGet(),
            0
        )
            .flatMapIterable { it }
            .map { it.toDomain() }
            .toList()

    companion object {
        const val STAKING_ACCOUNT = "erd1qqqqqqqqqqqqqpgqwkqnf30j7hj4r797kahr0p5t5nsksc8a73eqd732jd"
    }

}