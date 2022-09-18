package com.explwa.jexchange.app.di.viewmodel

import com.explwa.jexchange.presenter.util.MySchedulers
import com.explwa.jexchange.presenter.util.MySchedulersImpl
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