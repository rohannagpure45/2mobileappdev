package com.neu.mobileapplicationdevelopment202430.network

import com.neu.mobileapplicationdevelopment202430.model.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("getProducts")
    suspend fun getProducts(): List<Product>
}
