package com.example.ideaplatformtest.data

import kotlinx.coroutines.flow.Flow

interface ProductCardRepository {
    suspend fun fetchData(): Flow<List<ProductCardItem>>

    suspend fun deleteItem(productCardItem: ProductCardItem)

    suspend fun changeAmount(productCardItemId: Int, newAmount: Int)

    suspend fun filteredData(name: String): Flow<List<ProductCardItem>>
}