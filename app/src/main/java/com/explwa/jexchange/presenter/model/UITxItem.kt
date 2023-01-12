package com.explwa.jexchange.presenter.model

import com.explwa.jexchange.data.response.elrond.ActionResponse
import com.explwa.jexchange.domain.models.DomainOperation

sealed class UITxItem {
    data class Cell(
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
    ) : UITxItem()
    object Progress : UITxItem()
}