package com.mesada.shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesada.shop.R;
import com.mesada.shop.adapter.ProductAdapter;
import com.mesada.shop.adapter.ProductCategoryAdapter;
import com.mesada.shop.databinding.CategoryRowItemBinding;
import com.mesada.shop.databinding.FragmentProductListBinding;
import com.mesada.shop.model.Product;
import com.mesada.shop.model.ProductCategory;
import com.mesada.shop.viewmodel.ShopViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductListFragment extends Fragment implements ProductAdapter.ShopInterface, ProductCategoryAdapter.CategoryInterface {

    FragmentProductListBinding fragmentProductListBinding;
    private ProductAdapter productAdapter;
    private ProductCategoryAdapter productCategoryAdapter;
    private ShopViewModel shopViewModel;
    private NavController navController;

    public ProductListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater, container, false);
        return fragmentProductListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productAdapter = new ProductAdapter(this);
        productCategoryAdapter = new ProductCategoryAdapter(getContext(), this);
        fragmentProductListBinding.categoryRecyclerView.setAdapter(productCategoryAdapter);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        fragmentProductListBinding.categoryRecyclerView.setLayoutManager(layoutManager);
        fragmentProductListBinding.shopRecyclerView.setAdapter(productAdapter);
        fragmentProductListBinding.shopRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentProductListBinding.shopRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getProducts(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.submitList(products);
            }
        });
        shopViewModel.getProductCategory().observe(getViewLifecycleOwner(), new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(List<ProductCategory> category) {
                productCategoryAdapter.submitList(category);
            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Product product) {
        boolean isAdded = shopViewModel.addItemToCart(product);
        final Snackbar snackBar = Snackbar.make(requireView(), product.getName() + " has been added to cart.", Snackbar.LENGTH_LONG);
        if (isAdded) {
            snackBar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            });
            snackBar.show();
        } else {
            snackBar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemClick(ProductCategory productCategory) {
        shopViewModel.filter(productCategory.getProductCategory()).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.submitList(products);
            }
        });
    }
}