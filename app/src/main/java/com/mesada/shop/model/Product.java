package com.mesada.shop.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.mesada.shop.R;

import java.util.Objects;

public class Product {
    private String productid;
    private String productName;
    private String productCategory;
    private double productPrice;
    private String bgColor;

    public Product(String productid, String productName, String productCategory, double productPrice, String bgColor) {
        this.productid = productid;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.bgColor = bgColor;
    }

    public String getId() {
        return productid;
    }

    public void setId(String productid) {
        this.productid = productid;
    }

    public String getName() {
        return productName;
    }

    public void setName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return productPrice;
    }

    public void setPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getCategory() {
        return productCategory;
    }

    public void setCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getImageUrl() {
        {
            switch (productid)
            {
                case "p_1":
                    return R.drawable.p_1;
                case "p_2":
                    return R.drawable.p_2;
                case "p_3":
                    return R.drawable.p_3;
                case "p_4":
                    return R.drawable.p_4;
                case "p_5":
                    return R.drawable.p_5;
            }
        }
        return R.drawable.ic_launcher_background;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 &&
                getCategory().equals(product.getCategory()) &&
                getId().equals(product.getId()) &&
                getName().equals(product.getName()) &&
                getBgColor().equals(product.getBgColor());
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };


    @BindingAdapter("android:productImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .fitCenter()
                .into(imageView);
    }
}