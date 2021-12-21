package com.example.groceryapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.groceryapp.model.CartItem;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cartRepo {

    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    
    
    public LiveData<List<CartItem>> getCart(){
        if(mutableCart.getValue()== null){
            initCart();
        }

        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItem>());
    }

    public boolean addItemToCart(Product product){
        if(mutableCart.getValue()==null){
            initCart();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());

        for (CartItem cartItem:cartItemList){
            if(cartItem.getProduct().getId()==product.getId()){
                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItemList.set(index,cartItem);
                mutableCart.setValue(cartItemList);
                return true;
            }
        }

        CartItem cartItem = new CartItem(product,1);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        return true;
    }

    public void FillPreviousCart(LiveData<List<Category>> allcategories){
        List<CartItem> cartItemList = new ArrayList<>(Objects.requireNonNull(mutableCart.getValue()));
        List<Category> categoryList = new ArrayList<>(Objects.requireNonNull(allcategories.getValue()));
        for (Category category:categoryList){
            for(Product catprodct:category.getAllProducts()){
                if(cartItemList.size()>0){
                    boolean isincart=false;
                    for (CartItem cartItem:cartItemList){
                        if(cartItem.getProduct().getId()==catprodct.getId()){
                            isincart =true;
                            break;
                        }
                    }
                    if(!isincart && catprodct.getQty()>0){
                        CartItem newcart = new CartItem(catprodct,catprodct.getQty());
                        cartItemList.add(newcart);
                    }
                }
                else {
                    if(catprodct.getQty()>0){
                        CartItem newcart = new CartItem(catprodct,catprodct.getQty());
                        cartItemList.add(newcart);
                    }
                }

            }
        }

        mutableCart.setValue(cartItemList);

    }

    public boolean removeItemFromCart(CartItem cartItem) {
        if(mutableCart.getValue()==null)
            return true;

        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        cartItem.getProduct().setQty(0);
        cartItemList.remove(cartItem);
        mutableCart.setValue(cartItemList);
        return true;
    }
}
