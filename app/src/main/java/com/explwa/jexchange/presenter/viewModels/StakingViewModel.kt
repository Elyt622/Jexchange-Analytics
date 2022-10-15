package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.domain.usecases.GetStakingJex
import com.explwa.jexchange.domain.usecases.GetStakingRewards
import com.explwa.jexchange.presenter.util.MySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class StakingViewModel @Inject constructor(
    private val stakingUseCase: GetStakingJex,
    private val rewardUseCase: GetStakingRewards,
    private val mySchedulers: MySchedulers
) : ViewModel() {

    fun getStakingRewards() : Single<List<TokenResponse>> {
        return rewardUseCase.getStakingRewards()
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }

    fun getStakingJex(address: String) : Single<BigInteger> {
        return stakingUseCase.getStaking(address)
            .subscribeOn(mySchedulers.io)
            .observeOn(mySchedulers.main)
    }
}