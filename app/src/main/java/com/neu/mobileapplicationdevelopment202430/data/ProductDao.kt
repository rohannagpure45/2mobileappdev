package com.neu.mobileapplicationdevelopment202430.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neu.mobileapplicationdevelopment202430.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Product>)

    @Query("SELECT * FROM products")
    suspend fun getAllOnce(): List<Product>

    @Query("SELECT * FROM products")
    fun observeAll(): Flow<List<Product>>
}
