package com.example.ideaplatformtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ideaplatformtest.data.db.dao.ProductCardItemDao
import com.example.ideaplatformtest.data.db.entity.ProductCardItemEntity

@Database(
    version = 1, entities = [
        ProductCardItemEntity::class
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productCardItemDao(): ProductCardItemDao
}
