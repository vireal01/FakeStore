package com.vireal.fakestore.domain.usecase

import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.domain.repository.ItemRepository

class GetItemDetailsUseCase(private val itemRepository: ItemRepository) {
  suspend fun getItemDetails(id: Int): Result<Item> {
    return itemRepository.getItemById(id)
  }
}