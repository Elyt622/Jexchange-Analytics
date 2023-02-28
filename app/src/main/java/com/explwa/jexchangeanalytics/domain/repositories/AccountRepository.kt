package com.explwa.jexchangeanalytics.domain.repositories

import com.explwa.jexchangeanalytics.data.entities.AccountEntity
import com.explwa.jexchangeanalytics.domain.models.DomainAccount
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface AccountRepository {

    fun getAccountWithUsername(
        username: String
    ) : Single<DomainAccount>

    fun getAccountWithAddress(
        address: String
    ) : Single<DomainAccount>

    fun loadData(
        usernameOrAddress: String
    ) : Single<DomainAccount>

    fun loadData()
    : Observable<DomainAccount>

    fun insertAccount(
        account: AccountEntity
    ) : Completable

    fun getAllTokens(
        address: String
    ) : Observable<List<DomainToken>>

    fun getBalance(
        address: String,
        idToken: String
    ) : Single<List<DomainToken>>

    fun getTokenPrice(
        idToken: String
    ) : Single<Double>

}