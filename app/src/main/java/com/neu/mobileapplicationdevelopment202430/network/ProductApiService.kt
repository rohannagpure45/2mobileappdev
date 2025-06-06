package com.neu.mobileapplicationdevelopment202430.network

import com.neu.mobileapplicationdevelopment202430.data.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("api/getProducts")
    suspend fun getProducts(): List<Product>
}
