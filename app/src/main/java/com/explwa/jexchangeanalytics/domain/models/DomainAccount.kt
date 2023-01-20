package com.explwa.jexchangeanalytics.domain.models

data class DomainAccount(
    val address: String?,
    val herotag: String?,
    var balance: String? = null,
    var shard: Int? = null,
    var txCount: Int? = null
)

