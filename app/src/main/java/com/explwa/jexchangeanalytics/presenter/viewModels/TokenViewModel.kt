package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.repositories.TokenRepository
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val mySchedulers: MySchedulers
): ViewModel() {

    sealed class ViewState(
        val progressBarGone: Boolean,
        val token: UITokenItem.Cell,
        val errorMessage: String
    )

    class ShowToken(token: UITokenItem.Cell) : ViewState(true, token, "")
    class ShowLoading() : ViewState(false, UITokenItem.Cell(), "")
    class ShowError(errorMessage: String) : ViewState(false, UITokenItem.Cell(), errorMessage)

    private fun getTokenDetails(
        idToken: String
    ) : Single<UITokenItem.Cell> =
        tokenRepository.getTokenDetails(idToken)
            .map { it.toUIItem() as UITokenItem.Cell }

    fun getViewState(
        idToken: String
    ) : Observable<ShowToken> =
        getTokenDetails(idToken)
            .toObservable()
            .map { token -> ShowToken(token) }
            .doOnError { ShowError(it.message.toString()) }
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

}