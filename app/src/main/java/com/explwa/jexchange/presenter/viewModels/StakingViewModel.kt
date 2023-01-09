package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.domain.usecases.GetStakingRewards
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StakingViewModel @Inject constructor(
    private val rewardUseCase: GetStakingRewards,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun getStakingRewards() : Single<List<TokenResponse>> {
        return rewardUseCase.getStakingRewards()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }

}