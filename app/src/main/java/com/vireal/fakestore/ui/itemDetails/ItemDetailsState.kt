package com.vireal.fakestore.ui.itemDetails

import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.ui.base.State

data class ItemDetailsState(
  val isLoading: Boolean = false,
  val errorMessage: String? = null,
  val itemDetails: Item? = null,
): State