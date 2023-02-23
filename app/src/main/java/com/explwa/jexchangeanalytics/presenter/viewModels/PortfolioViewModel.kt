package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.usecases.GetBalance
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getBalance: GetBalance,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun getJexBalance()
    : Single<List<DomainToken>> =
        getBalance.getBalance(JEX_ID)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)


    companion object {
        const val JEX_ID = "JEX-9040ca"
    }
}