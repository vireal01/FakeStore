package com.vireal.fakestore.ui.itemDetails

import android.util.Log
import com.vireal.fakestore.ui.base.Reducer

class ItemDetailsReducer: Reducer<ItemDetailsState, ItemDetailsMessage, ItemDetailsEffect> {
  override fun reduce(
    state: ItemDetailsState,
    message: ItemDetailsMessage
  ): Pair<ItemDetailsState, ItemDetailsEffect?> {
    return when (message) {
      is ItemDetailsMessage.LoadItemDetails -> {
        Log.d("ItemDetailsReducer", "Loading item details: ${message.itemId}")
        state.copy(
          isLoading = true,
          errorMessage = null,
          itemDetails = null
        ) to ItemDetailsEffect.LoadItemDetails(message.itemId)
      }
      is ItemDetailsMessage.ItemDetailsLoaded -> {
        Log.d("ItemDetailsReducer", "Item details loaded: ${message.itemDetails.title}")
        state.copy(
          isLoading = false,
          itemDetails = message.itemDetails,
          errorMessage = null
        ) to null
      }
      is ItemDetailsMessage.LoadingFailed -> {
        Log.e("ItemDetailsReducer", "Loading failed: ${message.error}")
        state.copy(
          isLoading = false,
          errorMessage = message.error
        ) to null
      }
      is ItemDetailsMessage.AddToCart -> {
        state.copy(isLoading = true) to ItemDetailsEffect.AddToCart(message.itemId)
      }
    }
  }
}