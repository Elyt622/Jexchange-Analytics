package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    sealed class ViewState (
        val tokens : List<TokenResponse>,
        val progressBarVisibility : Boolean
    )

    class HomeViewModelStateLoading : ViewState(listOf(), false)
    class HomeViewModelStateSuccess(tokens: List<TokenResponse>) : ViewState(tokens, true)

    fun getAllTokensOnJexchange(): Single<List<TokenResponse>> {
        return repository.getTokensCountOnJexchange()
            .flatMap { repository.getAllTokensOnJexchange(it) }
            .toObservable()
            .flatMapIterable { it }
            .filter { it.price != null }
            .toList()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }
}