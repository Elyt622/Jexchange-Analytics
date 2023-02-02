package com.explwa.jexchangeanalytics.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.explwa.jexchangeanalytics.data.entities.AccountEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface AccountDao {
    @Query("SELECT * FROM AccountEntity")
    fun getAccount() : Observable<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountEntity): Completable
}