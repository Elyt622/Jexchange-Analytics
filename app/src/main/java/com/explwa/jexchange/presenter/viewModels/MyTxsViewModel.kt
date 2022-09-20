package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.app.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class MyTxsViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val mySchedulers: MySchedulers
    ) : ViewModel() {

    sealed class MyTxsViewModelStateSealed(
        val loginIsGone : Boolean,
        val myTxs : List<TransactionsResponse>,
        val progressBarIsGone: Boolean,
    )

    class MyTxsViewModelStateLoading : MyTxsViewModelStateSealed(true, listOf(), false)
    class MyTxsViewModelStateSuccess(myTxs : List<TransactionsResponse>) : MyTxsViewModelStateSealed(true, myTxs, true)
    class MyTxsViewModelStateLogin : MyTxsViewModelStateSealed(false, listOf(), true)
    class MyTxsViewModelStateError(val errorMessage: String) : MyTxsViewModelStateSealed(false, listOf(), true)


    fun getMyTokenTransfers(address: String) : Single<List<TransactionsResponse>> {
        return transactionsRepository.getAddressWithUsername(address)
            .flatMap { username ->
                transactionsRepository.getMyTokenTransfersCount(username.address.toString())
                    .flatMap { size ->
                        transactionsRepository.getMyTokenTransfers(username.address.toString(), size)
                }
            }.onErrorResumeNext {
                transactionsRepository.getMyTokenTransfersCount(address)
                    .flatMap { size ->
                        transactionsRepository.getMyTokenTransfers(address, size)
                    }
            }.doOnError {
                if (address.isNotEmpty())
                    throw Exception("INVALID_ADDRESS")
            }
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }
/*
    fun getMyStakingJex() : Observable<BigInteger> {
        var int = BigInteger("0")
        return transactionsRepository.getMyTokenTransfers()
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
        return transactionsRepository.getMyTokenTransfers()
            .toObservable()
            .map {
                for (element in it)
                    if (element.function != null && element.function.toString() == "fill_offer")
                        int += BigInteger(element.action?.argumentsResponse?.transfers?.get(0)?.value.toString())
            }.flatMap{
                return@flatMap Observable.just(int)
            }
    }
*/
}