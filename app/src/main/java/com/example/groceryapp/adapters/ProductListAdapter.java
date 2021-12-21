package com.example.groceryapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.databinding.ProductItemBinding;
import com.example.groceryapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends ListAdapter<Product,ProductListAdapter.Holder> {


    List<Product> products = new ArrayList<>();
//    Context mContext;

    ProductInterface productInterface;
    public ProductListAdapter(ProductInterface productInterface) {
        super(Product.itemCallback);
        this.productInterface = productInterface;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProductItemBinding itemBinding = ProductItemBinding.inflate(inflater,parent,false);
        itemBinding.setGroceryInterface(productInterface);
        return new Holder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Holder holder, int position) {
        Product product = products.get(position);
        holder.binding.setProduct(product);
//        holder.productName.setText(products.get(position).getName());
//        holder.prodActPrice.setText(""+products.get(position).getActual_price());
//        holder.prodActPrice.setPaintFlags(holder.prodActPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.prodFinalPrice.setText(""+products.get(position).getFinal_price());
//        holder.prodDiscount.setText(products.get(position).getDiscount()+" % OFF");
//
//
        if(product.getQty()==0){
            holder.binding.prodQtyInitial.setVisibility(View.VISIBLE);
            holder.binding.prodQtyLayout.setVisibility(View.GONE);
        }
        else {
            holder.binding.prodQtyInitial.setVisibility(View.GONE);
            holder.binding.prodQtyLayout.setVisibility(View.VISIBLE);
        }

        if(product.getDiscount()==0){
            holder.binding.textProdActualPrice.setVisibility(View.GONE);
            holder.binding.textDiscount.setVisibility(View.GONE);
        }
        else {
            holder.binding.textProdActualPrice.setVisibility(View.VISIBLE);
            holder.binding.textDiscount.setVisibility(View.VISIBLE);
        }

        holder.binding.prodQtyInitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.prodQtyInitial.setVisibility(View.GONE);
                holder.binding.prodQtyLayout.setVisibility(View.VISIBLE);
                product.setQty(1);
//                holder.binding.prodQty.setText("1");
                productInterface.addItem(product);
            }
        });

        holder.binding.prodQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.getQty()==1){
                    holder.binding.prodQtyLayout.setVisibility(View.GONE);
                    holder.binding.prodQtyInitial.setVisibility(View.VISIBLE);
                    product.setQty(0);
                    productInterface.removeItem(product);
                }
                else {
                    int quantity = product.getQty();
                    if (quantity>=1)
                        quantity--;
                    product.setQty(quantity);
                    holder.binding.prodQty.setText(""+quantity);
                }
            }
        });

        holder.binding.prodQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQty();
                quantity++;
                product.setQty(quantity);
//                holder.binding.prodQty.setText(""+quantity);
                productInterface.addItem(product);
            }
        });

    }
    public void submitList(List<Product> productList){
        products.clear();
        this.products = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {


        ProductItemBinding binding;
        public Holder(ProductItemBinding productItemBinding) {
            super(productItemBinding.getRoot());
            this.binding = productItemBinding;

        }
    }


    public interface ProductInterface{
        void addItem(Product product);
        void removeItem(Product product);
    }

}
