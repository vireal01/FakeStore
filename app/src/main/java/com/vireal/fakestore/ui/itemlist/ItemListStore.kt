package com.vireal.fakestore.ui.itemlist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vireal.fakestore.domain.usecase.GetItemsUseCase
import com.vireal.fakestore.ui.base.Store
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class ItemListStore @Inject constructor(
  private val getItemsUseCase: GetItemsUseCase,
  private val effectHandler: ItemListEffectHandler
) : ViewModel() {
  private val store = Store<ItemListState, ItemListMessage, ItemListEffect>(
    initialState = ItemListState(isLoading = true),
    reducer = ItemListReducer(),
    effectHandler = { effect ->
      when (effect) {
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
  )

  // Делегируем свойства и методы нашему экземпляру store
  val state = store.state
  val effects = store.effects

  fun dispatch(message: ItemListMessage) {
    store.dispatch(message)
  }

  init {
    dispatch(ItemListMessage.LoadItems)
  }
}