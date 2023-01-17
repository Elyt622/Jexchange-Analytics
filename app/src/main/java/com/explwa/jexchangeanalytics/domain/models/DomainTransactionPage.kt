package com.explwa.jexchangeanalytics.domain.models

data class DomainTransactionPage(
    val txsList: List<DomainTransaction>,
    val canRefresh: Boolean
)
