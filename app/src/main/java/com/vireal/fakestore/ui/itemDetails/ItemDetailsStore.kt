package com.vireal.fakestore.ui.itemDetails

import androidx.lifecycle.ViewModel
import com.vireal.fakestore.ui.base.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDetailsStore @Inject constructor(
  private val effectHandler: ItemDetailsEffectHandler
): ViewModel() {
  private val store = Store(
    initialState = ItemDetailsState(isLoading = true),
    reducer = ItemDetailsReducer(),
    effectHandler = { effect -> effectHandler.handle(effect) }
  )

  val state = store.state
  val effects = store.effects

  fun dispatch(message: ItemDetailsMessage) {
    store.dispatch(message)
  }
}