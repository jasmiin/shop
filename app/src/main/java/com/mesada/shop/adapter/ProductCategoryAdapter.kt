package com.mesada.shop.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mesada.shop.ProductListFragment
import com.mesada.shop.R
import com.mesada.shop.adapter.ProductCategoryAdapter.CategoryViewHolder
import com.mesada.shop.databinding.CategoryRowItemBinding
import com.mesada.shop.model.ProductCategory

class ProductCategoryAdapter(var context: Context, var categoryInterface: CategoryInterface) : ListAdapter<ProductCategory, CategoryViewHolder>(ProductCategory.itemCallback) {
    var bgColorCategory = "all"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryRowItemBinding = CategoryRowItemBinding.inflate(layoutInflater, parent, false)
        categoryRowItemBinding.categoryInterface = categoryInterface
        return CategoryViewHolder(categoryRowItemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.categoryRowItemBinding.productcategory = category
        holder.categoryRowItemBinding.executePendingBindings()
        if (bgColorCategory.toLowerCase() == category.productCategory.toLowerCase()) {
            holder.categoryRowItemBinding.catName.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.category_selected))
        } else {
            holder.categoryRowItemBinding.catName.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.category))
        }
    }

    inner class CategoryViewHolder(var categoryRowItemBinding: CategoryRowItemBinding) : RecyclerView.ViewHolder(categoryRowItemBinding.root)

    interface CategoryInterface {
        fun onItemClick(productCategory: ProductCategory?)
    }

    fun setCategory (bgCategory : String?) {
        bgColorCategory = bgCategory.toString()
    }

}