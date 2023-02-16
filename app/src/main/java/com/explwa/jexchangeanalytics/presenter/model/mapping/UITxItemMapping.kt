package com.explwa.jexchangeanalytics.presenter.model.mapping

import com.explwa.jexchangeanalytics.domain.models.DomainTransaction
import com.explwa.jexchangeanalytics.domain.models.DomainTransactionPage
import com.explwa.jexchangeanalytics.presenter.model.UITxItem

fun DomainTransaction.toUIItem(): UITxItem =
    UITxItem.Cell(
        txHash = txHash,
        receiver = receiver,
        sender = sender,
        value = value,
        fee = fee,
        timestamp = timestamp,
        data = data,
        function = function,
        action = action,
        type = type,
        originalTxHash = originalTxHash,
        price = price,
        operations = operations
    )

fun List<DomainTransactionPage>.toUIItem(): List<UITxItem> =
    ArrayList<UITxItem>().also {
        for(txPage in this) {
            it.addAll(
                txPage.txsList.map { tx ->
                     tx.toUIItem()
                }
            )
        }
        if(isNotEmpty() && last().canRefresh) {
            it.add(UITxItem.Progress)
        }
    }