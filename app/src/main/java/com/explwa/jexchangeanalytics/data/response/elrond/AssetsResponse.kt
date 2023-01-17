package com.explwa.jexchangeanalytics.data.response.elrond

import com.google.gson.annotations.SerializedName


data class AssetsResponse (

    @SerializedName("website") var website : String? = null,
    @SerializedName("description") var description : String? = null,
    @SerializedName("social") var socialResponse : SocialResponse? = SocialResponse(),
    @SerializedName("ledgerSignature") var ledgerSignature : String? = null,
    @SerializedName("status") var status : String? = null,
    @SerializedName("extraTokens") var extraTokens : ArrayList<String> = arrayListOf(),
    @SerializedName("pngUrl") var pngUrl : String? = null,
    @SerializedName("svgUrl") var svgUrl : String? = null

)