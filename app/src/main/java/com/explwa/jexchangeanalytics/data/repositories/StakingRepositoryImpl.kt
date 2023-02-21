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
        elrondApi.getStakingRewards()
            .toObservable()
            .flatMapIterable { it }
            .map { it.toDomain() }
            .toList()

}