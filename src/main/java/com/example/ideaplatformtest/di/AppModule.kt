package com.example.ideaplatformtest.di

import androidx.room.Room
import com.example.ideaplatformtest.data.ProductCardRepository
import com.example.ideaplatformtest.data.db.AppDatabase
import com.example.ideaplatformtest.data.impl.ProductCardInteractorImpl
import com.example.ideaplatformtest.data.impl.ProductCardRepositoryImpl
import com.example.ideaplatformtest.domain.ProductCardInteractor
import com.example.ideaplatformtest.ui.ProductCardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "data.db"
        )
            .createFromAsset("data.db")
            .build()
    }

    single<ProductCardRepository> { ProductCardRepositoryImpl(appDatabase = get()) }

    single<ProductCardInteractor> { ProductCardInteractorImpl(productCardRepository = get()) }

    single<ProductCardViewModel> { ProductCardViewModel(productCardInteractor = get()) }
}