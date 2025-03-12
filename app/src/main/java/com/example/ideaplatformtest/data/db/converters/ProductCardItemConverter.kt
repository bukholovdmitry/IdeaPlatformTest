package com.example.ideaplatformtest.data.db.converters

import com.example.ideaplatformtest.data.ProductCardItem
import com.example.ideaplatformtest.data.db.entity.ProductCardItemEntity

fun ProductCardItemEntity.toItem(): ProductCardItem {
    val listOfTags = when {
        this.tags.removePrefix("[")
            .removeSuffix("]").trim().isEmpty() -> emptyList<String>()

        else -> this.tags.removePrefix("[")
            .removeSuffix("]")
            .split(",")
            .map { it.trim().removeSurrounding("\"") }
    }

    return ProductCardItem(
        id = this.id,
        name = this.name,
        time = this.time,
        tags = listOfTags,
        amount = this.amount
    )
}

fun ProductCardItem.toEntity(): ProductCardItemEntity {
    return ProductCardItemEntity(
        id = this.id,
        name = this.name,
        time = this.time,
        tags = "[" + this.tags.joinToString(", ") + "]",
        amount = this.amount
    )
}