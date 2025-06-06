package com.neu.mobileapplicationdevelopment202430.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val price: String,
    val expiryDate: String?,
    val warranty: String?,
    val image: String,
    val type: String
)
