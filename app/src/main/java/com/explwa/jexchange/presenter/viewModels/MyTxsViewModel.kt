package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.domain.usecases.GetAllTokens
import com.explwa.jexchange.domain.usecases.GetMyTokenTransactions
import com.explwa.jexchange.presenter.model.UITxItem
import com.explwa.jexchange.presenter.model.mapping.toUIItem
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class MyTxsViewModel @Inject constructor(
    private val useCase1: GetAllTokens,
    private val useCase2: GetMyTokenTransactions,
    private val mySchedulers: MySchedulers
    ) : ViewModel() {

    sealed class ViewState(
        val loginIsGone : Boolean,
        val myTxs : List<UITxItem>,
        val progressBarIsGone: Boolean,
    )

    class MyTxsViewModelStateLoading : ViewState(true, listOf(), false)
    class MyTxsViewModelStateSuccess(myTxs : List<UITxItem>) : ViewState(true, myTxs, true)
    class MyTxsViewModelStateLogin : ViewState(false, listOf(), true)
    class MyTxsViewModelStateError(val errorMessage: String) : ViewState(false, listOf(), true)


    fun getViewState(
        address: String,
        token: String,
        viewCreated: Observable<Unit>,
        displayProgress: Observable<Unit>
    ): Observable<ViewState> =
        Observable.merge(
            viewCreated.map { false },
            displayProgress.map { false },
        )
            .toFlowable(BackpressureStrategy.DROP) //load/refresh should be finished before load/refresh again
            .toObservable()
            .flatMap {
                useCase2.invoke(
                    address,
                    token,
                    refresh = true
                )
            }
            .observeOn(mySchedulers.main)
            .map { txPageList ->
                val txList = txPageList.toUIItem()
                if (txList.isEmpty()) {
                    MyTxsViewModelStateLogin()
                } else {
                    MyTxsViewModelStateSuccess(txList)
                }
            }
            .onErrorResumeNext {
                Observable.just(MyTxsViewModelStateError("Error"))
            }

    fun getAllTokensOnJexchange(): Single<MutableList<String>> =
        useCase1.getAllTokensName()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

}