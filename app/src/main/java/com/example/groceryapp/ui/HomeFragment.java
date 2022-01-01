package com.example.groceryapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.groceryapp.adapters.CategoriesFragmentAdapter;
import com.example.groceryapp.databinding.FragmentHomeBinding;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.viewmodel.groceryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private CategoriesFragmentAdapter categoriesAdapter;

    private ArrayList<Category> categories = new ArrayList<>();

    //TODO : we need to make viewmodel to return product so that in product fragment we can access viewmodel
    public static groceryViewModel groceryViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    FragmentHomeBinding homeBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoriesAdapter = new CategoriesFragmentAdapter(requireActivity().getSupportFragmentManager());

        homeBinding.viewPager.setAdapter(categoriesAdapter);
        homeBinding.tabLayout.setupWithViewPager(homeBinding.viewPager);

        groceryViewModel = new ViewModelProvider(requireActivity()).get(groceryViewModel.class);
        groceryViewModel = new ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(groceryViewModel.class);
        groceryViewModel.getCategories(requireContext()).observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesAdapter.submitList(categories);
                groceryViewModel.FillPreviousCart();
            }
        });

    }

}