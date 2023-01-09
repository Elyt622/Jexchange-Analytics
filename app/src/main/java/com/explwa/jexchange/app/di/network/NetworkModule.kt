package com.explwa.jexchange.app.di.network

import com.explwa.jexchange.data.network.api.ElrondApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providesElrondApi(): ElrondApi =
        Retrofit.Builder()
            .baseUrl("https://api.multiversx.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(ElrondApi::class.java)
}