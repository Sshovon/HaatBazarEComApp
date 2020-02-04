package com.example.haatbazarecomapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haatbazarecomapp.DBqueries;
import com.example.haatbazarecomapp.R;
import com.example.haatbazarecomapp.WishlistAdapter;
import com.example.haatbazarecomapp.WishlistModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.haatbazarecomapp.DBqueries.wishlistModelList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private RecyclerView wishlistRecyclerView;
    public static WishlistAdapter wishlistAdapter;
    public SlideshowFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        wishlistRecyclerView=root.findViewById(R.id.my_wishlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(layoutManager);

        if(wishlistModelList.size()==0){
            DBqueries.wishList.clear();
            DBqueries.loadWishlist(getContext(),true);
        }

        wishlistAdapter=new WishlistAdapter(wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();


        return root;
    }
}