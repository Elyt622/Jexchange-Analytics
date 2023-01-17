package com.explwa.jexchangeanalytics.data.response.elrond

import com.google.gson.annotations.SerializedName


data class SocialResponse (

    @SerializedName("email"         ) var email         : String? = null,
    @SerializedName("blog"          ) var blog          : String? = null,
    @SerializedName("twitter"       ) var twitter       : String? = null,
    @SerializedName("whitepaper"    ) var whitepaper    : String? = null,
    @SerializedName("coinmarketcap" ) var coinmarketcap : String? = null,
    @SerializedName("coingecko"     ) var coingecko     : String? = null

)