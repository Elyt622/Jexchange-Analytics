package com.explwa.jexchangeanalytics.presenter.model

sealed class UITokenItem {
    data class Cell(
        var identifier        : String?  = null,
        var name              : String?  = null,
        var owner             : String?  = null,
        var decimals          : Int?     = null,
        var isPaused          : Boolean? = null,
        var transactions      : Int?     = null,
        var accounts          : Int?     = null,
        var canUpgrade        : Boolean? = null,
        var canMint           : Boolean? = null,
        var canBurn           : Boolean? = null,
        var canChangeOwner    : Boolean? = null,
        var canPause          : Boolean? = null,
        var canFreeze         : Boolean? = null,
        var canWipe           : Boolean? = null,
        var price             : Double?  = null,
        var marketCap         : Double?  = null,
        var supply            : String?  = null,
        var circulatingSupply : String?  = null,
        var balance           : String?  = null,
        var valueUsd          : Double?  = null,
        var website           : String?  = null,
        var description       : String?  = null,
        var ledgerSignature   : String?  = null,
        var status            : String?  = null,
        var extraTokens       : ArrayList<String> = arrayListOf(),
        var pngUrl            : String? = null,
        var svgUrl            : String? = null,
        var email             : String? = null,
        var blog              : String? = null,
        var twitter           : String? = null,
        var whitepaper        : String? = null,
        var coinmarketcap     : String? = null,
        var coingecko         : String? = null
    ) : UITokenItem()
    object Progress : UITokenItem()
}