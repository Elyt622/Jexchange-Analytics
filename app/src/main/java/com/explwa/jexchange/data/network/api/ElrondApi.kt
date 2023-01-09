package com.explwa.jexchange.data.network.api

import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import com.explwa.jexchange.domain.models.DomainTransaction
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ElrondApi {

    @GET("/usernames/{username}")
    fun getAddressWithUsername(
        @Path("username") username: String
    ) : Single<UsernameResponse>

    @GET("/accounts/erd1qqqqqqqqqqqqqpgqd3r2mh64wzcmuqtpmqs3tyluxsencr9w0y8qft6uyv/tokens?size=50")
    fun getStakingRewards() : Single<List<TokenResponse>>

    @GET("/accounts/{address}/transfers?status=success&order=desc")
    fun getMyTokenTransfers(
        @Path("address") address: String,
        @Query("token") token: String,
        @Query("size") size: Int,
        @Query("from") from: Int
    ) : Single<List<DomainTransaction>>

    @GET("/accounts/{address}/transfers/count?status=success")
    fun getMyTokenTransfersCount(
        @Path("address") address: String,
        @Query("token") token: String
    ) : Single<Int>

    @GET("/accounts/erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2/tokens/count")
    fun getTokensCountOnJexchange() : Single<Int>

    @GET("/accounts/erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2/tokens")
    fun getAllTokensOnJexchange(
        @Query("size") size: Int
    ) : Single<List<TokenResponse>>

    @GET("/transactions/{txHash}")
    fun getTransactionWithHash(
        @Path("txHash") txHash: String
    ) : Single<DomainTransaction>

}