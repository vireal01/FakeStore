package com.vireal.fakestore.di

import com.vireal.fakestore.data.local.dao.ItemDao
import com.vireal.fakestore.data.mapper.ItemMapper
import com.vireal.fakestore.data.remote.api.ItemApiService
import com.vireal.fakestore.data.repository.ItemRepositoryImpl
import com.vireal.fakestore.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  @Singleton
  fun provideItemRepository(
    apiService: ItemApiService,
    itemDao: ItemDao,
    itemMapper: ItemMapper
  ): ItemRepository {
    return ItemRepositoryImpl(apiService, itemDao, itemMapper)
  }
}