package com.explwa.jexchangeanalytics.domain.usecases

import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class GetAllTokens @Inject constructor(
    private val repository: TransactionsRepository
) {

    fun getAllTokensName()
    : Single<MutableList<String>> =
        repository.getTokensCountOnJexchange()
            .flatMap { repository.getAllTokensOnJexchange(it) }
            .toObservable()
            .flatMapIterable { it }
            .map { token ->
                token.identifier.toString()
            }.toList()


    fun getAllTokens()
    : Single<List<DomainToken>> =
        repository.getTokensCountOnJexchange()
            .flatMap { repository.getAllTokensOnJexchange(it) }

}