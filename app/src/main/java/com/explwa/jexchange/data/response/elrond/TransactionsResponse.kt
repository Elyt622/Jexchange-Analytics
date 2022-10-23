package com.explwa.jexchange.data.response.elrond

import com.google.gson.annotations.SerializedName
import java.math.BigInteger


data class TransactionsResponse (

    @SerializedName("txHash") var txHash : String? = null,
    @SerializedName("gasLimit") var gasLimit : Int? = null,
    @SerializedName("gasPrice") var gasPrice : Int? = null,
    @SerializedName("gasUsed") var gasUsed : Int? = null,
    @SerializedName("miniBlockHash") var miniBlockHash : String? = null,
    @SerializedName("nonce") var nonce : Int? = null,
    @SerializedName("receiver") var receiver : String? = null,
    @SerializedName("round") var round : Int? = null,
    @SerializedName("sender") var sender : String? = null,
    @SerializedName("signature") var signature : String? = null,
    @SerializedName("status") var status : String? = null,
    @SerializedName("value") var value : String? = null,
    @SerializedName("fee") var fee : String? = null,
    @SerializedName("timestamp") var timestamp : Int? = null,
    @SerializedName("data") var data : String? = null,
    @SerializedName("function") var function : String? = null,
    @SerializedName("action") var action : ActionResponse? = ActionResponse(),
    @SerializedName("type") var type : String? = null,
    @SerializedName("originalTxHash") var originalTxHash : String? = null,
    @SerializedName("results") var results : ArrayList<ResultsResponse> = arrayListOf(),
    @SerializedName("price") var price : Double? = null,
    @SerializedName("operations") var operations : ArrayList<OperationsResponse>? = arrayListOf()

)