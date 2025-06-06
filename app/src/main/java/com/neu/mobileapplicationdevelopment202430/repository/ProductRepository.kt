package com.neu.mobileapplicationdevelopment202430.repository

import com.neu.mobileapplicationdevelopment202430.data.ProductDao
import com.neu.mobileapplicationdevelopment202430.model.Product
import com.neu.mobileapplicationdevelopment202430.network.ProductApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}

class ProductRepository(
    private val dao: ProductDao,
    private val api: ProductApiService
) {
    fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val remote = api.getProducts().distinctBy { it.id }
            if (remote.isNotEmpty()) dao.insertAll(remote)
            emit(Resource.Success(remote))
        } catch (e: Exception) {
            val cached = dao.getAllOnce()
            if (cached.isNotEmpty()) emit(Resource.Success(cached))
            else emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO).catch { emit(Resource.Error(it)) }
}
