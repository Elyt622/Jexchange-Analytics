package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.app.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.response.transactions.TransactionsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class MyTxsViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    fun getMyJexTransfers() : Single<List<TransactionsResponse>> {
        return transactionsRepository.getMyJexTransfers()
    }

    fun getMyStakingJex() : Observable<BigInteger> {
        var int = BigInteger("0")
        return transactionsRepository.getMyJexTransfers()
            .toObservable()
            .map {
                for (element in it)
                    if (element.function != null && element.function.toString() == "enter_staking")
                        int += BigInteger(element.action?.argumentsResponse?.transfers?.get(0)?.value.toString())
            }.flatMap{
                return@flatMap Observable.just(int)
            }
    }

    fun getMySoldJex() : Observable<BigInteger> {
        var int = BigInteger("0")
        return transactionsRepository.getMyJexTransfers()
            .toObservable()
            .map {
                for (element in it)
                    if (element.function != null && element.function.toString() == "fill_offer")
                        int += BigInteger(element.action?.argumentsResponse?.transfers?.get(0)?.value.toString())
            }.flatMap{
                return@flatMap Observable.just(int)
            }
    }

}