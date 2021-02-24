package com.mesada.shop.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mesada.shop.model.Product;
import com.mesada.shop.model.ProductCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private MutableLiveData<List<ProductCategory>> mutableProductCategory;

    public LiveData<List<ProductCategory>> getProductCategory() {
        if (mutableProductCategory == null) {
            mutableProductCategory = new MutableLiveData<>();
            loadProductsCategory();
        }
        return mutableProductCategory;
    }

    private void loadProductsCategory() {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "All"));
        productCategoryList.add(new ProductCategory(2, "Jacket"));
        productCategoryList.add(new ProductCategory(3, "Blazer"));
        productCategoryList.add(new ProductCategory(4, "Tee"));

        mutableProductCategory.setValue(productCategoryList);
    }
}