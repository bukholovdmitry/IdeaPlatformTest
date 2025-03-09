package com.example.ideaplatformtest.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item")
data class ProductCardItemEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val time: Long,
    val tags: String,
    val amount: Int
)