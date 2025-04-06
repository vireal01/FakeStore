package com.vireal.fakestore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vireal.fakestore.data.local.entity.ItemEntity

@Dao
interface ItemDao {
  @Query("SELECT * FROM item")
  fun getAll(): List<ItemEntity>

  @Query("SELECT * FROM item WHERE id = :id")
  fun getById(id: Int): ItemEntity?

  //insert item
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(item: ItemEntity)

  //update item
  @Query("UPDATE item SET title = :title, description = :description, price = :price WHERE id = :id")
  fun updateItem(id: Int, title: String, description: String, price: Double)

  //delete item
  @Query("DELETE FROM item WHERE id = :id")
  fun deleteItem(id: Int)
}