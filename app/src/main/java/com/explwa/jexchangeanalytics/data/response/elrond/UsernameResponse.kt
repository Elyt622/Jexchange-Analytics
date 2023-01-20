package com.explwa.jexchangeanalytics.data.response.elrond

import com.explwa.jexchangeanalytics.domain.DomainModelConvertible
import com.explwa.jexchangeanalytics.domain.models.DomainAccount
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

) : DomainModelConvertible<DomainAccount> {

    override fun toDomain(): DomainAccount =
        DomainAccount(
            address = address,
            herotag = username,
            balance = balance,
            shard = shard,
            txCount = txCount,
        )
}
