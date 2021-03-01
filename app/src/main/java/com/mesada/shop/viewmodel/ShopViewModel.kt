package com.mesada.shop.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mesada.shop.model.Cart
import com.mesada.shop.model.Product
import com.mesada.shop.model.ProductCategory
import com.mesada.shop.repository.CartRepository
import com.mesada.shop.repository.CategoryRepository
import com.mesada.shop.repository.ProductRepository

class ShopViewModel : ViewModel() {
    var productRepo = ProductRepository()
    var cartRepo = CartRepository()
    var categoryRepo = CategoryRepository()
    var mutableProduct = MutableLiveData<Product>()
//    fun getProducts(context: Context?): LiveData<List<Product>>? {
//        return productRepo.getProducts(context)
//    }

//    fun setProduct(product: Product) {
//        mutableProduct.value = product
//    }

    val product: LiveData<Product>
        get() = mutableProduct

    val productCategory: LiveData<List<ProductCategory>>
        get() = categoryRepo.productCategory

    val cart: LiveData<List<Cart>?>
        get() = cartRepo.cart

    fun addItemToCart(product: Product?): Boolean {
        return cartRepo.addItemToCart(product)
    }

    fun removeItemFromCart(cartItem: Cart?) {
        cartRepo.removeItemFromCart(cartItem)
    }

    fun changeQuantity(cartItem: Cart?, quantity: Int) {
        cartRepo.changeQuantity(cartItem, quantity)
    }

    val totalPrice: LiveData<Double?>
        get() = cartRepo.totalPrice

    fun resetCart() {
        cartRepo.initCart()
    }

    fun filter(context: Context?, charString: String?): LiveData<List<Product>?>? {
        return productRepo.byCategory(context!!,charString!!)
    }

    fun setCategory(category: String) {
        categoryRepo.selectCategory(category)
}

    val category: LiveData<String?>
        get() = categoryRepo.SelectedCategory

}