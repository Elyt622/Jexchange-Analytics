package com.explwa.jexchange.domain.repositories

import com.explwa.jexchange.domain.models.DomainToken
import com.explwa.jexchange.domain.models.DomainUser
import io.reactivex.rxjava3.core.Single


interface StakingRepository {

    fun getStakingRewards() : Single<List<DomainToken>>

    fun getAddressWithUsername(username: String): Single<DomainUser>

}