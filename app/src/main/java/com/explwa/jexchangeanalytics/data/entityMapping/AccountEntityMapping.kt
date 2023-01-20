package com.explwa.jexchangeanalytics.data.entityMapping

import com.explwa.jexchangeanalytics.data.entities.AccountEntity
import com.explwa.jexchangeanalytics.domain.models.DomainAccount


fun DomainAccount.toEntity(): AccountEntity =
    AccountEntity(
        address,
        herotag,
        balance,
        shard,
        txCount
    )