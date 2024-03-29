package com.elrond.erdkotlin.domain.transaction

import com.elrond.erdkotlin.domain.wallet.models.Address

class GetTransactionInfoUsecase internal constructor(private val transactionRepository: TransactionRepository) {

    fun execute(txHash: String, sender: Address? = null, withResults: Boolean = false) =
        transactionRepository.getTransactionInfo(txHash, sender, withResults)
}
