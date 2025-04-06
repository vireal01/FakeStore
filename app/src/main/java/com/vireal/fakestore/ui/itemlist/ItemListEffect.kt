package com.vireal.fakestore.ui.itemlist

import com.vireal.fakestore.ui.base.Effect

sealed class ItemListEffect : Effect {
  data object LoadItems : ItemListEffect()
  data class NavigateToItemDetails(val itemId: Int) : ItemListEffect()
  data class ShowError(val error: String) : ItemListEffect()
  data object Retry : ItemListEffect()
}