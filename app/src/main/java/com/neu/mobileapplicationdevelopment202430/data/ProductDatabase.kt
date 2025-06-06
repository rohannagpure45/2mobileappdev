package com.neu.mobileapplicationdevelopment202430.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.neu.mobileapplicationdevelopment202430.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile private var INSTANCE: ProductDatabase? = null
        fun get(context: Context): ProductDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "products.db"
                ).build().also { INSTANCE = it }
            }
    }
}
