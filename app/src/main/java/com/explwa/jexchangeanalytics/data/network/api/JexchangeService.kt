package com.explwa.jexchangeanalytics.data.network.api

import com.explwa.jexchangeanalytics.data.response.jexchange.PriceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface JexchangeService {

    @GET("/prices/{tokenId}")
    fun getPriceForToken(
        @Path("tokenId") tokenId: String
    ) : Single<PriceResponse>

}