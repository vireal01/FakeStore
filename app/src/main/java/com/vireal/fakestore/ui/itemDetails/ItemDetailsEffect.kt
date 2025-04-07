package com.vireal.fakestore.ui.itemDetails

import com.vireal.fakestore.ui.base.Effect

sealed class ItemDetailsEffect: Effect {
  data class LoadItemDetails(val itemId: Int): ItemDetailsEffect()
  data class AddToCart(val itemId: Int): ItemDetailsEffect()
  data class ShowError(val error: String): ItemDetailsEffect()
  data object NavigateToCart: ItemDetailsEffect()
  data object NavigateBack: ItemDetailsEffect()
}