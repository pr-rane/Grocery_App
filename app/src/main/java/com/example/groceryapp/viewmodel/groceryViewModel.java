package com.example.groceryapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.groceryapp.model.CartItem;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Product;
import com.example.groceryapp.repository.cartRepo;
import com.example.groceryapp.repository.groceryRepo;

import java.util.List;

public class groceryViewModel extends ViewModel {

    groceryRepo groceryRepo = new groceryRepo();
    cartRepo cartRepo = new cartRepo();

    LiveData<List<Category>> categoryList;
    LiveData<List<CartItem>> cartList;
    public LiveData<List<Category>> getCategories(Context context){
        if(categoryList==null)
        categoryList = groceryRepo.getCategories(context);
        return categoryList;
    }

    public LiveData<List<CartItem>> getCart(){
        cartList = cartRepo.getCart();
        return cartList;
    }


    public boolean addProductToCart(Product product){
        return cartRepo.addItemToCart(product);
    }

    public void FillPreviousCart(){
        if(categoryList.getValue()!=null&&categoryList.getValue().size()>0){
            cartRepo.FillPreviousCart(categoryList);
        }
    }

    public boolean removeItemFromCart(CartItem cartItem){
        deleteFromProductList(cartItem);
        return cartRepo.removeItemFromCart(cartItem);
    }

    public void deleteFromProductList(CartItem cartItem){
        Product product = cartItem.getProduct();
        groceryRepo.deleteProductQty(product);
    }


}
