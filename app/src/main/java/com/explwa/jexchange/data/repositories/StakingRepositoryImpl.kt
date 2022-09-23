package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.domain.repositories.StakingRepository
import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.data.response.elrond.TokenResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StakingRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : StakingRepository {

    override fun getStakingRewards(): Single<List<TokenResponse>> {
        return elrondApi.getStakingRewards()
    }

}