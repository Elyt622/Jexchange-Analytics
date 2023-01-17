package com.explwa.jexchangeanalytics.app.di.viewmodel

import com.explwa.jexchangeanalytics.presenter.util.MySchedulers
import com.explwa.jexchangeanalytics.presenter.util.MySchedulersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsPresenterModule {

    @Binds
    abstract fun bindNetworkSchedulers(networkSchedulers: MySchedulersImpl): MySchedulers
}