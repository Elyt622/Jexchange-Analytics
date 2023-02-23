package com.explwa.jexchangeanalytics.data.repositories

import android.util.Log
import com.explwa.jexchangeanalytics.data.database.dao.AccountDao
import com.explwa.jexchangeanalytics.data.entities.AccountEntity
import com.explwa.jexchangeanalytics.data.network.api.ElrondApi
import com.explwa.jexchangeanalytics.domain.models.DomainAccount
import com.explwa.jexchangeanalytics.domain.models.DomainToken
import com.explwa.jexchangeanalytics.domain.repositories.AccountRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val elrondApi: ElrondApi,
    private val accountDao: AccountDao
) : AccountRepository {

    override var account : Single<DomainAccount>? = null

    // Get account with username
    override fun getAccountWithUsername(
        username: String
    ) : Single<DomainAccount> =
        elrondApi.getAccountWithUsername(username)
            .map { it.toDomain() }
            .doOnError { Log.d("DEBUG1", it.message.toString()) }

    // Get account with address
    override fun getAccountWithAddress(
        address: String
    ) : Single<DomainAccount> =
        elrondApi.getAccountWithAddress(address)
            .map { it.toDomain() }
            .doOnError { Log.d("DEBUG2", it.message.toString()) }

    override fun loadData(
        usernameOrAddress: String
    ) : Single<DomainAccount> =
        getAccountWithUsername(usernameOrAddress)
            .map {
                this.account = Single.just(it)
                it
            }
            .onErrorResumeNext {
                getAccountWithAddress(usernameOrAddress)
                    .map {
                        this.account = Single.just(it)
                        it
                    }
            }

    override fun loadData()
    : Observable<DomainAccount> =
        accountDao.getAccount()
            .map { it.last().toDomain() }

    override fun insertAccount(
        account: AccountEntity
    ) : Completable =
        accountDao.insertAccount(account)

    override fun getAllTokens() : Observable<List<DomainToken>>? =
        account?.blockingGet()?.address?.let {
            elrondApi.getAllTokens(
                it,
                elrondApi.getTokensCount(account!!.blockingGet().address!!).blockingGet(),
                0
            ).flatMapIterable { it }
                .map { it.toDomain() }
                .toList()
                .toObservable()
        }

    override fun getBalance(
        idToken: String
    ) : Single<List<DomainToken>> =
        elrondApi.getToken(
            accountDao.getAccount().blockingFirst().last().address,
            idToken
        ).toObservable()
            .flatMapIterable { it }
            .map { it.toDomain() }
            .toList()
            .doOnError {
                Log.d("DEBUG", it.message.toString())
            }

    override fun getTotalValue(
        address: String
    ) : Single<String> =
        elrondApi.getAllTokens(
            accountDao.getAccount().blockingFirst().last().address,
            60,
            0
        )
            .flatMapIterable { it }
            .map { it.valueUsd.toString() }
            .singleOrError()

}