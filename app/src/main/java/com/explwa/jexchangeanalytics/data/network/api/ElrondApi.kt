package com.explwa.jexchangeanalytics.data.network.api

import com.explwa.jexchangeanalytics.data.response.elrond.TokenResponse
import com.explwa.jexchangeanalytics.data.response.elrond.TransactionsResponse
import com.explwa.jexchangeanalytics.data.response.elrond.UsernameResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ElrondApi {

    @GET("/usernames/{username}")
    fun getAccountWithUsername(
        @Path("username") username: String
    ) : Single<UsernameResponse>

    @GET("/accounts/{address}")
    fun getAccountWithAddress(
        @Path("address") address: String
    ) : Single<UsernameResponse>

    @GET("/accounts/{address}/transfers?status=success&order=desc")
    fun getMyTokenTransfers(
        @Path("address") address: String,
        @Query("token") token: String,
        @Query("size") size: Int,
        @Query("from") from: Int
    ) : Single<List<TransactionsResponse>>

    @GET("/accounts/{address}/transfers/count?status=success")
    fun getMyTokenTransfersCount(
        @Path("address") address: String,
        @Query("token") token: String
    ) : Single<Int>

    @GET("/accounts/{address}/tokens/count")
    fun getTokensCount(
        @Path("address") address: String
    ) : Single<Int>

    @GET("/accounts/{address}/tokens")
    fun getAllTokens(
        @Path("address") address: String,
        @Query("size") size: Int,
        @Query("from") from: Int
    ) : Observable<List<TokenResponse>>

    @GET("/transactions/{txHash}")
    fun getTransactionWithHash(
        @Path("txHash") txHash: String
    ) : Single<TransactionsResponse>

    @GET("/tokens/{idToken}")
    fun getTokenDetails(
        @Path("idToken") idToken: String
    ) : Single<TokenResponse>

}