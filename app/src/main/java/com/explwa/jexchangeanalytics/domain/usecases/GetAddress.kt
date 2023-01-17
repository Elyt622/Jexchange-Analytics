package com.explwa.jexchangeanalytics.domain.usecases

import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class GetAddress @Inject constructor(
    private val repository: AccountRepository
){

    lateinit var address: Single<String>

    fun getAddress(
        usernameOrAddress: String
    ): Single<String> =
        repository.getAccountWithAddress(usernameOrAddress)
            .map { user ->
                user.address.toString()
            }.onErrorResumeNext {
                repository.getAccountWithUsername(usernameOrAddress)
                    .map { user ->
                        user.address.toString()
                    }
            }


}


