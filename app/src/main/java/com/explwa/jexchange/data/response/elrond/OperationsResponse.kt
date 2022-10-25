package com.explwa.jexchange.data.response.elrond

import com.google.gson.annotations.SerializedName

data class OperationsResponse (

    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("action"     ) var action     : String? = null,
    @SerializedName("type"       ) var type       : String? = null,
    @SerializedName("esdtType"   ) var esdtType   : String? = null,
    @SerializedName("identifier" ) var identifier : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("sender"     ) var sender     : String? = null,
    @SerializedName("receiver"   ) var receiver   : String? = null,
    @SerializedName("value"      ) var value      : String? = null,
    @SerializedName("decimals"   ) var decimals   : Int?    = null,
    @SerializedName("svgUrl"     ) var svgUrl     : String? = null,
    @SerializedName("data"       ) var data       : String? = null

)
