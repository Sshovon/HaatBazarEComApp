package com.example.haatbazarecomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haatbazarecomapp.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false;
    public static boolean ALREADY_ADDED_TO_CART = false;
    public static boolean running_cart_query = false;

    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    public static FloatingActionButton addToWishListButton;
    private Button buyNowBtn;
    private LinearLayout addToCartBtn;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;

    private FirebaseFirestore firebaseFirestore;
    public static String productID;

    private TextView productTitle;
    private TextView productPrice;
    private TextView productRating;
    private TextView totalRatings;
    private TextView productDescription;
    private DocumentSnapshot documentSnapshot;

    public static MenuItem cartItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ///backkey show code
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishListButton = findViewById(R.id.add_to_wishlist_button);
        productTitle = findViewById(R.id.product_title);
        productRating = findViewById(R.id.tv_product_rating_miniview);
        totalRatings = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.tv_product_description);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);

        buyNowBtn = findViewById(R.id.buy_now_btn);
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");

        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();

                    for (long i = 1; i <= (long) documentSnapshot.get("no_of_product_images"); i++) {
                        productImages.add(documentSnapshot.get("product_image_" + i).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    productRating.setText(documentSnapshot.get("average_rating").toString());
                    totalRatings.setText("Total Ratings (" + (long) documentSnapshot.get("total_ratings") + ")");
                    productPrice.setText(documentSnapshot.get("product_price").toString());
                    productDescription.setText(documentSnapshot.get("product_description").toString());


                    if (DBqueries.wishList.size() == 0) {
                        DBqueries.loadWishlist(ProductDetailsActivity.this, false);
                    }
                    if (DBqueries.cartList.size() == 0) {
                        DBqueries.loadCart(ProductDetailsActivity.this, false);
                    }

                    ///heree
                    if (DBqueries.cartList.contains(productID)) {
                        ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                        //addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
                    } else {
                        //addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#636363")));
                        ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                    }


                    if (DBqueries.wishList.contains(productID)) {
                        ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
                    } else {
                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#636363")));
                        ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailsActivity.this, "Succesfully Added to your cart", Toast.LENGTH_SHORT).show();
            }
        });

        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!running_wishlist_query) {
                    running_wishlist_query = true;
                    if (ALREADY_ADDED_TO_WISHLIST) {
                        int index = DBqueries.wishList.indexOf(productID);
                        DBqueries.removeFromWishList(index, ProductDetailsActivity.this);
                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#636363")));
                    } else {

                        ProductDetailsActivity.addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put("product_ID_" + String.valueOf(DBqueries.wishList.size()), productID);
                        addProduct.put("list_size", (long) DBqueries.wishList.size() + 1);

                        firebaseFirestore.collection("USERS").document(DBqueries.currentUser.getUid()).collection("USER_DATA")
                                .document("MY_WISHLIST")
                                .set(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    if (task.isSuccessful()) {
                                        if (DBqueries.wishlistModelList.size() != 0) {
                                            DBqueries.wishlistModelList.add(new WishlistModel(productID, documentSnapshot.get("product_image_1").toString(),
                                                    documentSnapshot.get("product_title").toString(),
                                                    documentSnapshot.get("average_rating").toString(),
                                                    (long) documentSnapshot.get("total_ratings"),
                                                    documentSnapshot.get("product_price").toString()));
                                        }
                                        ALREADY_ADDED_TO_WISHLIST = true;
                                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
                                        DBqueries.wishList.add(productID);
                                        Toast.makeText(ProductDetailsActivity.this, "This Product is added successfully to your Wishlist.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#636363")));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                }
                                running_wishlist_query = false;
                            }
                        });


                    }
                }
            }
        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalIntent = new Intent(ProductDetailsActivity.this, FinalActivity.class);
                startActivity(finalIntent);
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!running_cart_query) {
                    running_cart_query = true;
                    if (ALREADY_ADDED_TO_CART) {
                        running_cart_query = false;
                        Toast.makeText(ProductDetailsActivity.this, "Already added to cart.", Toast.LENGTH_SHORT).show();
                    } else {

                        //ProductDetailsActivity.addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put("product_ID_" + String.valueOf(DBqueries.cartList.size()), productID);
                        addProduct.put("list_size", (long) DBqueries.cartList.size() + 1);

                        firebaseFirestore.collection("USERS").document(DBqueries.currentUser.getUid()).collection("USER_DATA")
                                .document("MY_CART")
                                .set(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    if (DBqueries.cartItemModelList.size() != 0) {
                                        DBqueries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID,
                                                documentSnapshot.get("product_image_1").toString(),
                                                documentSnapshot.get("product_title").toString(),
                                                documentSnapshot.get("product_price").toString(),
                                                (long) 1));

                                    }
                                    ALREADY_ADDED_TO_CART = true;
                                    DBqueries.cartList.add(productID);
                                    Toast.makeText(ProductDetailsActivity.this, "This Product is added successfully to your cart.", Toast.LENGTH_SHORT).show();

                                    running_cart_query = false;
                                    invalidateOptionsMenu();
                                } else {
                                    //addToWishListButton.setEnabled(true);
                                    running_cart_query = false;
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                }

            }
        });
    }

    protected void onStart() {
        super.onStart();


        if (DBqueries.wishList.size() == 0) {
            DBqueries.loadWishlist(ProductDetailsActivity.this, false);
        }
        if (DBqueries.cartList.size() == 0) {
            DBqueries.loadCart(ProductDetailsActivity.this, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);

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
                    myCart();
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
        if (id == android.R.id.home) {
            //search icon job
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            /// notification icon job
            return true;
        } else if (id == R.id.main_cart_icon) {
            ///cart icon job
            return true;
        }

        if (DBqueries.cartList.contains(productID)) {
            ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
            //addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
        } else {
            //addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#636363")));
            ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
        }
        if (DBqueries.wishList.contains(productID)) {
            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
            addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
        } else {
            addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#636363")));
            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
        }


        return super.onOptionsItemSelected(item);
    }
    private void myCart() {
        Intent cartIntent= new Intent(ProductDetailsActivity.this,MainActivity.class);
        startActivity(cartIntent);

    }

}
