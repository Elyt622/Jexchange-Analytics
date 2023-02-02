package com.explwa.jexchangeanalytics.domain.usecases

import com.explwa.jexchangeanalytics.data.entityMapping.toEntity
import com.explwa.jexchangeanalytics.domain.models.DomainAccount
import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class GetAddress @Inject constructor(
    private val repository: AccountRepository
){

    fun execute(
        usernameOrAddress : String
    ) : Single<DomainAccount> =
        repository.loadData(usernameOrAddress)
            .map {
                it
            }

    fun getAccount()
    : Observable<DomainAccount> =
        repository.loadData()

    fun insertAccountInDB(
        account: DomainAccount
    ) : Completable = repository.insertAccount(account.toEntity())

}


