package com.mesada.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mesada.shop.adapter.ProductAdapter
import com.mesada.shop.adapter.ProductAdapter.ShopInterface
import com.mesada.shop.adapter.ProductCategoryAdapter
import com.mesada.shop.adapter.ProductCategoryAdapter.CategoryInterface
import com.mesada.shop.databinding.FragmentProductListBinding
import com.mesada.shop.model.Product
import com.mesada.shop.model.ProductCategory
import com.mesada.shop.viewmodel.ShopViewModel

class ProductListFragment : Fragment(), ShopInterface, CategoryInterface {
    var fragmentProductListBinding: FragmentProductListBinding? = null
    private var productAdapter: ProductAdapter? = null
    private var productCategoryAdapter: ProductCategoryAdapter? = null
    private var shopViewModel: ShopViewModel? = null
    private var navController: NavController? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater, container, false)
        return fragmentProductListBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductAdapter(this)
        productCategoryAdapter = context?.let { ProductCategoryAdapter(it, this) }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        fragmentProductListBinding!!.categoryRecyclerView.layoutManager = layoutManager
        fragmentProductListBinding!!.shopRecyclerView.adapter = productAdapter
        fragmentProductListBinding!!.categoryRecyclerView.adapter = productCategoryAdapter
        fragmentProductListBinding!!.shopRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        fragmentProductListBinding!!.shopRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        shopViewModel!!.filter(context, shopViewModel!!.category.value)!!.observe(viewLifecycleOwner, Observer { products -> productAdapter!!.submitList(products) })
        shopViewModel!!.setCategory(shopViewModel!!.category.value!!)
        shopViewModel!!.category.observe(viewLifecycleOwner, Observer { category -> productCategoryAdapter!!.setCategory(category) })
        shopViewModel!!.productCategory.observe(viewLifecycleOwner, Observer { category -> productCategoryAdapter!!.submitList(category) })
        navController = Navigation.findNavController(view)
    }

    override fun addItem(product: Product?) {
        val isAdded = shopViewModel!!.addItemToCart(product)
        val snackBar = Snackbar.make(requireView(), product!!.name + " has been added to cart.", Snackbar.LENGTH_LONG)
        if (isAdded) {
            snackBar.setAction("Dismiss") { snackBar.dismiss() }
            snackBar.show()
        } else {
            Snackbar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    override fun onItemClick(productCategory: ProductCategory?) {
        shopViewModel!!.filter(context, productCategory!!.productCategory)!!.observe(viewLifecycleOwner, Observer { products -> productAdapter!!.submitList(products) })
        shopViewModel!!.setCategory(productCategory.productCategory)
        shopViewModel!!.category.observe(viewLifecycleOwner, Observer { category -> productCategoryAdapter!!.setCategory(category) })
        fragmentProductListBinding!!.categoryRecyclerView.adapter = productCategoryAdapter
    }
}