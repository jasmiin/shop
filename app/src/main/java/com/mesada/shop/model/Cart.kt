package com.mesada.shop.model

import androidx.recyclerview.widget.DiffUtil

class Cart(var product: Product?, var quantity: Int) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val cartItem = o as Cart
        return quantity == cartItem.quantity && product == cartItem.product
    }

    companion object {
        @kotlin.jvm.JvmField
        var itemCallback: DiffUtil.ItemCallback<Cart> = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.quantity == newItem.quantity
            }

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
        }
    }

}