package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.app.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    sealed class HomeViewModelStateSealed (
        val tokens : List<TokenResponse>,
        val progressBarVisibility : Boolean
    )

    class HomeViewModelStateLoading : HomeViewModelStateSealed(listOf(), true)
    class HomeViewModelStateSuccess(tokens: List<TokenResponse>) : HomeViewModelStateSealed(tokens, false)

    fun getAllTokensOnJexchange(): Single<List<TokenResponse>> {
        return repository.getTokensCountOnJexchange()
            .flatMap { repository.getAllTokensOnJexchange(it) }
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }
}