package com.explwa.jexchange.data.response.elrond

import com.google.gson.annotations.SerializedName

data class SenderAssetsResponse (

    @SerializedName("name" ) var name : String?           = null,
    @SerializedName("tags" ) var tags : ArrayList<String> = arrayListOf()

)