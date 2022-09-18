package com.explwa.jexchange.data.response.transactions

import com.google.gson.annotations.SerializedName


data class ReceiverAssetsResponse (

    @SerializedName("name") var name : String? = null,
    @SerializedName("tags") var tags : ArrayList<String> = arrayListOf()

)