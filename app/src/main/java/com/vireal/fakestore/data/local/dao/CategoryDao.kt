package com.vireal.fakestore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vireal.fakestore.data.local.entity.CategoryEntity

@Dao
interface CategoryDao {
   @Query("SELECT * FROM category")
   suspend fun getAllCategories(): List<CategoryEntity>

   @Query("SELECT * FROM category WHERE id = :id")
   suspend fun getCategoryById(id: Int): CategoryEntity?

   @Delete
   suspend fun deleteCategory(category: CategoryEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)
}