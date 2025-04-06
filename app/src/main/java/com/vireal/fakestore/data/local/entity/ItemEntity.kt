package com.vireal.fakestore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "item")
data class ItemEntity(
  @PrimaryKey val id: Int,
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "description") val description: String?,
  @ColumnInfo(name = "price") val price: Double,
)