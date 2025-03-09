package com.example.ideaplatformtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideaplatformtest.data.ProductCardItem
import com.example.ideaplatformtest.domain.ProductCardInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductCardViewModel(
    private val productCardInteractor: ProductCardInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<ProductCardItem>>(emptyList())
    val uiState: StateFlow<List<ProductCardItem>> = _uiState

    init {
        viewModelScope.launch {
            productCardInteractor.fetchData().collect {
                _uiState.value = it
            }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            productCardInteractor.fetchData().collect {
                _uiState.value = it
            }
        }
    }

    fun deleteProductCardItem(productCardItem: ProductCardItem) {
        viewModelScope.launch {
            productCardInteractor.deleteItem(productCardItem)
            productCardInteractor.fetchData().collect {
                _uiState.value = it
            }
        }
    }

    fun changeAmountProductCardItem(productCardItemId: Int, newAmount: Int) {
        viewModelScope.launch {
            productCardInteractor.changeAmount(productCardItemId, newAmount)
            productCardInteractor.fetchData().collect {
                _uiState.value = it
            }
        }

    }

    fun filterData(name: String) {
        viewModelScope.launch {
            productCardInteractor.filteredData(name).collect {
                _uiState.value = it
            }
        }
    }
}