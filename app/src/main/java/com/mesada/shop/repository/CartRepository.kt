package com.mesada.shop.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mesada.shop.model.Cart
import com.mesada.shop.model.Product
import java.util.*

class CartRepository {
    private val mutableCart = MutableLiveData<List<Cart>?>()
    private val mutableTotalPrice = MutableLiveData<Double?>()
    val cart: LiveData<List<Cart>?>
        get() {
            if (mutableCart.value == null) {
                initCart()
            }
            return mutableCart
        }

    fun initCart() {
        mutableCart.value = ArrayList()
        calculateCartTotal()
    }

    fun addItemToCart(product: Product?): Boolean {
        if (mutableCart.value == null) {
            initCart()
        }
        val cartItemList: MutableList<Cart> = ArrayList(mutableCart.value)
        for (cartItem in cartItemList) {
            if (product != null) {
                if (cartItem.product!!.id == product.id) {
                    if (cartItem.quantity == 5) {
                        return false
                    }
                    val index = cartItemList.indexOf(cartItem)
                    cartItem.quantity = cartItem.quantity + 1
                    cartItemList[index] = cartItem
                    mutableCart.value = cartItemList
                    calculateCartTotal()
                    return true
                }
            }
        }
        val cartItem = Cart(product, 1)
        cartItemList.add(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
        return true
    }

    fun removeItemFromCart(cartItem: Cart?) {
        if (mutableCart.value == null) {
            return
        }
        val cartItemList: MutableList<Cart> = ArrayList(mutableCart.value)
        cartItemList.remove(cartItem)
        mutableCart.setValue(cartItemList)
        calculateCartTotal()
    }

    fun changeQuantity(cartItem: Cart?, quantity: Int) {
        if (mutableCart.value == null) return
        val cartItemList: MutableList<Cart> = ArrayList(mutableCart.value)
        val updatedItem = Cart(cartItem!!.product, quantity)
        cartItemList[cartItemList.indexOf(cartItem)] = updatedItem
        mutableCart.value = cartItemList
        calculateCartTotal()
    }

    private fun calculateCartTotal() {
        if (mutableCart.value == null) return
        var total = 0.0
        val cartItemList = mutableCart.value
        for (cartItem in cartItemList!!) {
            total += cartItem.product!!.price * cartItem.quantity
        }
        mutableTotalPrice.value = total
    }

    val totalPrice: LiveData<Double?>
        get() {
            if (mutableTotalPrice.value == null) {
                mutableTotalPrice.value = 0.0
            }
            return mutableTotalPrice
        }
}

