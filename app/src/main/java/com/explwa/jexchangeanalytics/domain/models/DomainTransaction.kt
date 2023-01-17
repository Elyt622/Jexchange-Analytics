package com.explwa.jexchangeanalytics.domain.models

import com.explwa.jexchangeanalytics.data.response.elrond.*

data class DomainTransaction(
    var txHash: String? = null,
    var receiver: String? = null,
    var sender: String? = null,
    var value: String? = null,
    var fee: String? = null,
    var timestamp: Int? = null,
    var data: String? = null,
    var function: String? = null,
    var action: ActionResponse? = ActionResponse(),
    var type: String? = null,
    var originalTxHash: String? = null,
    var price: Double? = null,
    var operations: ArrayList<DomainOperation>? = null
)
