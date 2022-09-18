package com.explwa.jexchange.data.network.api

import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.data.response.elrond.UsernameResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ElrondApi {

    @GET("accounts/erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2/transactions?status=success&withScamInfo=true")
    fun getTransactions(
        @Query("sender") senderAddress: String
    ) : Single<List<TransactionsResponse>>

    @GET("/usernames/{username}")
    fun getAddressWithUsername(
        @Path("username") username: String
    ) : Single<UsernameResponse>

    @GET("/accounts/erd1qqqqqqqqqqqqqpgqd3r2mh64wzcmuqtpmqs3tyluxsencr9w0y8qft6uyv/tokens?size=50")
    fun getStakingRewards() : Single<List<TokenResponse>>

    @GET("/transactions?receiver=erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2&token=JEX-9040ca&status=success&function=Create_offer&order=desc")
    fun getJexTransactions() : Single<List<TransactionsResponse>>

    @GET("/accounts/{address}/transfers?token=JEX-9040ca&status=success&order=desc")
    fun getMyTokenTransfers(
        @Path("address") address: String,
        @Query("size") size: Int
    ) : Single<List<TransactionsResponse>>

    @GET("/accounts/{address}/transfers/count?token=JEX-9040ca&status=success")
    fun getMyTokenTransfersCount(
        @Path("address") address: String
    ) : Single<Int>

    @GET("/accounts/erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2/tokens/count")
    fun getTokensCountOnJexchange() : Single<Int>

    @GET("/accounts/erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2/tokens")
    fun getAllTokensOnJexchange(
        @Query("size") size: Int
    ) : Single<List<TokenResponse>>

}