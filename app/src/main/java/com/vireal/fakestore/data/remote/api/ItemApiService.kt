package com.vireal.fakestore.data.remote.api

import com.vireal.fakestore.data.remote.dto.ItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemApiService {
  @GET("products")
  suspend fun getItems(): List<ItemDto>

  @GET("products/{id}")
  suspend fun getItem(@Path("id") id: Int): ItemDto

  @GET("products/category/{category}")
  suspend fun getItemsByCategory(@Path("category") category: String): List<ItemDto>
}