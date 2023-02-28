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

    fun getAllTokens()
    : Observable<List<DomainToken>>?

    fun getBalance(
        idToken: String
    ) : Single<List<DomainToken>>

    fun getTotalValue(
        address: String
    ) : Single<String>

}