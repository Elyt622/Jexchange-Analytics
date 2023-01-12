package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.domain.usecases.GetAllTokens
import com.explwa.jexchange.presenter.model.UITokenItem
import com.explwa.jexchange.presenter.model.mapping.toUIItem
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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