package com.example.groceryapp.model;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class Product {

    private int id;

    private String name;
    private String category_name;
    private double final_price;
    private double actual_price;
    private double discount;
    private int qty;
    private boolean available;
    private String variant;
    private ArrayList<String> imageUrls;

    public Product(int id, String name, String category_name, double final_price, double actual_price, double discount, int qty, ArrayList<String> imageUrls) {
        this.id = id;
        this.name = name;
        this.category_name = category_name;
        this.final_price = final_price;
        this.actual_price = actual_price;
        this.discount = discount;
        this.qty = qty;
        this.imageUrls = imageUrls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    public double getActual_price() {
        return actual_price;
    }

    public void setActual_price(double actual_price) {
        this.actual_price = actual_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", final_price=" + final_price +
                ", actual_price=" + actual_price +
                ", discount=" + discount +
                ", variant='" + variant + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId() &&
                Double.compare(product.getFinal_price(), getFinal_price()) == 0 &&
                Double.compare(product.getActual_price(), getActual_price()) == 0 &&
                Double.compare(product.getDiscount(), getDiscount()) == 0 &&
                getQty() == product.getQty() &&
                getName().equals(product.getName()) &&
                getCategory_name().equals(product.getCategory_name()) &&
                getVariant().equals(product.getVariant());
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Product oldItem, @NonNull @NotNull Product newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Product oldItem, @NonNull @NotNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:productImage")
    public static void loadImage(ImageView imageView,ArrayList<String> imgUrl){
        Glide.with(imageView)
                .load(imgUrl.get(0))
                .fitCenter()
                .into(imageView);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
