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
//    Context mContext;
    private  CartInterface cartInterface;

    public CartAdapter(CartInterface cartInterface) {
//        super(Product.itemCallback);
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
        holder.binding.setCartItem(product);

//        holder.productName.setText(products.get(position).getName());
//        holder.prodActPrice.setText(""+products.get(position).getActual_price());
//        holder.prodActPrice.setPaintFlags(holder.prodActPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.prodFinalPrice.setText(""+products.get(position).getFinal_price());
//        holder.prodDiscount.setText(products.get(position).getDiscount()+" % OFF");
//
//
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

        holder.binding.prodDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartInterface.deleteItem(cartItems.get(position));
            }
        });

        holder.binding.prodQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItems.get(position).getProduct().getQty()==1){
//                    holder.binding.prodQtyLayout.setVisibility(View.GONE);
                    cartItems.get(position).getProduct().setQty(0);
                    cartInterface.deleteItem(cartItems.get(position));
                }
                else {
                    int quantity = cartItems.get(position).getProduct().getQty();
                    if (quantity>=1)
                        quantity--;
                    cartItems.get(position).getProduct().setQty(quantity);
                    holder.binding.prodQty.setText(""+quantity);
                }
            }
        });

        holder.binding.prodQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItems.get(position).getProduct().getQty();
                quantity++;
                cartItems.get(position).getProduct().setQty(quantity);
                holder.binding.prodQty.setText(""+quantity);
            }
        });

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
    }

}
