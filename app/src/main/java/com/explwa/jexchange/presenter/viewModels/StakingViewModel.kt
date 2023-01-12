package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.domain.usecases.GetStakingRewards
import com.explwa.jexchange.presenter.model.UITokenItem
import com.explwa.jexchange.presenter.model.mapping.toUIItem
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StakingViewModel @Inject constructor(
    private val rewardUseCase: GetStakingRewards,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun getStakingRewards() : Single<List<UITokenItem>> {
        return rewardUseCase.getStakingRewards()
            .toObservable()
            .flatMapIterable { it }
            .map { it.toUIItem() }
            .toList()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }

}