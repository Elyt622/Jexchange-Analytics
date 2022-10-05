package com.explwa.jexchange.data.response.elrond

import com.google.gson.annotations.SerializedName


data class LogsResponse (

    @SerializedName("id"      ) var id      : String?           = null,
    @SerializedName("address" ) var address : String?           = null,
    @SerializedName("events"  ) var events  : ArrayList<EventsResponse> = arrayListOf()

)