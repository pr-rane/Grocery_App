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

        return new Holder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Holder holder, int position) {
        Product product = products.get(position);
        holder.binding.setItemPosition(position);
        holder.binding.setProduct(product);
        holder.binding.setGroceryInterface(productInterface);

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
                productInterface.addItem(product,position);
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
        void addItem(Product product,int itemPosition);
        void removeItem(Product product,int itemPosition);
    }

}
