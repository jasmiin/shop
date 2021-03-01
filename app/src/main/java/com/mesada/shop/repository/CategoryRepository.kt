package com.mesada.shop.repository

import androidx.lifecycle.MutableLiveData
import com.mesada.shop.model.ProductCategory
import androidx.lifecycle.LiveData
import com.mesada.shop.model.Cart
import java.util.*

class CategoryRepository {
    private var mutableProductCategory: MutableLiveData<List<ProductCategory>>? = null
    private val mutableCategory = MutableLiveData<String?>()

    val productCategory: LiveData<List<ProductCategory>>
        get() {
            if (mutableProductCategory == null) {
                mutableProductCategory = MutableLiveData()
                loadProductsCategory()
            }
            return mutableProductCategory!!
        }

    private fun loadProductsCategory() {
        val productCategoryList: MutableList<ProductCategory> = ArrayList()
        productCategoryList.add(ProductCategory(1, "All"))
        productCategoryList.add(ProductCategory(2, "Jacket"))
        productCategoryList.add(ProductCategory(3, "Blazer"))
        productCategoryList.add(ProductCategory(4, "Tee"))
        mutableProductCategory!!.value = productCategoryList
    }

    val SelectedCategory: LiveData<String?>
        get() {
            if (mutableCategory.value == null) {
                mutableCategory.value = "all"
            }
            return mutableCategory
        }

    fun selectCategory(category: String?) {
        if (mutableCategory.value == null) return
        mutableCategory.value = category
    }
}