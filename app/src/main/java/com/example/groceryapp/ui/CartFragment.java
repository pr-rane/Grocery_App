package com.example.groceryapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.groceryapp.adapters.CartAdapter;
import com.example.groceryapp.databinding.FragmentCartBinding;
import com.example.groceryapp.model.CartItem;
import com.example.groceryapp.viewmodel.groceryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartInterface {

    groceryViewModel groceryViewModel;
    public CartFragment() {
        // Required empty public constructor
    }

    FragmentCartBinding cartBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartBinding = FragmentCartBinding.inflate(inflater,container,false);
        return cartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CartAdapter cartAdapter = new CartAdapter(this);
        cartBinding.cartRecycler.setAdapter(cartAdapter);
        cartBinding.cartRecycler.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));

        groceryViewModel = new ViewModelProvider(requireActivity()).get(groceryViewModel.class);
        groceryViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                Log.e("CartFragment",String.valueOf(cartItems.size()));
                cartAdapter.submitList(cartItems);
            }
        });


    }

    @Override
    public void deleteItem(CartItem cartItem) {
        groceryViewModel.removeItemFromCart(cartItem);
    }
}