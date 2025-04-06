package com.vireal.fakestore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vireal.fakestore.data.local.dao.CategoryDao
import com.vireal.fakestore.data.local.dao.ItemDao
import com.vireal.fakestore.data.local.entity.CategoryEntity
import com.vireal.fakestore.data.local.entity.ItemEntity

@Database(entities = [ItemEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun itemDao(): ItemDao
  abstract fun categoryDao(): CategoryDao
}