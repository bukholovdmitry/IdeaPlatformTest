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
    private var filterText = ""

    init {
        viewModelScope.launch {
            productCardInteractor.fetchData().collect {
                filterText = ""
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
            if (filterText == ""){
                fetchData()
            } else{
                filterData(filterText)
            }

        }
    }

    fun changeAmountProductCardItem(productCardItemId: Int, newAmount: Int) {
        viewModelScope.launch {
            productCardInteractor.changeAmount(productCardItemId, newAmount)
            if (filterText == ""){
                fetchData()
            } else{
                filterData(filterText)
            }
        }

    }

    fun filterData(name: String) {
        viewModelScope.launch {
            filterText = name
            productCardInteractor.filteredData(name).collect {
                _uiState.value = it
            }
        }
    }
}