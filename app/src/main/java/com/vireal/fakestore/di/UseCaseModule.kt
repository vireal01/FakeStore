package com.vireal.fakestore.di

import com.vireal.fakestore.domain.repository.ItemRepository
import com.vireal.fakestore.domain.usecase.GetItemDetailsUseCase
import com.vireal.fakestore.domain.usecase.GetItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

  @Provides
  fun provideGetItemsUseCase(repository: ItemRepository): GetItemsUseCase {
    return GetItemsUseCase(repository)
  }
  @Provides
  fun provideGetItemDetailUseCase(repository: ItemRepository): GetItemDetailsUseCase {
    return GetItemDetailsUseCase(repository)
  }
//
//  @Provides
//  fun provideUpdateItemUseCase(repository: ItemRepository): UpdateItemUseCase {
//    return UpdateItemUseCase(repository)
//  }
}