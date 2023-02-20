package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.usecases.GetAllTokens
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val usecase: GetAllTokens,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    sealed class ViewState(
        val tokens: List<UITokenItem>,
        val progressBarGone: Boolean,
        val errorMessage: String
        )

    class ShowLoading : ViewState(listOf(), false, "")
    class ShowTokens(tokens: List<UITokenItem>) : ViewState(tokens, true, "")
    class ShowError(errorMessage: String) : ViewState(listOf(), true, errorMessage)
    object ShowNone : ViewState(listOf(), true, "")

    fun getViewState(
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
            .concatMap {
                usecase.invoke(
                    it
                ).subscribeOn(mySchedulers.io)
            }.observeOn(mySchedulers.main)
            .map { tokenPageList ->
                val tokenList = tokenPageList.toUIItem()
                if (tokenList.isEmpty()) ShowNone
                else ShowTokens(tokenList.distinct())
            }.doOnError {
                ShowError(it.message.toString())
            }

}