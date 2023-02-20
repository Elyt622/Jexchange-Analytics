package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.models.DomainAccount
import com.explwa.jexchangeanalytics.domain.usecases.GetAddress
import com.explwa.jexchangeanalytics.domain.usecases.GetAllTokens
import com.explwa.jexchangeanalytics.domain.usecases.GetMyTokenTransactions
import com.explwa.jexchangeanalytics.presenter.model.UITxItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class MyTxsViewModel @Inject constructor(
    private val getAlltokens: GetAllTokens,
    private val getMyTokenTxs: GetMyTokenTransactions,
    private val getAddress: GetAddress,
    private val mySchedulers: MySchedulers
    ) : ViewModel() {

    sealed class ViewState(
        val myTxs : List<UITxItem>,
        val progressBarIsGone: Boolean,
    )

    class ShowLoading : ViewState(listOf(), false)
    class ShowTxs(myTxs : List<UITxItem>) : ViewState(myTxs, true)
    class ShowError(val errorMessage: String) : ViewState(listOf(), true)

    var account = fun(): Observable<DomainAccount> =
        getAddress.getAccount()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

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
                getMyTokenTxs.invoke(
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

}