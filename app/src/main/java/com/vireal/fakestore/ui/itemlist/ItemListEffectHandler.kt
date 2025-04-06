package com.vireal.fakestore.ui.itemlist

import android.util.Log
import com.vireal.fakestore.domain.usecase.GetItemsUseCase
import com.vireal.fakestore.ui.base.EffectHandler
import javax.inject.Inject

class ItemListEffectHandler @Inject constructor(
  private val getItemsUseCase: GetItemsUseCase
) : EffectHandler<ItemListEffect, ItemListMessage> {
  override suspend fun handle(effect: ItemListEffect): ItemListMessage? {
    return when (effect) {
      is ItemListEffect.LoadItems -> {
        try {
          val items = getItemsUseCase()
          ItemListMessage.ItemsLoaded(items.getOrThrow())
        } catch (e: Exception) {
          ItemListMessage.LoadingFailed(e.message ?: "Unknown error")
        }
      }
      is ItemListEffect.NavigateToItemDetails -> null
      is ItemListEffect.Retry -> {
        try {
          val items = getItemsUseCase()
          ItemListMessage.ItemsLoaded(items.getOrThrow())
        } catch (e: Exception) {
          ItemListMessage.LoadingFailed(e.message ?: "Unknown error")
        }
      }
      is ItemListEffect.ShowError -> Log.d("ItemListEffectHandler", "Error: ${effect.error}").let { null }
    }
  }
}