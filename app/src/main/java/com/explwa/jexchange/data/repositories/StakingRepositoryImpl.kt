package com.explwa.jexchange.data.repositories

import com.explwa.jexchange.domain.repositories.StakingRepository
import com.explwa.jexchange.data.network.api.ElrondApi
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StakingRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : StakingRepository {

    override fun getStakingRewards(): Single<List<TokenResponse>> {
        return elrondApi.getStakingRewards()
    }

    override fun getAddressWithUsername(username: String): Single<UsernameResponse> {
        return elrondApi.getAddressWithUsername(username)
    }

    override fun getEnterStaking(address: String, size: Int): Single<List<TransactionsResponse>> {
        return elrondApi.getEnterStaking(address, size)
    }

    override fun getEnterStakingCount(address: String): Single<Int> {
        return elrondApi.getEnterStakingCount(address)
    }

    override fun getExitStaking(address: String, size: Int): Single<List<TransactionsResponse>> {
        return elrondApi.getExitStaking(address, size)
    }

    override fun getExitStakingCount(address: String): Single<Int> {
        return elrondApi.getExitStakingCount(address)
    }

    override fun getExitStakingWithPenalty(address: String, size: Int): Single<List<TransactionsResponse>> {
        return elrondApi.getExitStakingWithPenalty(address, size)
    }

    override fun getExitStakingWithPenaltyCount(address: String): Single<Int> {
        return elrondApi.getExitStakingWithPenaltyCount(address)
    }

}