package com.explwa.jexchange.data.response.elrond

import com.google.gson.annotations.SerializedName

data class EventsResponse (

    @SerializedName("address"    ) var address    : String?           = null,
    @SerializedName("identifier" ) var identifier : String?           = null,
    @SerializedName("topics"     ) var topics     : ArrayList<String> = arrayListOf(),
    @SerializedName("order"      ) var order      : Int?              = null

)