package com.vireal.fakestore.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vireal.fakestore.domain.model.Category

data class ItemDto(
  @SerializedName("id") val id: Int,
  @SerializedName("title") val title: String,
  @SerializedName("price") val price: Double,
  @SerializedName("description") val description: String,
)