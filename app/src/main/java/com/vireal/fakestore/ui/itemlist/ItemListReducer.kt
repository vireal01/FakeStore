package com.vireal.fakestore.ui.itemlist

import com.vireal.fakestore.ui.base.Reducer

class ItemListReducer : Reducer<ItemListState, ItemListMessage, ItemListEffect> {
  override fun reduce(state: ItemListState, message: ItemListMessage): Pair<ItemListState, ItemListEffect?> {
    return when (message) {
      is ItemListMessage.LoadItems -> {
        state.copy(isLoading = true) to ItemListEffect.LoadItems
      }

      is ItemListMessage.ItemsLoaded -> {
        state.copy(items = message.items, isLoading = false) to null
      }

      is ItemListMessage.LoadingFailed -> {
        state.copy(isLoading = false, error = message.error) to ItemListEffect.ShowError(message.error)
      }

      is ItemListMessage.ItemClicked -> {
        state to ItemListEffect.NavigateToItemDetails(message.itemId)
      }

      is ItemListMessage.RetryClicked -> {
        state.copy(error = null) to ItemListEffect.Retry
      }
    }
  }
}
