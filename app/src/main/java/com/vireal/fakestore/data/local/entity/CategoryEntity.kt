package com.vireal.fakestore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
  @PrimaryKey val id: Int,
  @ColumnInfo("name") val name: String
)
