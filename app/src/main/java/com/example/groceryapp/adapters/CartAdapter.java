package com.example.groceryapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.databinding.CartItemBinding;
import com.example.groceryapp.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {


    List<CartItem> cartItems = new ArrayList<>();
    private  CartInterface cartInterface;

    public CartAdapter(CartInterface cartInterface) {
        this.cartInterface = cartInterface;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CartItemBinding itemBinding = CartItemBinding.inflate(inflater,parent,false);

        return new Holder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Holder holder, int position) {
        CartItem product = cartItems.get(position);
        holder.binding.setItemPosition(position);
        holder.binding.setCartItem(product);
        holder.binding.setCartInterface(cartInterface);

        if(cartItems.get(position).getProduct().getQty()==0){
            cartInterface.deleteItem(cartItems.get(position));
        }
        else {
            holder.binding.prodQtyLayout.setVisibility(View.VISIBLE);
        }

        if(cartItems.get(position).getProduct().getDiscount()==0){
            holder.binding.textProdActualPrice.setVisibility(View.GONE);
            holder.binding.textDiscount.setVisibility(View.GONE);
        }
        else {
            holder.binding.textProdActualPrice.setVisibility(View.VISIBLE);
            holder.binding.textDiscount.setVisibility(View.VISIBLE);
        }

    }
    public void submitList(List<CartItem> Items){
        cartItems.clear();
        this.cartItems = Items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {


        CartItemBinding binding;
        public Holder(CartItemBinding cartItemBinding) {
            super(cartItemBinding.getRoot());
            this.binding = cartItemBinding;

        }
    }

    public interface CartInterface{
        void deleteItem(CartItem cartItem);
        void addItem(CartItem cartItem,int itemPosition);
        void removeItem(CartItem cartItem,int itemPosition);
    }

}
