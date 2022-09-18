package com.explwa.jexchange.data.response.staking

import com.google.gson.annotations.SerializedName


data class AssetsResponse (

    @SerializedName("website"         ) var website         : String? = null,
    @SerializedName("description"     ) var description     : String? = null,
    @SerializedName("ledgerSignature" ) var ledgerSignature : String? = null,
    @SerializedName("status"          ) var status          : String? = null,
    @SerializedName("pngUrl"          ) var pngUrl          : String? = null,
    @SerializedName("svgUrl"          ) var svgUrl          : String? = null

)