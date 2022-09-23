package com.explwa.jexchange.presenter.viewModels

import androidx.lifecycle.ViewModel
import com.explwa.jexchange.domain.repositories.StakingRepository
import com.explwa.jexchange.data.response.elrond.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StakingViewModel @Inject constructor(
    private val stakingRepository: StakingRepository
) : ViewModel() {

    fun getStakingRewards() : Single<List<TokenResponse>> {
        return stakingRepository.getStakingRewards()
    }
}