package com.explwa.jexchange.app.domain.repositories

import com.explwa.jexchange.data.response.elrond.TokenResponse
import io.reactivex.rxjava3.core.Single


interface StakingRepository {
    fun getStakingRewards() : Single<List<TokenResponse>>
}