package com.vireal.fakestore.data.repository

import com.vireal.fakestore.data.local.dao.ItemDao
import com.vireal.fakestore.data.mapper.ItemMapper
import com.vireal.fakestore.data.remote.api.ItemApiService
import com.vireal.fakestore.domain.model.Item
import com.vireal.fakestore.domain.repository.ItemRepository
import javax.inject.Inject

// The main reason for this class is to provide a way to get data from both local and remote sources.
// It hides the complexity of dealing with different data sources and provides a clean API for the rest of the app to use.
class ItemRepositoryImpl @Inject constructor(
private val apiService: ItemApiService,
private val itemDao: ItemDao,
private val mapper: ItemMapper
) : ItemRepository {

  override suspend fun getItems(): Result<List<Item>> = try {
    // Check is database (cache) is empty
    val localItem = itemDao.getAll()

    if (localItem.isNotEmpty()) {
      // get from local database
      Result.success(localItem.map { mapper.mapEntityToDomain(it) })
    } else {
      // if local database is empty, get from remote API
      val remoteItems = apiService.getItems()

      // store them in local database
      val entities = remoteItems.map {
        mapper.mapDtoToEntity(it).also { entity ->  itemDao.insert(entity) }
      }

      // get database items as a Domain model
      Result.success(entities.map { mapper.mapEntityToDomain(it) })
    }
  } catch (e: Exception) {
    Result.failure(e)
  }

  override suspend fun getItemById(id: Int, forceUpdate: Boolean): Result<Item> = try {
    // Get item from local database
    val localItem = itemDao.getById(id)

    if (localItem != null && !forceUpdate) {
      // If item is found in local database, return it as a Domain model
      Result.success(mapper.mapEntityToDomain(localItem))
    } else {
      // If item is not found in local database, get it from remote API
      val remoteItem = apiService.getItem(id)

      // Convert the remote item DTO to an Entity and insert it into the local database
      val entity = mapper.mapDtoToEntity(remoteItem)
      itemDao.insert(entity)

      // Convert the remote item DTO to a Domain model and return it
      Result.success(mapper.mapDtoToDomain(remoteItem))
    }
  } catch (e: Exception) {
    Result.failure(e)
  }

  override suspend fun getItemsByCategoryId(id: Int): Result<List<Item>> {
    TODO("Not yet implemented")
  }

  override suspend fun updateItem(item: Item): Result<Unit> {
    TODO("Not yet implemented")
  }

  override suspend fun deleteItem(id: Int): Result<Unit> {
    TODO("Not yet implemented")
  }
}