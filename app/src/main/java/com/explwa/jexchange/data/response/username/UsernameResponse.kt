package com.explwa.jexchange.data.response.username

import com.google.gson.annotations.SerializedName


data class UsernameResponse (

    @SerializedName("address") var address : String? = null,
    @SerializedName("balance") var balance : String? = null,
    @SerializedName("nonce") var nonce : Int? = null,
    @SerializedName("shard") var shard : Int? = null,
    @SerializedName("rootHash") var rootHash : String? = null,
    @SerializedName("txCount") var txCount : Int? = null,
    @SerializedName("scrCount") var scrCount : Int? = null,
    @SerializedName("username") var username : String? = null,
    @SerializedName("developerReward") var developerReward : String? = null

)
