package com.explwa.jexchangeanalytics.app.di.repository

import com.explwa.jexchangeanalytics.data.repositories.AccountRepositoryImpl
import com.explwa.jexchangeanalytics.domain.repositories.StakingRepository
import com.explwa.jexchangeanalytics.domain.repositories.TransactionsRepository
import com.explwa.jexchangeanalytics.data.repositories.StakingRepositoryImpl
import com.explwa.jexchangeanalytics.data.repositories.TransactionsRepositoryImpl
import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
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

    @Binds
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository
}