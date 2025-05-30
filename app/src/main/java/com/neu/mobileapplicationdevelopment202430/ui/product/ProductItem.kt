package com.neu.mobileapplicationdevelopment202430.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.neu.mobileapplicationdevelopment202430.data.Product

@Composable
fun ProductItem(product: Product) {
    val bg = if (product.category == "food") Color(0xFF6D7F7A) else Color(0xFF016A7B)
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(bg)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(bg)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .padding(8.dp)
            ) {
                Text(product.name, color = Color.Black)
                product.expiryDate?.let { Text(it, color = Color.Black) }
                product.warranty?.let { Text("$it years", color = Color.Black) }
                Text("$${product.price}", color = Color.Black)
            }
        }
    }
}
