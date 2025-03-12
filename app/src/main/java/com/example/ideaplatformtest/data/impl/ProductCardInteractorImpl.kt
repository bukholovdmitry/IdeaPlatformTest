package com.example.ideaplatformtest.data.impl

import com.example.ideaplatformtest.data.ProductCardItem
import com.example.ideaplatformtest.data.ProductCardRepository
import com.example.ideaplatformtest.domain.ProductCardInteractor
import kotlinx.coroutines.flow.Flow

class ProductCardInteractorImpl(private val productCardRepository: ProductCardRepository) :
    ProductCardInteractor {
    override suspend fun fetchData(): Flow<List<ProductCardItem>> {
        return productCardRepository.fetchData()
    }

    override suspend fun deleteItem(productCardItem: ProductCardItem) {
        productCardRepository.deleteItem(productCardItem)
    }

    override suspend fun changeAmount(productCardItemId: Int, newAmount: Int) {
        productCardRepository.changeAmount(productCardItemId, newAmount)
    }

    override suspend fun filteredData(name: String): Flow<List<ProductCardItem>> {
        return productCardRepository.filteredData(name)
    }
}