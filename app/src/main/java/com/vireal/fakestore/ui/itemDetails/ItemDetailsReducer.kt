package com.vireal.fakestore.ui.itemDetails

import com.vireal.fakestore.ui.base.Reducer

class ItemDetailsReducer: Reducer<ItemDetailsState, ItemDetailsMessage, ItemDetailsEffect> {
  override fun reduce(
    state: ItemDetailsState,
    message: ItemDetailsMessage
  ): Pair<ItemDetailsState, ItemDetailsEffect?> {
    return when (message) {
      is ItemDetailsMessage.LoadItemDetails -> {
        state.copy(isLoading = true) to ItemDetailsEffect.LoadItemDetails(message.itemId)
      }
      is ItemDetailsMessage.ItemDetailsLoaded -> {
        state.copy(isLoading = false, itemDetails = message.itemDetails) to null
      }
      is ItemDetailsMessage.LoadingFailed -> {
        state.copy(isLoading = false, errorMessage = message.error) to null
      }
      is ItemDetailsMessage.AddToCart -> {
        state.copy(isLoading = false) to ItemDetailsEffect.AddToCart(message.itemId)
      }
      is ItemDetailsMessage.NavigateBackClicked -> {
        state.copy(isLoading = false) to ItemDetailsEffect.NavigateBack
      }
    }
  }
}