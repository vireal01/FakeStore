package com.vireal.fakestore.domain.usecase

import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.domain.repository.ItemRepository

class GetItemsUseCase(private val itemRepository: ItemRepository) {
  suspend operator fun invoke(): Result<List<Item>> {
    return itemRepository.getItems()
  }
}