package com.explwa.jexchange.data.network.api

import com.explwa.jexchange.data.response.transactions.TransactionsResponse
import com.explwa.jexchange.data.response.staking.StakingResponse
import com.explwa.jexchange.data.response.username.UsernameResponse
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
    fun getStakingRewards() : Single<List<StakingResponse>>

    @GET("/transactions?receiver=erd1qqqqqqqqqqqqqpgqawkm2tlyyz6vtg02fcr5w02dyejp8yrw0y8qlucnj2&token=JEX-9040ca&status=success&function=Create_offer&order=desc")
    fun getJexTransactions() : Single<List<TransactionsResponse>>

    @GET("/accounts/erd1fdq6nmaa62c0cz8f299ycsz0q8lyfr7q87gqpjwnweux5uu9pqcq68ejhz/transfers?token=JEX-9040ca&status=success&order=desc&size=104")
    fun getMyJexTransfers() : Single<List<TransactionsResponse>>

}