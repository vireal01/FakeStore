package com.vireal.fakestore.ui.itemlist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vireal.fakestore.domain.usecase.GetItemsUseCase
import com.vireal.fakestore.ui.base.Store
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class ItemListStore @Inject constructor(
  private val effectHandler: ItemListEffectHandler
) : ViewModel() {
  private val store = Store(
    initialState = ItemListState(isLoading = true),
    reducer = ItemListReducer(),
    effectHandler = { effect -> effectHandler.handle(effect) }
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