package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.domain.usecases.GetMyTokenTransactions
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class MyTxsViewModel @Inject constructor(
    private val useCase: GetMyTokenTransactions,
    private val mySchedulers: MySchedulers
    ) : ViewModel() {

    sealed class ViewState(
        val loginIsGone : Boolean,
        val myTxs : List<TransactionsResponse>,
        val progressBarIsGone: Boolean,
    )

    class MyTxsViewModelStateLoading : ViewState(true, listOf(), false)
    class MyTxsViewModelStateSuccess(myTxs : List<TransactionsResponse>) : ViewState(true, myTxs, true)
    class MyTxsViewModelStateLogin : ViewState(false, listOf(), true)
    class MyTxsViewModelStateError(val errorMessage: String) : ViewState(false, listOf(), true)

    fun getTransactionsWithToken(address: String, token: String) : Single<List<TransactionsResponse>> =
        Observable.fromSingle(getMyTokenTransfers(address, token))
            .concatMapIterable { it }
            .concatMap {
                if(it.originalTxHash != null)
                    Observable.fromSingle(getTransactionWithHash(it.originalTxHash!!))
                else
                    Observable.just(it)
            }.toList()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

    private fun getMyTokenTransfers(address: String, token: String)
    : Single<List<TransactionsResponse>> =
        useCase.getMyTokenTransfers(address, token)

    private fun getTransactionWithHash(txHash: String)
    : Single<TransactionsResponse> =
        useCase.getTransactionWithHash(txHash)

    fun getAllTokensOnJexchange(): Single<MutableList<String>> =
        useCase.getAllTokensOnJexchange()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

}