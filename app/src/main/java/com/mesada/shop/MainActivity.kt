package com.mesada.shop

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mesada.shop.viewmodel.ShopViewModel

class MainActivity : AppCompatActivity() {
    var navController: NavController? = null
    var shopViewModel: ShopViewModel? = null
    private var cartQuantity = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController!!)
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        shopViewModel!!.cart.observe(this, Observer { cart ->
            var quantity = 0
            for (cartItem in cart!!) {
                quantity += cartItem.quantity
            }
            cartQuantity = quantity
            invalidateOptionsMenu()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        navController!!.navigateUp()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.cartFragment)
        val actionView = menuItem.actionView
        val cartBadgeTextView = actionView.findViewById<TextView>(R.id.cart_badge_text_view)
        cartBadgeTextView.text = cartQuantity.toString()
        cartBadgeTextView.visibility = if (cartQuantity == 0) View.GONE else View.VISIBLE
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController!!) || super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}