package com.explwa.jexchangeanalytics.domain.models

data class DomainTokenPage (
    var tokenList: List<DomainToken>,
    val canRefresh: Boolean
)