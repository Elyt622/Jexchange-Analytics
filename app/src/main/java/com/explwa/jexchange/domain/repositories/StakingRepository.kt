package com.explwa.jexchange.domain.repositories

import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import com.explwa.jexchange.domain.models.DomainTransaction
import io.reactivex.rxjava3.core.Single


interface StakingRepository {

    fun getStakingRewards() : Single<List<TokenResponse>>

    fun getAddressWithUsername(username: String): Single<UsernameResponse>

}