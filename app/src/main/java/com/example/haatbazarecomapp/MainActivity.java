package com.example.haatbazarecomapp;

import android.content.ClipData;
import android.os.Bundle;

import com.example.haatbazarecomapp.ui.gallery.GalleryFragment;
import com.example.haatbazarecomapp.ui.home.HomeFragment;
import com.example.haatbazarecomapp.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.haatbazarecomapp.ProductDetailsActivity.cartItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;

    private static int  currentFragment;
    private FrameLayout frameLayout;

    private  NavigationView navigationView;

    @Override
    public void onBackPressed(){
        DrawerLayout drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        invalidateOptionsMenu();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);


         navigationView = findViewById(R.id.nav_view);

        ///extra 2 line added from video

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_homepage, R.id.nav_my_orders, R.id.nav_my_cart, R.id.nav_my_wishlist,
                R.id.nav_my_account, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        if(DBqueries.currentUser==null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else{
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment==HOME_FRAGMENT){
            getMenuInflater().inflate(R.menu.main, menu);
        }

        cartItem = menu.findItem(R.id.main_cart_icon);
        if(DBqueries.cartList.size()>0){
            cartItem = menu.findItem(R.id.main_cart_icon);
            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeIcon =cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.cart);
            TextView badgeCount=cartItem.getActionView().findViewById(R.id.badge_count);
            badgeCount.setText(String.valueOf(DBqueries.cartList.size()));


            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else{
            cartItem.setActionView(null);
        }

        return true;
    }

    ///nije kora extra method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {

            return true;
        } else if (id == R.id.main_notification_icon) {
            /// notification icon job
            return true;
        } else if (id == R.id.main_cart_icon) {
             myCart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    ///notun implement kora code
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_homepage) {
            setFragment(new HomeFragment(),HOME_FRAGMENT);

        } else if (id == R.id.nav_my_orders) {

        } else if (id == R.id.nav_my_wishlist) {
            myWishlist();

        } else if (id == R.id.nav_my_cart) {
            myCart();

        } else if (id == R.id.nav_my_account) {

        } else if (id == R.id.nav_logout) {

        }
        return false;

    }

    private void myCart() {
        invalidateOptionsMenu();

        FragmentManager fm =getSupportFragmentManager();
        GalleryFragment galleryFragment= new GalleryFragment();
        fm.beginTransaction().replace(R.id.drawer_layout,galleryFragment).commit();

    }
    private void setFragment(Fragment fragment,int fragmentNo){
        currentFragment=fragmentNo;
        FragmentManager fm= getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.drawer_layout,fragment).commit();
        finish();
    }
    private void myWishlist(){
        FragmentManager fm =getSupportFragmentManager();
        SlideshowFragment slideshowFragment= new SlideshowFragment();
        fm.beginTransaction().replace(R.id.drawer_layout,slideshowFragment).commit();

    }
}
