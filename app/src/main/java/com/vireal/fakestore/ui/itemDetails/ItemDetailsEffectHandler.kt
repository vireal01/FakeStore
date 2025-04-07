package com.vireal.fakestore.ui.itemDetails

import android.util.Log
import com.vireal.fakestore.domain.usecase.GetItemDetailsUseCase
import com.vireal.fakestore.ui.base.EffectHandler
import javax.inject.Inject

class ItemDetailsEffectHandler @Inject constructor(
  private val getItemDetailsUseCase: GetItemDetailsUseCase
) : EffectHandler<ItemDetailsEffect, ItemDetailsMessage> {
  override suspend fun handle(effect: ItemDetailsEffect): ItemDetailsMessage? {
    return when (effect) {
      is ItemDetailsEffect.LoadItemDetails -> {
        try {
          Log.d("ItemDetailsEffectHandler", "Loading item details for ID: ${effect.itemId}")

          val result = getItemDetailsUseCase.getItemDetails(
            id = effect.itemId,
            forceUpdate = true
          )

          if (result.isSuccess) {
            val item = result.getOrThrow()
            Log.d("ItemDetailsEffectHandler", "Successfully loaded item: ${item.title}")
            ItemDetailsMessage.ItemDetailsLoaded(item)
          } else {
            val error = result.exceptionOrNull()?.message ?: "Unknown error"
            Log.e("ItemDetailsEffectHandler", "Error loading item: $error")
            ItemDetailsMessage.LoadingFailed(error)
          }
        } catch (e: Exception) {
          Log.e("ItemDetailsEffectHandler", "Exception loading item: ${e.message}", e)
          ItemDetailsMessage.LoadingFailed(e.message ?: "Unknown error")
        }
      }
      is ItemDetailsEffect.AddToCart -> {
        // TODO: Implement add to cart functionality
        Log.d("ItemDetailsEffectHandler", "Add to cart: ${effect.itemId}")
        null
      }
      is ItemDetailsEffect.ShowError -> {
        Log.e("ItemDetailsEffectHandler", "Error: ${effect.error}")
        ItemDetailsMessage.LoadingFailed(effect.error)
      }
      is ItemDetailsEffect.NavigateToCart -> {
        // Navigation will be handled in the screen
        Log.d("ItemDetailsEffectHandler", "Navigate to cart")
        null //
      }
    }
  }
}