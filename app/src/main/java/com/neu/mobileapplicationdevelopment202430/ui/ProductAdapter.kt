package com.neu.mobileapplicationdevelopment202430.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neu.mobileapplicationdevelopment202430.databinding.ItemProductBinding
import com.neu.mobileapplicationdevelopment202430.model.Product

class ProductAdapter(private val list: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.Holder>() {

    class Holder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val p = list[position]
        with(holder.binding) {
            tvName.text = p.name
            tvPrice.text = "$${p.price.filter { it.isDigit() || it == '.' }}"
            tvExtra.text = p.expiryDate ?: "${p.warranty} warranty"
            Glide.with(root).load(p.image).into(img)
        }
    }

    fun updateData(newList: List<Product>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
