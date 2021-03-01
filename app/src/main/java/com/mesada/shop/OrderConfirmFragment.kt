package com.mesada.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mesada.shop.databinding.FragmentOrderconfirmBinding
import com.mesada.shop.viewmodel.ShopViewModel

class OrderConfirmFragment : Fragment() {
    var navController: NavController? = null
    var fragmentOrderconfirmBinding: FragmentOrderconfirmBinding? = null
    var shopViewModel: ShopViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOrderconfirmBinding = FragmentOrderconfirmBinding.inflate(inflater, container, false)
        return fragmentOrderconfirmBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        fragmentOrderconfirmBinding!!.btnReturn.setOnClickListener { navController!!.navigate(R.id.action_orderConfirmFragment_to_productlistFragment) }
    }
}