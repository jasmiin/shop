package com.mesada.shop

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.mesada.shop.adapter.CartAdapter
import com.mesada.shop.adapter.CartAdapter.CartInterface
import com.mesada.shop.databinding.FragmentCartBinding
import com.mesada.shop.model.Cart
import com.mesada.shop.viewmodel.ShopViewModel

class CartFragment : Fragment(), CartInterface {
    var shopViewModel: ShopViewModel? = null
    var fragmentCartBinding: FragmentCartBinding? = null
    var navController: NavController? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)
        return fragmentCartBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val cartListAdapter = CartAdapter(this)
        fragmentCartBinding!!.cartRecyclerView.adapter = cartListAdapter
        fragmentCartBinding!!.cartRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        shopViewModel!!.cart.observe(viewLifecycleOwner, Observer { cartItems ->
            cartListAdapter.submitList(cartItems)
            if (cartItems!!.size > 0) {
                fragmentCartBinding!!.buyNowButton.setBackgroundColor(Color.parseColor("#000000"))
            } else {
                fragmentCartBinding!!.buyNowButton.setBackgroundColor(Color.parseColor("#DCDCDC"))
            }
        })
        shopViewModel!!.totalPrice.observe(viewLifecycleOwner, Observer { aDouble -> fragmentCartBinding!!.orderTotalTextView.text = "$$aDouble" })
        fragmentCartBinding!!.buyNowButton.setOnClickListener {
            if (shopViewModel!!.totalPrice.value == 0.0) {
                Snackbar.make(requireView(), "Please add item to your cart.", Snackbar.LENGTH_LONG).show()
            } else {
                navController!!.navigate(R.id.action_cartFragment_to_checkoutFragment)
            }
        }
    }

    override fun deleteItem(cartItem: Cart?) {
        shopViewModel!!.removeItemFromCart(cartItem)
    }

    override fun changeQuantity(cartItem: Cart?, quantity: Int) {
        shopViewModel!!.changeQuantity(cartItem, quantity)
    }

    companion object {
        private const val TAG = "CartFragment"
    }
}