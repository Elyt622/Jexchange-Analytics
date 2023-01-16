package com.explwa.jexchange.domain.usecases

import com.explwa.jexchange.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class GetAddress @Inject constructor(
    private val repository: TransactionsRepository
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


