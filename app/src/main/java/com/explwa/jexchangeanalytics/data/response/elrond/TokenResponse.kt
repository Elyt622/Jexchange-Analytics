package com.explwa.jexchangeanalytics.data.response.elrond

import com.explwa.jexchangeanalytics.domain.DomainModelConvertible
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.google.gson.annotations.SerializedName


data class TokenResponse (

    @SerializedName("identifier"        ) var identifier        : String?  = null,
    @SerializedName("name"              ) var name              : String?  = null,
    @SerializedName("owner"             ) var owner             : String?  = null,
    @SerializedName("decimals"          ) var decimals          : Int?     = null,
    @SerializedName("isPaused"          ) var isPaused          : Boolean? = null,
    @SerializedName("assets"            ) var assets            : AssetsResponse?  = AssetsResponse(),
    @SerializedName("transactions"      ) var transactions      : Int?     = null,
    @SerializedName("accounts"          ) var accounts          : Int?     = null,
    @SerializedName("canUpgrade"        ) var canUpgrade        : Boolean? = null,
    @SerializedName("canMint"           ) var canMint           : Boolean? = null,
    @SerializedName("canBurn"           ) var canBurn           : Boolean? = null,
    @SerializedName("canChangeOwner"    ) var canChangeOwner    : Boolean? = null,
    @SerializedName("canPause"          ) var canPause          : Boolean? = null,
    @SerializedName("canFreeze"         ) var canFreeze         : Boolean? = null,
    @SerializedName("canWipe"           ) var canWipe           : Boolean? = null,
    @SerializedName("price"             ) var price             : Double?  = null,
    @SerializedName("marketCap"         ) var marketCap         : Double?  = null,
    @SerializedName("supply"            ) var supply            : String?  = null,
    @SerializedName("circulatingSupply" ) var circulatingSupply : String?  = null,
    @SerializedName("balance"           ) var balance           : String?  = null,
    @SerializedName("valueUsd"          ) var valueUsd          : Double?  = null

) : DomainModelConvertible<DomainToken> {

    override fun toDomain(): DomainToken {
        return DomainToken(
            identifier,
            name,
            owner,
            decimals,
            isPaused,
            transactions,
            accounts,
            canUpgrade,
            canMint,
            canBurn,
            canChangeOwner,
            canPause,
            canFreeze,
            canWipe,
            price,
            marketCap,
            supply,
            circulatingSupply,
            balance,
            valueUsd,
            assets?.website,
            assets?.description,
            assets?.ledgerSignature,
            assets?.status,
            assets?.pngUrl,
            assets?.svgUrl,
            assets?.socialResponse?.email,
            assets?.socialResponse?.blog,
            assets?.socialResponse?.twitter,
            assets?.socialResponse?.whitepaper,
            assets?.socialResponse?.coinmarketcap,
            assets?.socialResponse?.coingecko
        )
    }

}