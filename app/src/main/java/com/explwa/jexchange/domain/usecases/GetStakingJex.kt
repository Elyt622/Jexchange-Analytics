package com.explwa.jexchange.domain.usecases

import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.domain.repositories.StakingRepository
import io.reactivex.rxjava3.core.Single
import java.math.BigInteger
import javax.inject.Inject

class GetStakingJex @Inject constructor(
    private val repository: StakingRepository
) {

    fun getStaking(address: String)
    : Single<BigInteger> =
        getEnterStaking(address)
            .flatMap { enterStaking ->
                getExitStaking(address)
                    .flatMap { exitStaking ->
                        getExitStakingWithPenalty(address)
                            .map { exitStakingWithPenalty ->
                                BigInteger("0")
                                    .add(enterStaking)
                                    .subtract(exitStaking)
                                    .subtract(exitStakingWithPenalty)
                            }
                    }
            }

    private fun getEnterStaking(address: String)
    : Single<BigInteger> =
        repository.getAddressWithUsername(address)
            .flatMap { username ->
                repository.getEnterStakingCount(username.address.toString())
                    .flatMap { size ->
                        repository.getEnterStaking(username.address.toString(), size)
                            .map {
                                sumBigInteger(it, true)
                            }
                    }
            }

    private fun getExitStaking(address: String)
    : Single<BigInteger> =
        repository.getAddressWithUsername(address)
            .flatMap { username ->
                repository.getExitStakingCount(username.address.toString())
                    .flatMap { size ->
                        repository.getExitStaking(username.address.toString(), size)
                            .map {
                                sumBigInteger(it, false)
                            }
                    }
            }

    private fun getExitStakingWithPenalty(address: String)
    : Single<BigInteger> =
        repository.getAddressWithUsername(address)
            .flatMap { username ->
                repository.getExitStakingWithPenaltyCount(username.address.toString())
                    .flatMap { size ->
                        repository.getExitStakingWithPenalty(username.address.toString(), size)
                            .map {
                                sumBigInteger(
                                    it,
                                    false
                                )
                            }
                    }
            }

    private fun sumBigInteger(
        txs: List<TransactionsResponse>,
        enterStaking : Boolean
    ): BigInteger {
        var int = BigInteger("0")
        if (enterStaking)
            for (tx1 in txs)
                if (tx1.function != null)
                    int = int.add(
                        BigInteger(
                            tx1.action?.
                            argumentsResponse?.
                            transfers?.
                            get(0)?.
                            value.toString()
                        )
                    )
                else
                    for (tx2 in txs)
                        if (tx2.operations != null && tx2.operations!![0].value != null)
                            int = int.add(
                                BigInteger(
                                    tx2.operations!![0].
                                    value!!
                                )
                            )
        return int
    }
}