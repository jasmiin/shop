package com.mesada.shop.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mesada.shop.databinding.CartRowBinding;
import com.mesada.shop.model.Cart;

import static androidx.databinding.library.baseAdapters.BR.cartItem;
import static androidx.databinding.library.baseAdapters.BR.categoryInterface;

public class CartAdapter extends ListAdapter<Cart, CartAdapter.CartViewHolder> {

    private CartInterface cartInterface;
    public CartAdapter(CartInterface cartInterface) {
        super(Cart.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartRowBinding cartRowBinding = CartRowBinding.inflate(layoutInflater, parent, false);
        return new CartViewHolder(cartRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.cartRowBinding.setCartItem(getItem(position));
        holder.cartRowBinding.cartBackground.setBackgroundColor(Color.parseColor(getItem(position).getProduct().getBgColor()));
        holder.cartRowBinding.executePendingBindings();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        CartRowBinding cartRowBinding;
        public CartViewHolder(@NonNull CartRowBinding cartRowBinding) {
            super(cartRowBinding.getRoot());
            this.cartRowBinding = cartRowBinding;

            cartRowBinding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });

        }
    }

    public interface CartInterface {
        void deleteItem(Cart cartItem);
        void changeQuantity(Cart cartItem, int quantity);
    }
}