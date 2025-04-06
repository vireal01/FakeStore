package com.vireal.fakestore.di

import android.content.Context
import androidx.room.Room
import com.vireal.fakestore.data.local.dao.CategoryDao
import com.vireal.fakestore.data.local.dao.ItemDao
import com.vireal.fakestore.data.local.database.AppDatabase
import dagger.hilt.components.SingletonComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(
      context,
      AppDatabase::class.java,
      "item_database"
    ).build()
  }

  @Provides
  @Singleton
  fun provideItemDao(database: AppDatabase): ItemDao {
    return database.itemDao()
  }

  @Provides
  @Singleton
  fun provideCategoryDao(database: AppDatabase): CategoryDao {
    return database.categoryDao()
  }
}