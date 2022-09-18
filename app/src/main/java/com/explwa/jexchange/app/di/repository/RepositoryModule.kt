package com.explwa.jexchange.app.di.repository

import com.explwa.jexchange.app.domain.repositories.StakingRepository
import com.explwa.jexchange.app.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.repositories.StakingRepositoryImpl
import com.explwa.jexchange.data.repositories.TransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindStakingRepository(stakingRepositoryImpl: StakingRepositoryImpl): StakingRepository

    @Binds
    abstract fun bindTransactionsRepository(transactionsRepositoryImpl: TransactionsRepositoryImpl): TransactionsRepository
}