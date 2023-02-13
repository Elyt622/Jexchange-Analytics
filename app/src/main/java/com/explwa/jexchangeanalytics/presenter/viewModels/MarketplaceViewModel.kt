package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.usecases.GetAllTokens
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val usecase: GetAllTokens,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    sealed class ViewState (
        val tokens : List<UITokenItem>,
        val progressBarVisibility : Boolean
    )

    class HomeViewModelStateLoading : ViewState(listOf(), false)
    class HomeViewModelStateSuccess(tokens: List<UITokenItem>) : ViewState(tokens, true)

    fun getAllTokens(): Single<List<UITokenItem>> {
        return usecase.getAllTokens()
            .toObservable()
            .flatMapIterable { it }
            .map { it.toUIItem() }
            .toList()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }
}