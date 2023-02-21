package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.models.DomainAccount
import com.explwa.jexchangeanalytics.domain.usecases.GetAddress
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usecase: GetAddress,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun setAccount (
        usernameOrAddress: String
    ): Single<DomainAccount> =
        usecase.execute(usernameOrAddress)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

    fun insertAccountInDB(account: DomainAccount)
    : Completable = usecase.insertAccountInDB(account)
        .subscribeOn(mySchedulers.io)
        .observeOn(mySchedulers.main)

}