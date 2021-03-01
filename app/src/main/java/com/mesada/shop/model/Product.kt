package com.mesada.shop.model

import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mesada.shop.R

class Product(var id: String, var name: String, var category: String, var price: Double, var bgColor: String) {

    val imageUrl: Int
        get() {
            run {
                when (this.id) {
                    "p_1" -> return R.drawable.pic_1
                    "p_2" -> return R.drawable.pic_2
                    "p_3" -> return R.drawable.pic_3
                    "p_4" -> return R.drawable.pic_4
                    "p_5" -> return R.drawable.pic_5
                    else -> return R.drawable.pic_5
                }
            }
            return R.drawable.ic_launcher_background
        }

    val imageResourceBg: Int
        get() {
            run {
                when (id) {
                    "p_1" -> return R.drawable.p_1
                    "p_2" -> return R.drawable.p_2
                    "p_3" -> return R.drawable.p_3
                    "p_4" -> return R.drawable.p_1
                    "p_5" -> return R.drawable.p_2
                    else -> return R.drawable.p_3
                }
            }
            return R.drawable.ic_launcher_background
        }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val product = o as Product
        return java.lang.Double.compare(product.price, price) == 0 && category == product.category && id == product.id && name == product.name && bgColor == product.bgColor
    }

    companion object {
        @JvmField
        var itemCallback: DiffUtil.ItemCallback<Product> = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }

        fun loadImage(imageView: ImageView?, imageUrl: String?) {
            Glide.with(imageView!!)
                    .load(imageUrl)
                    .fitCenter()
                    .into(imageView)
        }
    }

}