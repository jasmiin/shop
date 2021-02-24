package com.mesada.shop;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.snackbar.Snackbar;
import com.mesada.shop.databinding.FragmentCheckoutBinding;
import com.mesada.shop.model.Cart;
import com.mesada.shop.viewmodel.ShopViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;
import static com.mesada.shop.BR.cartItem;
import static com.mesada.shop.helper.RandomNumber.generateID;

public class CheckoutFragment extends Fragment {

    NavController navController;
    FragmentCheckoutBinding fragmentCheckoutBinding;
    ShopViewModel shopViewModel;
    boolean isAllFieldsChecked = false;
    private static FileWriter file;
    long randomNumber= generateID();
    SendMessage SM;

    public CheckoutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCheckoutBinding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return fragmentCheckoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        fragmentCheckoutBinding.switchAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fragmentCheckoutBinding.payNowButton.setEnabled(true);
                    fragmentCheckoutBinding.payNowButton.setBackgroundColor(Color.parseColor("#0000FF"));
                } else {
                    fragmentCheckoutBinding.payNowButton.setEnabled(false);
                    fragmentCheckoutBinding.payNowButton.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            }
        });
        fragmentCheckoutBinding.payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    List<Cart> cart = shopViewModel.getCart().getValue();
                    int quantity = 0;
                    JSONArray jsonArray = new JSONArray();
                    for (Cart cartItem : cart) {
                        quantity += cartItem.getQuantity();
                        JSONObject object = null;
                        JSONObject customerDetails = new JSONObject();;
                        try {
                            customerDetails.put("name", fragmentCheckoutBinding.editTextTextPersonName.getText());
                            customerDetails.put("email", fragmentCheckoutBinding.editTextTextEmailAddress.getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(customerDetails);
                        for(int i=0; i<cartItem.getQuantity(); i++) {
                            object = new JSONObject();
                            try {
                                object.put("productid", cartItem.getProduct().getId());
                                object.put("productname", cartItem.getProduct().getName());
                                object.put("productcategory", cartItem.getProduct().getCategory());
                                object.put("productprice", cartItem.getProduct().getPrice());
                                object.put("productbgColor", cartItem.getProduct().getBgColor());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                                jsonArray.put(object);
                            }
                    }
                    JSONObject shopObj = new JSONObject();
                    try {
                        shopObj.put(fragmentCheckoutBinding.editTextTextPersonName.getText().toString(), jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String jsonStr = shopObj.toString();
                    save(jsonStr);
                    shopViewModel.resetCart();
                    navController.navigate(R.id.action_checkoutFragment_to_orderConfirmFragment);
                }
            }
        });
    }

    interface SendMessage {
        void sendData(long message);
    }

    private boolean CheckAllFields() {
        if (fragmentCheckoutBinding.editTextTextPersonName.length() == 0) {
            fragmentCheckoutBinding.editTextTextPersonName.setError("Name is required");
            return false;
        }

        if (fragmentCheckoutBinding.editTextTextEmailAddress.length() == 0) {
            fragmentCheckoutBinding.editTextTextEmailAddress.setError("email is required");
            return false;
        } else if (!isEmailValid(fragmentCheckoutBinding.editTextTextEmailAddress.getText().toString())) {
            fragmentCheckoutBinding.editTextTextEmailAddress.setError("This field must be email format");
            return false;
        }

        return true;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void save(String string) {
        String FILE_NAME = "order_"+randomNumber+".text";
        String list = string;
        FileOutputStream fos = null;
        try {
            fos = getContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(list.getBytes());
            Snackbar.make(requireView(), "Your order number is #"+randomNumber, Snackbar.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
