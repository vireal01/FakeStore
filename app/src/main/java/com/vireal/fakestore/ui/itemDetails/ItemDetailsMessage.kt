package com.vireal.fakestore.ui.itemDetails

import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.ui.base.Message

sealed class ItemDetailsMessage: Message {
  data class LoadItemDetails(val itemId: Int): ItemDetailsMessage()
  data class ItemDetailsLoaded(val itemDetails: Item): ItemDetailsMessage()
  data class LoadingFailed(val error: String): ItemDetailsMessage()
  data class AddToCart(val itemId: Int): ItemDetailsMessage()
}