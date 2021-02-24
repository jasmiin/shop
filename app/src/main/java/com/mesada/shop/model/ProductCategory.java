package com.mesada.shop.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ProductCategory {

    Integer productId;
    String productCategory;

    public ProductCategory(Integer productId, String productCategory) {
        this.productId = productId;
        this.productCategory = productCategory;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public static DiffUtil.ItemCallback<ProductCategory> itemCallback = new DiffUtil.ItemCallback<ProductCategory>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductCategory oldItem, @NonNull ProductCategory newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductCategory oldItem, @NonNull ProductCategory newItem) {
            return true;
        }
    };
}