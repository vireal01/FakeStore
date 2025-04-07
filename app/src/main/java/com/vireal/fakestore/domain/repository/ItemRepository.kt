package com.vireal.fakestore.domain.repository

import com.vireal.fakestore.domain.model.Item

interface ItemRepository {
  suspend fun getItems(): Result<List<Item>>
  suspend fun getItemById(id: Int, forceUpdate: Boolean): Result<Item>
  suspend fun getItemsByCategoryId(id: Int): Result<List<Item>>
  suspend fun updateItem(item: Item): Result<Unit>
  suspend fun deleteItem(id: Int): Result<Unit>
}
