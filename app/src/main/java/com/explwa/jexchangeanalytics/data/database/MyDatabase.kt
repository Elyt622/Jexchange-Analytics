package com.explwa.jexchangeanalytics.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.explwa.jexchangeanalytics.data.database.dao.AccountDao
import com.explwa.jexchangeanalytics.data.entities.AccountEntity

@Database(entities = [AccountEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}

