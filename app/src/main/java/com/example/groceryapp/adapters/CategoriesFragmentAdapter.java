package com.example.groceryapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.groceryapp.model.Category;
import com.example.groceryapp.ui.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragmentAdapter extends FragmentPagerAdapter {

    private List<Category> categoriesTab = new ArrayList<>();
    private ProductsFragment fragment;


    public CategoriesFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return categoriesTab.size();
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new ProductsFragment(categoriesTab.get(position));
        return fragment;
    }

    public void submitList(List<Category> categorylist){
        categoriesTab.clear();
        this.categoriesTab = categorylist;
        notifyDataSetChanged();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return categoriesTab.get(position).getName();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
