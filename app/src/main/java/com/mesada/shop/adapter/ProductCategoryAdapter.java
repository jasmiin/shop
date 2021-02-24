package com.mesada.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mesada.shop.ProductListFragment;
import com.mesada.shop.R;
import com.mesada.shop.databinding.CategoryRowItemBinding;
import com.mesada.shop.model.Product;
import com.mesada.shop.model.ProductCategory;

import static androidx.databinding.library.baseAdapters.BR.categoryInterface;

public class ProductCategoryAdapter extends ListAdapter<ProductCategory, ProductCategoryAdapter.CategoryViewHolder> {

    Context context;
    CategoryInterface categoryInterface;
    int row_index;

    public ProductCategoryAdapter(Context context, CategoryInterface categoryInterface) {
        super(ProductCategory.itemCallback);
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryRowItemBinding categoryRowItemBinding = CategoryRowItemBinding.inflate(layoutInflater, parent, false);
        categoryRowItemBinding.setCategoryInterface(categoryInterface);
        return new CategoryViewHolder(categoryRowItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        ProductCategory category = getItem(position);
        holder.categoryRowItemBinding.setProductcategory(category);
        holder.categoryRowItemBinding.executePendingBindings();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        CategoryRowItemBinding categoryRowItemBinding;

        public CategoryViewHolder(CategoryRowItemBinding binding) {
            super(binding.getRoot());
            this.categoryRowItemBinding = binding;
        }
    }

    public interface CategoryInterface {
        void onItemClick(ProductCategory productCategory);
    }
}