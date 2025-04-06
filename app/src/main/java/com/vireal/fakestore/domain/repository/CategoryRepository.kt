package com.vireal.fakestore.domain.repository

import com.vireal.fakestore.domain.model.Category

interface CategoryRepository {
  suspend fun getAllCategories(): Result<List<Category>>
  suspend fun getCategoryById(id: Int): Result<Category>
  suspend fun deleteCategory(id: Int): Result<Unit>
  suspend fun insertCategory(id: Int, name: String): Result<Unit>
}