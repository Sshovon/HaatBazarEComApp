package com.example.haatbazarecomapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haatbazarecomapp.CartAdapter;
import com.example.haatbazarecomapp.CartItemModel;
import com.example.haatbazarecomapp.DBqueries;
import com.example.haatbazarecomapp.FinalActivity;
import com.example.haatbazarecomapp.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private GalleryViewModel galleryViewModel;
    private TextView totalCartAmount;
    public static CartAdapter cartAdapter;
    public GalleryFragment(){

    }
    private RecyclerView cartItemsRecyclerView;
    private Button cartBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        cartItemsRecyclerView= root.findViewById(R.id.cart_item_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);
        cartBtn=(Button)root.findViewById(R.id.cart_continue_btn);
        totalCartAmount=root.findViewById(R.id.total_cart_amount);

        if(DBqueries.cartItemModelList.size()==0){
            DBqueries.cartList.clear();
            DBqueries.loadCart(getContext(),true);
        }

        cartAdapter = new CartAdapter(DBqueries.cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();


        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalIntent= new Intent(getContext(), FinalActivity.class);
                container.getContext().startActivity(finalIntent);
            }
        });


        return root;
    }

}