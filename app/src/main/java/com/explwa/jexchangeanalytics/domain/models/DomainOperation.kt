package com.explwa.jexchangeanalytics.domain.models

data class DomainOperation(
    var id         : String? = null,
    var action     : String? = null,
    var type       : String? = null,
    var esdtType   : String? = null,
    var identifier : String? = null,
    var name       : String? = null,
    var sender     : String? = null,
    var receiver   : String? = null,
    var value      : String? = null,
    var decimals   : Int?    = null,
    var svgUrl     : String? = null,
    var data       : String? = null
)
