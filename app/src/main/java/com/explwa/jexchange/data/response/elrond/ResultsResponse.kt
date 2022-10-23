package com.explwa.jexchange.data.response.elrond

import com.google.gson.annotations.SerializedName

data class ResultsResponse (

    @SerializedName("hash"           ) var hash           : String?       = null,
    @SerializedName("timestamp"      ) var timestamp      : Int?          = null,
    @SerializedName("nonce"          ) var nonce          : Int?          = null,
    @SerializedName("gasLimit"       ) var gasLimit       : Int?          = null,
    @SerializedName("gasPrice"       ) var gasPrice       : Int?          = null,
    @SerializedName("value"          ) var value          : String?       = null,
    @SerializedName("sender"         ) var sender         : String?       = null,
    @SerializedName("receiver"       ) var receiver       : String?       = null,
    @SerializedName("data"           ) var data           : String?       = null,
    @SerializedName("prevTxHash"     ) var prevTxHash     : String?       = null,
    @SerializedName("originalTxHash" ) var originalTxHash : String?       = null,
    @SerializedName("callType"       ) var callType       : String?       = null,
    @SerializedName("miniBlockHash"  ) var miniBlockHash  : String?       = null,

)