package com.explwa.jexchangeanalytics.domain.usecases

import com.explwa.jexchangeanalytics.domain.models.DomainTransactionPage
import com.explwa.jexchangeanalytics.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class GetMyTokenTransactions @Inject constructor(
    private val repository: TransactionsRepository
) {

    private var txPageArrayList = ArrayList<DomainTransactionPage>()
    private var from : Int = 0

    fun invoke(
        address: String,
        token: String,
        refresh: Boolean = false
    ): Observable<List<DomainTransactionPage>> =
        Observable.defer {
            if (refresh) {
                txPageArrayList = ArrayList()
                from = 0
            } else {
                from += 13
            }
            repository.getTxPage(
                address,
                token,
                13,
                from
            )
                .map {
                    txPageArrayList.apply {
                        add(it)
                    }
                }.toObservable()
        }

}