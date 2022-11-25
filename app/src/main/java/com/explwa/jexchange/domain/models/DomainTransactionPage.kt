package com.explwa.jexchange.domain.models

data class DomainTransactionPage(
    val txsList: List<DomainTransaction>,
    val canRefresh: Boolean
)
