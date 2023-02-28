package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.usecases.GetBalance
import com.explwa.jexchangeanalytics.domain.usecases.GetValueFromAccount
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getBalance: GetBalance,
    private val getValueFromAccount: GetValueFromAccount,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun getJexBalance(address: String)
    : Single<List<DomainToken>> =
        getBalance.getBalance(address, JEX_ID)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

    fun getTotalValue(address: String)
    : Single<BigDecimal> =
        getValueFromAccount.getTotalValue(address)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)

    companion object {
        const val JEX_ID = "JEX-9040ca"
    }
}