package com.explwa.jexchange.data.response.transactions

import com.google.gson.annotations.SerializedName


data class TransfersResponse (

    @SerializedName("type") var type : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("ticker") var ticker : String? = null,
    @SerializedName("svgUrl") var svgUrl : String? = null,
    @SerializedName("token") var token : String? = null,
    @SerializedName("decimals") var decimals : Int? = null,
    @SerializedName("value") var value : String? = null

)