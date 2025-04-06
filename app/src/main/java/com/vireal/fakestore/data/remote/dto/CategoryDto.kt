package com.vireal.fakestore.data.remote.dto

import com.google.gson.annotations.SerializedName


class CategoryDto(
  @SerializedName("id") val id: Int,
  @SerializedName("name") val name: String,
  @SerializedName("slug") val slug: String
)
