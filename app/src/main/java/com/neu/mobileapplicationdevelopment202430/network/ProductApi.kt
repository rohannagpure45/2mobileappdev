package com.neu.mobileapplicationdevelopment202430.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductApi {
    val service: ProductApiService = Retrofit.Builder()
        .baseUrl("https://mobileapplicationdevelopment.pythonanywhere.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApiService::class.java)
}
