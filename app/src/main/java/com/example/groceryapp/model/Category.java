package com.example.groceryapp.model;

import java.util.ArrayList;

public class Category {

    private int id;
    private String name;
    private ArrayList<Product> products = new ArrayList<>();

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
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

    public void setProduct(Product product){
        this.products.add(product);
    }

    public ArrayList<Product> getAllProducts(){
        return products;
    }

    public void clearAllProduct(){
        this.products.clear();
    }

}
