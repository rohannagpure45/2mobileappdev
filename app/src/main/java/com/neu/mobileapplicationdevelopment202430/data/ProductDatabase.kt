package com.neu.mobileapplicationdevelopment202430.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile private var inst: ProductDatabase? = null
        fun get(ctx: Context): ProductDatabase =
            inst ?: synchronized(this) {
                inst ?: Room.databaseBuilder(
                    ctx.applicationContext,
                    ProductDatabase::class.java,
                    "products.db"
                ).build().also { inst = it }
            }
    }
}
