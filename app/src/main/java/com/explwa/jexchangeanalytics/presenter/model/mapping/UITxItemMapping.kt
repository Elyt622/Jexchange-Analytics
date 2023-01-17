package com.explwa.jexchangeanalytics.presenter.model.mapping

import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.models.DomainTransaction
import com.explwa.jexchangeanalytics.domain.models.DomainTransactionPage
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
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

fun DomainToken.toUIItem(): UITokenItem =
    UITokenItem.Cell(
        identifier,
        name,
        owner,
        decimals,
        isPaused,
        transactions,
        accounts,
        canUpgrade,
        canMint,
        canBurn,
        canChangeOwner,
        canPause,
        canFreeze,
        canWipe,
        price,
        marketCap,
        supply,
        circulatingSupply,
        balance,
        valueUsd,
        website,
        description,
        ledgerSignature,
        status,
        extraTokens,
        pngUrl,
        svgUrl,
        email,
        blog,
        twitter,
        whitepaper,
        coinmarketcap,
        coingecko
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