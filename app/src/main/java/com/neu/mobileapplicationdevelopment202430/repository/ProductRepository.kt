package com.neu.mobileapplicationdevelopment202430.repository

import com.neu.mobileapplicationdevelopment202430.data.ProductDao
import com.neu.mobileapplicationdevelopment202430.data.Product
import com.neu.mobileapplicationdevelopment202430.network.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}

class ProductRepository(private val dao: ProductDao) {

    fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        val remote = ProductApi.service.getProducts().distinctBy { it.id }
        dao.clear()
        dao.insertAll(remote)
        emit(Resource.Success(remote))
    }.catch { e ->
        val cached = dao.flowAll().first()
        if (cached.isNotEmpty()) emit(Resource.Success(cached))
        else emit(Resource.Error(e))
    }.flowOn(Dispatchers.IO)
}
