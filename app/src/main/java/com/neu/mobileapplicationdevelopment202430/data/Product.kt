package com.neu.mobileapplicationdevelopment202430.data

/**
 * A single product entry.
 *
 * @param id          Unique identifier (as string).
 * @param name        The display name.
 * @param category    Either "food" or "equipment".
 * @param expiryDate  ISO date string (e.g. "2024-07-23") for food, or null.
 * @param price       Price as decimal string (e.g. "55.31").
 * @param warranty    Warranty in years as string (e.g. "3") for equipment, or null.
 * @param imageUrl    URL pointing to an image for this product.
 */
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val expiryDate: String?,
    val price: String,
    val warranty: String?,
    val imageUrl: String
)
