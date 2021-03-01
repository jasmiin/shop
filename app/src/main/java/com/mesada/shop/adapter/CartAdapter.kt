package com.mesada.shop.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mesada.shop.adapter.CartAdapter.CartViewHolder
import com.mesada.shop.databinding.CartRowBinding
import com.mesada.shop.model.Cart

class CartAdapter(private val cartInterface: CartInterface) : ListAdapter<Cart, CartViewHolder>(Cart.itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cartRowBinding = CartRowBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(cartRowBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.cartRowBinding.cartItem = getItem(position)
        holder.cartRowBinding.cartBackground.setBackgroundResource(getItem(position)!!.product!!.imageResourceBg)
        holder.cartRowBinding.executePendingBindings()
    }

    inner class CartViewHolder(var cartRowBinding: CartRowBinding) : RecyclerView.ViewHolder(cartRowBinding.root) {

        init {
            cartRowBinding.deleteProductButton.setOnClickListener { cartInterface.deleteItem(getItem(adapterPosition)) }
        }
    }

    interface CartInterface {
        fun deleteItem(cartItem: Cart?)
        fun changeQuantity(cartItem: Cart?, quantity: Int)
    }

}