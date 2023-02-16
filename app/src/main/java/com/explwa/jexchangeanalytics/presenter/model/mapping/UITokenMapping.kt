package com.explwa.jexchangeanalytics.presenter.model.mapping

import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.models.DomainTokenPage
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem

fun DomainToken.toUIItem(): UITokenItem =
    UITokenItem.Cell(
        identifier = identifier,
        name = name,
        owner = owner,
        decimals = decimals,
        isPaused = isPaused,
        transactions = transactions,
        accounts = accounts,
        canUpgrade = canUpgrade,
        canMint = canMint,
        canBurn = canBurn,
        canChangeOwner = canChangeOwner,
        canPause = canPause,
        canFreeze = canFreeze,
        canWipe = canWipe,
        price = price,
        marketCap = marketCap,
        supply = supply,
        circulatingSupply = circulatingSupply,
        balance = balance,
        valueUsd = valueUsd,
        website = website,
        description = description,
        ledgerSignature = ledgerSignature,
        status = status,
        extraTokens = extraTokens,
        pngUrl = pngUrl,
        svgUrl = svgUrl,
        email = email,
        blog = blog,
        twitter = twitter,
        whitepaper = whitepaper,
        coinmarketcap = coinmarketcap,
        coingecko = coingecko
    )

fun List<DomainTokenPage>.toUIItem(): List<UITokenItem> =
    ArrayList<UITokenItem>().also {
        for(tokenPage in this) {
            it.addAll(
                tokenPage.tokenList.map { token ->
                    token.toUIItem()
                }
            )
        }
        if(isNotEmpty() && last().canRefresh) {
            it.add(UITokenItem.Progress)
        }
    }