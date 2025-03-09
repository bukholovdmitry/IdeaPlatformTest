package com.example.ideaplatformtest.data

data class ProductCardItem(
    val id: Int,
    val name: String,
    val time: Long,
    val tags: List<String>,
    val amount: Int
)