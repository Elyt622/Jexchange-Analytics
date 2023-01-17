package com.explwa.jexchangeanalytics.data.repositories

import com.explwa.jexchangeanalytics.data.network.api.ElrondApi
import com.explwa.jexchangeanalytics.domain.models.DomainUser
import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi
) : AccountRepository {

    // Get account with username
    override fun getAccountWithUsername(username: String)
            : Single<DomainUser> =
        elrondApi.getAccountWithUsername(username)
            .map { it.toDomain() }
            .doOnError { }

    // Get account with address
    override fun getAccountWithAddress(address: String)
            : Single<DomainUser> =
        elrondApi.getAccountWithAddress(address)
            .map { it.toDomain() }
            .doOnError { }

}