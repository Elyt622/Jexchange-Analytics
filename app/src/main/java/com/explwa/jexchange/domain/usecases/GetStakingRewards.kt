package com.explwa.jexchange.domain.usecases

import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.domain.repositories.StakingRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetStakingRewards @Inject constructor(
    private val repository: StakingRepository
){
    fun getStakingRewards() : Single<List<TokenResponse>> {
        return repository.getStakingRewards()
            .toObservable()
            .flatMapIterable { it }
            .filter {
                it.identifier != "JEX-9040ca"
            }.toList()
    }
}