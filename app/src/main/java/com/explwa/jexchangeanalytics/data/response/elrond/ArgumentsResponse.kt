package com.explwa.jexchangeanalytics.data.response.elrond

import com.google.gson.annotations.SerializedName


data class ArgumentsResponse (

    @SerializedName("transfers") var transfers : ArrayList<TransfersResponse> = arrayListOf(),
    @SerializedName("receiver") var receiver : String? = null,
    @SerializedName("functionName") var functionName : String? = null,
    @SerializedName("functionArgs") var functionArgs   : ArrayList<String> = arrayListOf(),

)