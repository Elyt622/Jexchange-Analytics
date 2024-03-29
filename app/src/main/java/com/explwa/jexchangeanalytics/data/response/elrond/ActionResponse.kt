package com.explwa.jexchangeanalytics.data.response.elrond

import com.google.gson.annotations.SerializedName


data class ActionResponse (

    @SerializedName("category") var category : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("description") var description : String? = null,
    @SerializedName("arguments") var argumentsResponse : ArgumentsResponse? = ArgumentsResponse()

)