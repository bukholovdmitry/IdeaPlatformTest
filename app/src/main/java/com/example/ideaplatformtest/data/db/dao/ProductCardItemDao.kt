package com.example.ideaplatformtest.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.ideaplatformtest.data.db.entity.ProductCardItemEntity

@Dao
interface ProductCardItemDao {
    @Query("SELECT * FROM item ORDER BY id")
    suspend fun getProductCardItems(): List<ProductCardItemEntity>

    @Delete
    suspend fun deleteItem(productCardItemEntity: ProductCardItemEntity)

    @Query("UPDATE item SET amount = :amount WHERE id = :id")
    suspend fun changeAmount(id: Int, amount: Int)

    @Query("SELECT * FROM item WHERE name LIKE '%' || :name || '%' ORDER BY id")
    suspend fun getFilteredProductCardItems(name: String): List<ProductCardItemEntity>
}