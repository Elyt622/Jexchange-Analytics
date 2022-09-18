package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.app.domain.repositories.StakingRepository
import com.explwa.jexchange.data.response.staking.StakingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StakingViewModel @Inject constructor(
    private val stakingRepository: StakingRepository
) : ViewModel() {

    fun getStakingRewards() : Single<List<StakingResponse>> {
        return stakingRepository.getStakingRewards()
    }
}