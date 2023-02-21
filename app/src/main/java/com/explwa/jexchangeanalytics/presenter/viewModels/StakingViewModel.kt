package com.explwa.jexchangeanalytics.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchangeanalytics.domain.usecases.GetStakingRewards
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StakingViewModel @Inject constructor(
    private val rewardUseCase: GetStakingRewards,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun getStakingRewards()
    : Single<List<UITokenItem>> =
        rewardUseCase.getStakingRewards()
            .toObservable()
            .flatMapIterable { it }
            .map { it.toUIItem() }
            .toList()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)


}