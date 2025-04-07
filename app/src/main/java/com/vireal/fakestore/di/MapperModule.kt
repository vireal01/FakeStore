package com.vireal.fakestore.di

import com.vireal.fakestore.data.mapper.ItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

  @Provides
  @Singleton
  fun provideItemMapper(): ItemMapper {
    return ItemMapper()
  }
}