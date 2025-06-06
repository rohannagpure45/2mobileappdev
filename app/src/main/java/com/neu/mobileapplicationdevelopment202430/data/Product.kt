package com.neu.mobileapplicationdevelopment202430.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val expiryDate: String?,
    val price: String,
    val warranty: String?,
    val imageUrl: String
)
