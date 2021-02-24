package com.mesada.shop.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mesada.shop.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private MutableLiveData<List<Product>> mutableProductList;
    private MutableLiveData<List<Product>> filteredProduct = new MutableLiveData<>();;

    public LiveData<List<Product>> getProducts(Context context) {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts(context);
        }
        return mutableProductList;
    }

    private void loadProducts(Context context) {
        List<Product> productList = new ArrayList<>();
       try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context));
            JSONArray userArray = obj.getJSONArray("products");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);
                productList.add(new Product(userDetail.getString("id"),userDetail.getString("name"),userDetail.getString("category"), userDetail.getInt("price"), userDetail.getString("bgColor")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mutableProductList.setValue(productList);
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("products.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

        public LiveData<List<Product>> byCategory(String charString) {
        if (mutableProductList.getValue() == null) return mutableProductList;
            List<Product> productList = mutableProductList.getValue();
            List<Product> filtered = new ArrayList<>();
            for (Product product: productList) {
                if ((charString.toLowerCase()).contains("all")) {
                    filtered.add(product);
                } else if (product.getCategory().toLowerCase().contains(charString.toLowerCase())) {
                    filtered.add(product);
                }
        }

        filteredProduct.setValue(filtered);
        return filteredProduct;
    }
}