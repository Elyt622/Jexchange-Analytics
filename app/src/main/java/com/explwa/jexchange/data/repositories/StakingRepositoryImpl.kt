package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.domain.models.DomainToken
import com.explwa.jexchange.domain.models.DomainUser
import com.explwa.jexchange.domain.repositories.StakingRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StakingRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : StakingRepository {

    override fun getStakingRewards(): Single<List<DomainToken>> {
        return elrondApi.getStakingRewards()
            .toObservable()
            .flatMapIterable { it }
            .map { it.toDomain() }
            .toList()
    }

    override fun getAccountWithUsername(username: String): Single<DomainUser> {
        return elrondApi.getAccountWithUsername(username)
            .map { it.toDomain() }
    }

}