package com.vireal.fakestore.ui.itemlist

import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.ui.base.State

data class ItemListState(
  val items: List<Item> = emptyList(),
  val isLoading: Boolean = false,
  val error: String? = null,
) : State
