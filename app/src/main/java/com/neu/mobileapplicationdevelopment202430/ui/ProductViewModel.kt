package com.neu.mobileapplicationdevelopment202430.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.neu.mobileapplicationdevelopment202430.data.ProductDatabase
import com.neu.mobileapplicationdevelopment202430.repository.ProductRepository
import com.neu.mobileapplicationdevelopment202430.repository.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class SortOrder { NAME_ASC, NAME_DESC, PRICE_ASC, PRICE_DESC }

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ProductRepository(ProductDatabase.get(app).productDao())

    private val _sort = MutableStateFlow(SortOrder.NAME_ASC)
    val sort: StateFlow<SortOrder> = _sort

    private val rawFlow: Flow<Resource<List<com.neu.mobileapplicationdevelopment202430.data.Product>>> =
        repo.getProducts()

    private val combined = rawFlow.combine(_sort) { res, order -> when (res) {
        is Resource.Loading   -> Resource.Loading
        is Resource.Error     -> Resource.Error(res.throwable)
        is Resource.Success   -> {
            val sorted = when (order) {
                SortOrder.NAME_ASC  -> res.data.sortedBy { it.name }
                SortOrder.NAME_DESC -> res.data.sortedByDescending { it.name }
                SortOrder.PRICE_ASC -> res.data.sortedBy { it.price.filter { c->c.isDigit()||c=='.' }.toDouble() }
                SortOrder.PRICE_DESC-> res.data.sortedByDescending { it.price.filter { c->c.isDigit()||c=='.' }.toDouble() }
            }
            Resource.Success(sorted)
        }
    }}.stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)

    val state: StateFlow<Resource<List<com.neu.mobileapplicationdevelopment202430.data.Product>>> = combined

    fun setSort(order: SortOrder) { _sort.value = order }

    init { viewModelScope.launch { rawFlow.collect() } }
}
