package com.mesada.shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesada.shop.databinding.FragmentOrderconfirmBinding;
import com.mesada.shop.viewmodel.ShopViewModel;

public class OrderConfirmFragment extends Fragment {

    NavController navController;
    FragmentOrderconfirmBinding fragmentOrderconfirmBinding;
    ShopViewModel shopViewModel;

    public OrderConfirmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentOrderconfirmBinding = FragmentOrderconfirmBinding.inflate(inflater, container, false);
        return fragmentOrderconfirmBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        fragmentOrderconfirmBinding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_orderConfirmFragment_to_productlistFragment);
            }
        });
    }
}
