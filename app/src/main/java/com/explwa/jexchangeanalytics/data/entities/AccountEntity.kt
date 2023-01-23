package com.explwa.jexchangeanalytics.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.explwa.jexchangeanalytics.domain.DomainModelConvertible
import com.explwa.jexchangeanalytics.domain.models.DomainAccount

@Entity
data class AccountEntity(
    @PrimaryKey
    val address: String,
    @ColumnInfo(name = "herotag")
    val herotag: String?,
    @ColumnInfo(name = "balance")
    val balance: String?,
    @ColumnInfo(name = "shard")
    val shard: Int?,
    @ColumnInfo(name = "txCount")
    val txCount: Int?
    ) : DomainModelConvertible<DomainAccount> {

    override fun toDomain(): DomainAccount =
        DomainAccount(
            address,
            herotag,
            balance,
            shard,
            txCount
        )
}