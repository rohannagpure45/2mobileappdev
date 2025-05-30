package com.neu.mobileapplicationdevelopment202430.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class ProductPagingSource : PagingSource<Int, Product>() {

    // Generate 100 placeholder products; convert ints to Strings
    private val productList: List<Product> = List(100) { index ->
        val id = (index + 1).toString()
        val name = "Product $id"
        // alternate food/equipment for demo
        val isFood = (index % 2 == 0)
        val category = if (isFood) "food" else "equipment"
        val expiryDate = if (isFood) "2025-01-${(index % 28 + 1).toString().padStart(2, '0')}" else null
        val price = ((index + 1) * 10).toString()
        val warranty = if (!isFood) ((index % 5) + 1).toString() else null
        val imageUrl = if (isFood)
            "https://via.placeholder.com/150/6D7F7A/FFFFFF?text=Food+$id"
        else
            "https://via.placeholder.com/150/016A7B/FFFFFF?text=Equip+$id"

        Product(
            id = id,
            name = name,
            category = category,
            expiryDate = expiryDate,
            price = price,
            warranty = warranty,
            imageUrl = imageUrl
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val start = params.key ?: 0
        val end = (start + params.loadSize).coerceAtMost(productList.size)
        val page = productList.subList(start, end)
        return LoadResult.Page(
            data = page,
            prevKey = if (start == 0) null else start - params.loadSize,
            nextKey = if (end >= productList.size) null else end
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(state.config.pageSize)
        }
    }
}
