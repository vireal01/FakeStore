package com.vireal.fakestore.data.mapper

import com.vireal.fakestore.data.remote.dto.CategoryDto
import com.vireal.fakestore.domain.model.Category

class CategoryMapper {
  fun Category.toCategoryDto(): CategoryDto {
    return CategoryDto(
      id = this.id,
      name = this.name,
      slug = ""
    )
  }
}