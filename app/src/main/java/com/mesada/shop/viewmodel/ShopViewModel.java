package com.mesada.shop.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mesada.shop.model.Cart;
import com.mesada.shop.model.Product;
import com.mesada.shop.model.ProductCategory;
import com.mesada.shop.repository.CartRepository;
import com.mesada.shop.repository.CategoryRepository;
import com.mesada.shop.repository.ProductRepository;

import java.util.List;

public class ShopViewModel extends ViewModel {

    ProductRepository productRepo = new ProductRepository();
    CartRepository cartRepo = new CartRepository();
    CategoryRepository categoryRepo = new CategoryRepository();

    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();
    long ordernumber;

    public LiveData<List<Product>> getProducts(Context context) {
        return productRepo.getProducts(context);
    }

    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public LiveData<Product> getProduct() {
        return mutableProduct;
    }

    public LiveData<List<ProductCategory>> getProductCategory() {
        return categoryRepo.getProductCategory();
    }

    public LiveData<List<Cart>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Product product) {
        return cartRepo.addItemToCart(product);
    }

    public void removeItemFromCart(Cart cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(Cart cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }

    public  LiveData<List<Product>> filter(String charString) {
        return productRepo.byCategory(charString);
    }

}
