package com.example.groceryapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.groceryapp.R;
import com.example.groceryapp.databinding.ActivityMainBinding;
import com.example.groceryapp.model.CartItem;
import com.example.groceryapp.viewmodel.groceryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    groceryViewModel groceryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(mainBinding.bottomNavView,navController);


        BottomNavigationMenuView mbottomNavigationMenuView = (BottomNavigationMenuView) mainBinding.bottomNavView.getChildAt(0);
        View menuview = mbottomNavigationMenuView.getChildAt(2);

        BottomNavigationItemView itemView = (BottomNavigationItemView) menuview;

        View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.cart_nav_item,mbottomNavigationMenuView, false);
        TextView cart_badge_text = cart_badge.findViewById(R.id.cart_badge_text);

        itemView.addView(cart_badge);
        groceryModel = new ViewModelProvider(this).get(groceryViewModel.class);
        groceryModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                Log.e("MainActivity",String.valueOf(cartItems.size()));
                cart_badge_text.setText(String.valueOf(cartItems.size()));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}