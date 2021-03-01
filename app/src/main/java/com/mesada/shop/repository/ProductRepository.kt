package com.mesada.shop.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mesada.shop.model.Product
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*


class ProductRepository {
    private var mutableProductList: MutableLiveData<List<Product>>? = null
    private val filteredProduct = MutableLiveData<List<Product>?>()

    fun byCategory(context: Context?, charString: String): LiveData<List<Product>?>? {
        mutableProductList = MutableLiveData()
        loadProducts(context)

        val productList = mutableProductList!!.value
        val filtered: MutableList<Product> = ArrayList()
        if (productList != null) {
            for (product in productList) {
                if (charString.toLowerCase().contains("all")) {
                    filtered.add(product)
                } else if (product.category.toLowerCase().contains(charString.toLowerCase())) {
                    filtered.add(product)
                }
            }
            filteredProduct.value = filtered
            return filteredProduct
        }
        filteredProduct.value = filtered
        return filteredProduct
    }

    private fun loadProducts(context: Context?): Unit? {
        val productList: MutableList<Product> = ArrayList()
        try {
            val obj = JSONObject(loadJSONFromAsset(context!!))
            val userArray = obj.getJSONArray("products")
            for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)
                productList.add(Product(userDetail.getString("id"), userDetail.getString("name"), userDetail.getString("category"), userDetail.getInt("price").toDouble(), userDetail.getString("bgColor")))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return mutableProductList?.setValue(productList);
    }

    fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val `is` = context.assets.open("products.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}