package com.example.ideaplatformtest.data.impl

import com.example.ideaplatformtest.data.ProductCardItem
import com.example.ideaplatformtest.data.ProductCardRepository
import com.example.ideaplatformtest.data.db.AppDatabase
import com.example.ideaplatformtest.data.db.converters.toEntity
import com.example.ideaplatformtest.data.db.converters.toItem
import com.example.ideaplatformtest.data.db.entity.ProductCardItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductCardRepositoryImpl(private val appDatabase: AppDatabase) : ProductCardRepository {
    override suspend fun fetchData(): Flow<List<ProductCardItem>> = flow {
        emit(convertFromEntity(appDatabase.productCardItemDao().getProductCardItems()))
    }

    override suspend fun deleteItem(productCardItem: ProductCardItem) {
        appDatabase.productCardItemDao().deleteItem(productCardItem.toEntity())
    }

    override suspend fun changeAmount(productCardItemId: Int, newAmount: Int) {
        appDatabase.productCardItemDao().changeAmount(productCardItemId, newAmount)
    }

    override suspend fun filteredData(name: String): Flow<List<ProductCardItem>> = flow {
        emit(convertFromEntity(appDatabase.productCardItemDao().getFilteredProductCardItems(name)))
    }

    private fun convertFromEntity(entities: List<ProductCardItemEntity>): List<ProductCardItem> {
        return entities.map { entity ->
            entity.toItem()
        }
    }
}