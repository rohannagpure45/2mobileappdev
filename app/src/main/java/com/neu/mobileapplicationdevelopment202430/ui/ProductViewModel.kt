package com.neu.mobileapplicationdevelopment202430.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neu.mobileapplicationdevelopment202430.model.Product
import com.neu.mobileapplicationdevelopment202430.repository.ProductRepository
import com.neu.mobileapplicationdevelopment202430.repository.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class SortOrder { NAME_ASC, PRICE_ASC, PRICE_DESC }

class ProductViewModel(private val repo: ProductRepository) : ViewModel() {
    private val _state = MutableStateFlow<Resource<List<Product>>>(Resource.Loading)
    val state: StateFlow<Resource<List<Product>>> = _state
    val sortOrder = MutableStateFlow(SortOrder.PRICE_ASC)

    fun load() {
        viewModelScope.launch {
            repo.getProducts().collectLatest { _state.value = it }
        }
    }
}
