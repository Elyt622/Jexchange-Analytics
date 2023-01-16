package com.explwa.jexchange.domain.models

import java.math.BigInteger

data class DomainUser(
    val address: String?,
    val herotag: String?,
    var balance: String? = null,
    var shard: Int? = null,
    var txCount: Int? = null,
    val stakingJex: BigInteger?
)

