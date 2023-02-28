package com.explwa.jexchangeanalytics.domain.usecases

import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetBalance @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun getBalance(
        address: String,
        idToken: String
    ) : Single<List<DomainToken>> =
        accountRepository
            .getBalance(address, idToken)

}