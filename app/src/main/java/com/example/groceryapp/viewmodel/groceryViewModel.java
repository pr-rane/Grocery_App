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
    public LiveData<List<Category>> getCategories(){
        if(categoryList==null)
        categoryList = groceryRepo.getCategories();
        return categoryList;
    }

    public LiveData<List<CartItem>> getCart(){
        cartList = cartRepo.getCart();
        return cartList;
    }


    public boolean addProductToCart(Product product){
        addToProductList(product);
        return cartRepo.addItemToCart(product);
    }

    public void addToProductList(Product product){
        groceryRepo.addProductQty(product);
    }
    public void FillPreviousCart(){
        if(categoryList!=null&&categoryList.getValue().size()>0){
            cartRepo.FillPreviousCart(categoryList);
        }
    }

    public boolean deleteProductFromCart(Product product){
        deleteFromCart(product);
        return cartRepo.deleteProductFromCart(product);
    }

    private void deleteFromCart(Product product) {
        groceryRepo.deleteProductQty(product);
    }

    public boolean removeItemFromCart(Product product){
        removeFromProductList(product);
        return cartRepo.removeItemFromCart(product);
    }

    public void removeFromProductList(Product product){
        groceryRepo.removeProductQty(product);
    }


}
