package com.example.groceryapp.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.example.groceryapp.MyApplication;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Product;
import com.example.groceryapp.remote.data.DataManager;
import com.example.groceryapp.remote.data.DataValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class groceryRepo {

    private MutableLiveData<List<Category>> mutableLiveData;
    private DataManager dataManager;

    public LiveData<List<Category>> getCategories(){
        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            loadCategories();
        }
        return mutableLiveData;
    }

    private void loadCategories(){
        List<Category> categoryList = new ArrayList<>();
        dataManager = new DataManager();
        dataManager.sendVolleyRequest(new DataValues() {
            @Override
            public void setJsonResponse(JSONObject response) {

                try {
                    JSONArray catJArray = response.getJSONArray("result_array");

                    for (int catIndex=0; catIndex < catJArray.length(); catIndex++)
                    {
                        try {
                            JSONObject catObject = catJArray.getJSONObject(catIndex);
                            // Pulling items from the array
                            int categoryId = catObject.getInt("ID");
                            String categoryName = catObject.getString("name");
                            Category currentCategory = new Category(categoryId,categoryName);

                            JSONArray productJArray = catObject.getJSONArray("products");
                            for (int prodIndex=0; prodIndex < productJArray.length(); prodIndex++)
                            {
                                JSONObject prodObject = productJArray.getJSONObject(prodIndex);
                                Integer prodId = prodObject.getInt("ID");
                                String prodName = prodObject.getString("name");
                                String prodCatname = prodObject.getString("category_name");
                                Double prodFinalprice = prodObject.getDouble("final_price");
                                Double prodActualprice = prodObject.getDouble("actual_price");
                                Double prodDiscount = prodObject.getDouble("discount");
                                Integer prodQty = prodObject.getInt("qty");
                                JSONArray prodImgJArray = prodObject.getJSONArray("images");
                                ArrayList<String> imageUrls = new ArrayList<>();
                                for (int imgIndex=0; imgIndex < prodImgJArray.length(); imgIndex++)
                                {
                                    JSONObject imgObject = prodImgJArray.getJSONObject(imgIndex);
                                    Integer imgId = imgObject.getInt("img_id");
                                    String imgUrl = imgObject.getString("img_product");
                                    imageUrls.add(imgUrl);
                                }
                                Product currentProduct = new Product(prodId,prodName,
                                        prodCatname,prodFinalprice,prodActualprice,prodDiscount,prodQty,imageUrls);

                                currentCategory.setProduct(currentProduct);
                            }

                            categoryList.add(currentCategory);

                        } catch (JSONException e) {
                            Log.e("TAG",e.getMessage());
                        }
                    }
                    mutableLiveData.setValue(categoryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void setJsonError(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(MyApplication.getAppContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addProductQty(Product product) {
        if(mutableLiveData.getValue()==null)
            return;

        List<Category> categoryList = new ArrayList<>(mutableLiveData.getValue());
        for(Category category:categoryList){
            for (Product item:category.getAllProducts()){
                if(product.equals(item)){
                    item.setQty(item.getQty()+1);
                }
            }
        }
        mutableLiveData.setValue(categoryList);
    }

    public void removeProductQty(Product product) {
        if(mutableLiveData.getValue()==null)
            return;

        List<Category> categoryList = new ArrayList<>(mutableLiveData.getValue());
        for(Category category:categoryList){
            for (Product item:category.getAllProducts()){
                if(product.equals(item)){
                    if(item.getQty()>0){
                        item.setQty(item.getQty()-1);
                    }
                    break;
                }
            }
        }
        mutableLiveData.setValue(categoryList);
    }

    public void deleteProductQty(Product product) {
        if(mutableLiveData.getValue()==null)
            return;

        List<Category> categoryList = new ArrayList<>(mutableLiveData.getValue());
        for(Category category:categoryList){
            for (Product item:category.getAllProducts()){
                if(product.equals(item)){
                    item.setQty(0);
                }
            }
        }
        mutableLiveData.setValue(categoryList);
    }
}
