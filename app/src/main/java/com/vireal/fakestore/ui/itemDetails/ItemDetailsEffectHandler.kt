package com.vireal.fakestore.ui.itemDetails

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
          val itemDetails = getItemDetailsUseCase.getItemDetails(effect.itemId)
          ItemDetailsMessage.ItemDetailsLoaded(itemDetails.getOrThrow())
        } catch (e: Exception) {
          ItemDetailsMessage.LoadingFailed(e.message ?: "Unknown error")
        }
      }
      is ItemDetailsEffect.AddToCart -> null //TODO: Implement add to cart
      is ItemDetailsEffect.ShowError -> {
        ItemDetailsMessage.LoadingFailed(effect.error)
      }
      is ItemDetailsEffect.NavigateToCart -> null //TODO: Implement navigation to cart
      is ItemDetailsEffect.NavigateBack -> null //TODO: Implement navigation back
    }
  }
}