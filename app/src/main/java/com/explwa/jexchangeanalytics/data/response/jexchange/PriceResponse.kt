package com.explwa.jexchangeanalytics.data.response.jexchange

import com.google.gson.annotations.SerializedName

data class PriceResponse (
    @SerializedName("rate" ) var rate : Double? = null,
    @SerializedName("unit" ) var unit : String? = null
)