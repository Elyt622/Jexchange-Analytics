package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.usecases.GetAllTokens
import com.explwa.jexchangeanalytics.domain.usecases.GetBalance
import com.explwa.jexchangeanalytics.domain.usecases.GetValueFromAccount
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getAllTokens: GetAllTokens,
    private val getBalance: GetBalance,
    private val getValueFromAccount: GetValueFromAccount,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    sealed class ViewState(
        val tokens: List<UITokenItem>,
        val errorMessage: String
    )

    class ShowTokens(tokens: List<UITokenItem>) : ViewState(tokens, "")
    class ShowError(errorMessage: String) : ViewState(listOf(), errorMessage)
    object ShowNone : ViewState(listOf(), "")

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

    fun getViewState(
        address: String,
        viewCreated: Observable<Unit>,
        displayProgress: Observable<Unit>,
    ): Observable<ViewState> =
        Observable.merge(
            viewCreated.map { false },
            displayProgress.map { false },
        )
            .toFlowable(BackpressureStrategy.DROP) //load/refresh should be finished before load/refresh again
            .toObservable()
            .concatMap {
                getAllTokens.invoke(
                    address,
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

    companion object {
        const val JEX_ID = "JEX-9040ca"
    }
}