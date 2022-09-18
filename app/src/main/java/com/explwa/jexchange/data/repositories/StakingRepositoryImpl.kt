package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.app.domain.repositories.StakingRepository
import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.data.response.staking.StakingResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StakingRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : StakingRepository {

    override fun getStakingRewards(): Single<List<StakingResponse>> {
        return elrondApi.getStakingRewards()
    }

}