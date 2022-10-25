package com.explwa.jexchange.domain.usecases

import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.domain.repositories.TransactionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class GetMyTokenTransactions @Inject constructor(
    private val repository: TransactionsRepository
) {

    fun getMyTokenTransfers(address: String, token: String)
    : Single<List<TransactionsResponse>> =
        repository.getAddressWithUsername(address)
            .flatMap { username ->
                repository.getMyTokenTransfersCount(
                    username.address.toString(),
                    token
                ).flatMap { size ->
                        repository.getMyTokenTransfers(
                            username.address.toString(),
                            token,
                            13,
                            0
                        )
                    }
            }.onErrorResumeNext {
                repository.getMyTokenTransfersCount(
                    address,
                    token
                ).flatMap { size ->
                        repository.getMyTokenTransfers(
                            address,
                            token,
                            13,
                            0
                        )
                    }
            }.doOnError {
                if (address.isNotEmpty())
                    throw Exception("INVALID_ADDRESS")
            }

    fun getTransactionWithHash(txHash: String)
    : Single<TransactionsResponse> =
        repository.getTransactionWithHash(txHash)

    fun getAllTokensOnJexchange()
    : Single<MutableList<String>> =
        repository.getTokensCountOnJexchange()
            .flatMap {
                repository.getAllTokensOnJexchange(it)
                    .toObservable()
                    .flatMapIterable { it }
                    .map { token ->
                        token.identifier.toString()
                    }.toList()
            }

}