package com.mesada.shop.adapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mesada.shop.databinding.ProductRowBinding;
import com.mesada.shop.model.Product;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ShopViewHolder> implements Filterable {

    ShopInterface shopInterface;
    public ProductAdapter(ShopInterface shopInterface) {
        super(Product.itemCallback);
        this.shopInterface = shopInterface;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductRowBinding productRowBinding = ProductRowBinding.inflate(layoutInflater, parent, false);
        productRowBinding.setShopInterface(shopInterface);
        return new ShopViewHolder(productRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Product product = getItem(position);
        holder.productRowBinding.setProduct(product);
        holder.productRowBinding.imageBackground.setBackgroundColor(Color.parseColor(product.getBgColor()));
        holder.productRowBinding.productImageView.setImageResource(product.getImageUrl());
        holder.productRowBinding.executePendingBindings();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        ProductRowBinding productRowBinding;

        public ShopViewHolder(ProductRowBinding binding) {
            super(binding.getRoot());
            this.productRowBinding = binding;
        }
    }



    public interface ShopInterface {
        void addItem(Product product);
    }
}
