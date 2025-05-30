package com.neu.mobileapplicationdevelopment202430.ui.product

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neu.mobileapplicationdevelopment202430.R
import com.neu.mobileapplicationdevelopment202430.data.Product
import com.neu.mobileapplicationdevelopment202430.databinding.ItemProductBinding

class ProductAdapter(
    private val items: List<Product>
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    inner class VH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val product = items[position]
        with(holder.binding) {
            // background color by category
            val bgColor = if (product.category == "equipment")
                Color.parseColor("#016A7B")
            else
                Color.parseColor("#6D7F7A")
            container.setBackgroundColor(bgColor)

            // load image by category URL
            val url = product.imageUrl
            Glide.with(imgProduct.context)
                .load(url)
                .centerCrop()
                .into(imgProduct)

            tvName.text = product.name

            // show expiry date or warranty
            tvExtra.text = when (product.category) {
                "food" -> product.expiryDate ?: ""
                "equipment" -> "${product.warranty} years"
                else -> ""
            }

            tvPrice.text = "$${product.price}"
        }
    }

    override fun getItemCount() = items.size
}
