package com.mesada.shop.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mesada.shop.R
import com.mesada.shop.adapter.ProductAdapter.ShopViewHolder
import com.mesada.shop.databinding.ProductRowBinding
import com.mesada.shop.model.Product

class ProductAdapter(var shopInterface: ShopInterface) : ListAdapter<Product, ShopViewHolder>(Product.itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val productRowBinding = ProductRowBinding.inflate(layoutInflater, parent, false)
        productRowBinding.shopInterface = shopInterface
        return ShopViewHolder(productRowBinding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val product = getItem(position)
        holder.productRowBinding.product = product
        holder.productRowBinding.imageBackground.setBackgroundColor(Color.parseColor(product!!.bgColor))
        holder.productRowBinding.imageBackground.setBackgroundResource(product!!.imageResourceBg)
        holder.productRowBinding.productImageView.setImageResource(product.imageUrl)
        holder.productRowBinding.executePendingBindings()
    }

    inner class ShopViewHolder(var productRowBinding: ProductRowBinding) : RecyclerView.ViewHolder(productRowBinding.root)

    interface ShopInterface {
        fun addItem(product: Product?)
    }

}