package com.explwa.jexchangeanalytics.domain.repositories

import com.explwa.jexchangeanalytics.domain.models.DomainUser
import io.reactivex.rxjava3.core.Single

interface AccountRepository {

    fun getAccountWithUsername(username: String) : Single<DomainUser>

    fun getAccountWithAddress(address: String) : Single<DomainUser>

}