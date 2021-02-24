package com.mesada.shop;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mesada.shop.R;
import com.mesada.shop.adapter.CartAdapter;
import com.mesada.shop.databinding.FragmentCartBinding;
import com.mesada.shop.model.Cart;
import com.mesada.shop.viewmodel.ShopViewModel;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartInterface {

    private static final String TAG = "CartFragment";
    ShopViewModel shopViewModel;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        final CartAdapter cartListAdapter = new CartAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartItems) {
                cartListAdapter.submitList(cartItems);
                if (cartItems.size() > 0) {
                    fragmentCartBinding.buyNowButton.setBackgroundColor(Color.parseColor("#000000"));
                } else {
                    fragmentCartBinding.buyNowButton.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            }
        });

        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                fragmentCartBinding.orderTotalTextView.setText("$" + aDouble.toString());
            }
        });

        fragmentCartBinding.buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopViewModel.getTotalPrice().getValue() == 0.0) {
                    Snackbar.make(requireView(), "Please add item to your cart.", Snackbar.LENGTH_LONG).show();
                } else {
                    navController.navigate(R.id.action_cartFragment_to_checkoutFragment);
                }
            }
        });
    }

    @Override
    public void deleteItem(Cart cartItem) {
        shopViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(Cart cartItem, int quantity) {
        shopViewModel.changeQuantity(cartItem, quantity);
    }
}