package com.neu.mobileapplicationdevelopment202430.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class ProductRepository {
    fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ProductPagingSource() }
        ).flow
    }
}
