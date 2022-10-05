package com.explwa.jexchange.domain.usecases

import com.explwa.jexchange.domain.repositories.TransactionsRepository
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class GetMyTokenTransactionsUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {

    fun getMyTokenTransfers(address: String, token: String) : Single<List<TransactionsResponse>> {
        return repository.getAddressWithUsername(address)
            .flatMap { username ->
                repository.getMyTokenTransfersCount(username.address.toString(), token)
                    .flatMap { size ->
                        repository.getMyTokenTransfers(username.address.toString(), token, size)
                    }
            }.onErrorResumeNext {
                repository.getMyTokenTransfersCount(address, token)
                    .flatMap { size ->
                        repository.getMyTokenTransfers(address, token, size)
                    }
            }.doOnError {
                if (address.isNotEmpty())
                    throw Exception("INVALID_ADDRESS")
            }
    }

    fun getTransactionWithHash(txHash: String) : Single<TransactionsResponse> {
        return repository.getTransactionWithHash(txHash)
    }

}