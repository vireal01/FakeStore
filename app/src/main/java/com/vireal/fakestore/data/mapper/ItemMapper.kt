package com.vireal.fakestore.data.mapper

import com.vireal.fakestore.data.local.entity.ItemEntity
import com.vireal.fakestore.data.remote.dto.ItemDto
import com.vireal.fakestore.domain.model.Item

class ItemMapper {
  fun mapEntityToDomain(itemEntity: ItemEntity): Item {
    return Item(
      id = itemEntity.id,
      title = itemEntity.title,
      price = itemEntity.price,
      description = itemEntity.description ?: "",
    )
  }

  fun mapDtoToEntity(itemDto: ItemDto): ItemEntity {
    return ItemEntity(
      id = itemDto.id,
      title = itemDto.title,
      price = itemDto.price,
      description = itemDto.description,
    )
  }

  fun mapDtoToDomain(itemDto: ItemDto): Item {
    return Item(
      id = itemDto.id,
      title = itemDto.title,
      price = itemDto.price,
      description = itemDto.description,
    )
  }
}