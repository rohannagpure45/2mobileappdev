package com.neu.mobileapplicationdevelopment202430.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neu.mobileapplicationdevelopment202430.data.Product
import com.neu.mobileapplicationdevelopment202430.data.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    // Flow of paginated product data
    val products: Flow<PagingData<Product>> = repository.getProducts().cachedIn(viewModelScope)
}
