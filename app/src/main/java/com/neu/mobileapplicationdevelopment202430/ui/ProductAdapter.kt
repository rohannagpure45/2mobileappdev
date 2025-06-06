package com.neu.mobileapplicationdevelopment202430.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.neu.mobileapplicationdevelopment202430.data.Product
import com.neu.mobileapplicationdevelopment202430.databinding.ItemProductBinding

class ProductAdapter(private var products: List<Product> = emptyList()) :
    RecyclerView.Adapter<ProductAdapter.VH>() {

    class VH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = products[position]
        with(holder.binding) {
            productImage.load(p.imageUrl)
            productName.text = p.name
            if (p.expiryDate != null) {
                date.text = p.expiryDate
                date.visibility = View.VISIBLE
            } else {
                date.visibility = View.GONE
            }
            if (p.warranty != null) {
                warranty.text = "${p.warranty} years"
                warranty.visibility = View.VISIBLE
            } else {
                warranty.visibility = View.GONE
            }
            price.text = "$${p.price}"
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
