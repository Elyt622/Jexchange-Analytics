package com.explwa.jexchange.domain.repositories

import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import io.reactivex.rxjava3.core.Single


interface StakingRepository {

    fun getStakingRewards() : Single<List<TokenResponse>>

    fun getEnterStaking(address: String, size: Int): Single<List<TransactionsResponse>>

    fun getEnterStakingCount(address: String): Single<Int>

    fun getExitStaking(address: String, size: Int): Single<List<TransactionsResponse>>

    fun getExitStakingCount(address: String): Single<Int>

    fun getExitStakingWithPenalty(address: String, size: Int): Single<List<TransactionsResponse>>

    fun getExitStakingWithPenaltyCount(address: String): Single<Int>

    fun getAddressWithUsername(username: String): Single<UsernameResponse>

}