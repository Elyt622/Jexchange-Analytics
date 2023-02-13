package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.repositories.TokenRepository
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val mySchedulers: MySchedulers
): ViewModel() {

    fun getTokenDetails(
        idToken: String
    ): Single<DomainToken> =
        tokenRepository.getTokenDetails(idToken)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

}