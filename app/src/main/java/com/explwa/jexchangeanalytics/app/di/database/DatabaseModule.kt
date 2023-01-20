package com.explwa.jexchangeanalytics.app.di.database

import android.content.Context
import androidx.room.Room
import com.explwa.jexchangeanalytics.data.database.MyDatabase
import com.explwa.jexchangeanalytics.data.database.dao.AccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return Room.databaseBuilder(appContext,
            MyDatabase::class.java,
            "MyDatabase.db")
            .build()
    }

    @Provides
    fun provideUserDao(database: MyDatabase): AccountDao {
        return database.accountDao()
    }
}
