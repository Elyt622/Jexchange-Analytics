package com.explwa.jexchangeanalytics.domain.usecases

import com.explwa.jexchangeanalytics.domain.models.DomainTokenPage
import com.explwa.jexchangeanalytics.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class GetAllTokens @Inject constructor(
    private val repository: TransactionsRepository
) {

    private var tokenPageArrayList = mutableListOf<DomainTokenPage>()
    private var from = -10

    fun invoke(
        address: String,
        refresh: Boolean = false
    ): Observable<List<DomainTokenPage>> =
        Observable.defer {
            if (refresh) {
                tokenPageArrayList = mutableListOf()
                from = 0
            } else {
                from += 10
            }
            repository.getAllTokens(
                address,
                10,
                from
            )
                .map {
                    tokenPageArrayList.apply {
                        add(it)
                    }
                }
        }

}