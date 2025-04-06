package com.vireal.fakestore.ui.itemlist

import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.ui.base.Message

sealed class ItemListMessage : Message {
  data object LoadItems : ItemListMessage()
  data class ItemsLoaded(val items: List<Item>) : ItemListMessage()
  data class LoadingFailed(val error: String) : ItemListMessage()
  data class ItemClicked(val itemId: Int) : ItemListMessage()
  data object RetryClicked : ItemListMessage()
}