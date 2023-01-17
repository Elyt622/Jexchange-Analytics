package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.usecases.GetAllTokens
import com.explwa.jexchangeanalytics.domain.usecases.GetMyTokenTransactions
import com.explwa.jexchangeanalytics.presenter.model.UITxItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
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
        val myTxs : List<UITxItem>,
        val progressBarIsGone: Boolean,
    )

    class ShowLoading : ViewState(listOf(), false)
    class ShowTxs(myTxs : List<UITxItem>) : ViewState(myTxs, true)
    class ShowError(val errorMessage: String) : ViewState(listOf(), true)


    fun getViewState(
        address: String,
        token: String,
        viewCreated: Observable<Unit>,
        displayProgress: Observable<Unit>,
        refresh: Observable<Unit>
    ): Observable<ViewState> =
        Observable.merge(
            viewCreated.map { false },
            displayProgress.map { false },
            refresh.map { true }
        )
            .toFlowable(BackpressureStrategy.DROP) //load/refresh should be finished before load/refresh again
            .toObservable()
            .flatMap {
                useCase2.invoke(
                    address,
                    token,
                    it
                ).subscribeOn(mySchedulers.io)
            }
            .observeOn(mySchedulers.main)
            .map { txPageList ->
                val txList = txPageList.toUIItem()
                if (txList.isEmpty()) {
                    ShowError("")
                } else {
                    ShowTxs(txList)
                }
            }
            .onErrorResumeNext {
                Observable.just(ShowError("Error"))
            }

    fun getAllTokensOnJexchange(): Single<MutableList<String>> =
        useCase1.getAllTokensName()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

}