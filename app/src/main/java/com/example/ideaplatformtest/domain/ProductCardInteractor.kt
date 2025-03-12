package com.example.ideaplatformtest.domain

import com.example.ideaplatformtest.data.ProductCardItem
import kotlinx.coroutines.flow.Flow

interface ProductCardInteractor {
    suspend fun fetchData(): Flow<List<ProductCardItem>>

    suspend fun deleteItem(productCardItem: ProductCardItem)

    suspend fun changeAmount(productCardItemId: Int, newAmount: Int)

    suspend fun filteredData(name: String): Flow<List<ProductCardItem>>
}