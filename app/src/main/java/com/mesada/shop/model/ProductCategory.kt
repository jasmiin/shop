package com.mesada.shop.model

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ProductCategory(var productId: Int, var productCategory: String) {

    companion object {
        @JvmField
        var itemCallback: DiffUtil.ItemCallback<ProductCategory> = object : DiffUtil.ItemCallback<ProductCategory>() {
            override fun areItemsTheSame(oldItem: ProductCategory, newItem: ProductCategory): Boolean {
                return oldItem.productId == newItem.productId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ProductCategory, newItem: ProductCategory): Boolean {
                return oldItem == newItem
            }
        }
    }

}