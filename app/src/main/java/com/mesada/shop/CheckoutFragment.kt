package com.mesada.shop

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mesada.shop.databinding.FragmentCheckoutBinding
import com.mesada.shop.helper.RandomNumber
import com.mesada.shop.viewmodel.ShopViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.util.regex.Pattern

class CheckoutFragment : Fragment() {
    var navController: NavController? = null
    var fragmentCheckoutBinding: FragmentCheckoutBinding? = null
    var shopViewModel: ShopViewModel? = null
    var isAllFieldsChecked = false
    var randomNumber = RandomNumber.generateID()
    var SM: SendMessage? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCheckoutBinding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return fragmentCheckoutBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        fragmentCheckoutBinding!!.switchAgree.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fragmentCheckoutBinding!!.payNowButton.isEnabled = true
                fragmentCheckoutBinding!!.payNowButton.setBackgroundColor(Color.parseColor("#0000FF"))
            } else {
                fragmentCheckoutBinding!!.payNowButton.isEnabled = false
                fragmentCheckoutBinding!!.payNowButton.setBackgroundColor(Color.parseColor("#DCDCDC"))
            }
        }
        fragmentCheckoutBinding!!.payNowButton.setOnClickListener {
            isAllFieldsChecked = CheckAllFields()
            if (isAllFieldsChecked) {
                val cart = shopViewModel!!.cart.value!!
                var quantity = 0
                val jsonArray = JSONArray()
                for (cartItem in cart) {
                    quantity += cartItem.quantity
                    var `object`: JSONObject? = null
                    val customerDetails = JSONObject()
                    try {
                        customerDetails.put("name", fragmentCheckoutBinding!!.editTextTextPersonName.text)
                        customerDetails.put("email", fragmentCheckoutBinding!!.editTextTextEmailAddress.text)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    jsonArray.put(customerDetails)
                    for (i in 0 until cartItem.quantity) {
                        `object` = JSONObject()
                        try {
                            `object`.put("productid", cartItem.product!!.id)
                            `object`.put("productname", cartItem.product!!.name)
                            `object`.put("productcategory", cartItem.product!!.category)
                            `object`.put("productprice", cartItem.product!!.price)
                            `object`.put("productbgColor", cartItem.product!!.bgColor)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        jsonArray.put(`object`)
                    }
                }
                val shopObj = JSONObject()
                try {
                    shopObj.put(fragmentCheckoutBinding!!.editTextTextPersonName.text.toString(), jsonArray)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                val jsonStr = shopObj.toString()
                save(jsonStr)
                shopViewModel!!.resetCart()
                navController!!.navigate(R.id.action_checkoutFragment_to_orderConfirmFragment)
            }
        }
    }

    interface SendMessage {
        fun sendData(message: Long)
    }

    private fun CheckAllFields(): Boolean {
        if (fragmentCheckoutBinding!!.editTextTextPersonName.length() == 0) {
            fragmentCheckoutBinding!!.editTextTextPersonName.error = "Name is required"
            return false
        }
        if (fragmentCheckoutBinding!!.editTextTextEmailAddress.length() == 0) {
            fragmentCheckoutBinding!!.editTextTextEmailAddress.error = "email is required"
            return false
        } else if (!isEmailValid(fragmentCheckoutBinding!!.editTextTextEmailAddress.text.toString())) {
            fragmentCheckoutBinding!!.editTextTextEmailAddress.error = "This field must be email format"
            return false
        }
        return true
    }

    fun save(string: String) {
        val FILE_NAME = "order_$randomNumber.text"
        var fos: FileOutputStream? = null
        try {
            fos = requireContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fos.write(string.toByteArray())
            Snackbar.make(requireView(), "Your order number is #$randomNumber", Snackbar.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private val file: FileWriter? = null
        fun isEmailValid(email: String?): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}