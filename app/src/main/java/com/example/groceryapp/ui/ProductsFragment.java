package com.example.groceryapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.groceryapp.adapters.ProductListAdapter;
import com.example.groceryapp.databinding.FragmentProductsBinding;
import com.example.groceryapp.model.CartItem;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment implements ProductListAdapter.ProductInterface {

    private List<Product> allProducts;
    private Category categoryObject;
    private ProductListAdapter productListAdapter;

    public ProductsFragment() {
        // Required empty public constructor
    }


    public ProductsFragment(Category category) {
        categoryObject = category;
    }
    FragmentProductsBinding productsBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        productsBinding = FragmentProductsBinding.inflate(inflater,container,false);
        return productsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productListAdapter = new ProductListAdapter(this);
        productsBinding.productRecycler.setAdapter(productListAdapter);
        productsBinding.productRecycler.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        if(categoryObject!=null)
        {
            allProducts = new ArrayList<>();
            allProducts.clear();
            allProducts.addAll(categoryObject.getAllProducts());
            productListAdapter.submitList(allProducts);
        }

    }

    @Override
    public void addItem(Product product) {
        if(HomeFragment.groceryViewModel.addProductToCart(product)){
            Toast.makeText(requireContext(), "isadded", Toast.LENGTH_SHORT).show();
            productListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void removeItem(Product product) {
        CartItem cartItem = new CartItem(product,0);
        if(HomeFragment.groceryViewModel.removeItemFromCart(cartItem)){
            Toast.makeText(requireContext(), "isremove", Toast.LENGTH_SHORT).show();
        }
    }
}