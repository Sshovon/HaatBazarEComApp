package com.example.haatbazarecomapp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.haatbazarecomapp.CategoryAdapter;
import com.example.haatbazarecomapp.CategoryModel;
import com.example.haatbazarecomapp.DBqueries;
import com.example.haatbazarecomapp.GridProductLayoutAdapter;
import com.example.haatbazarecomapp.HomePageAdapter;
import com.example.haatbazarecomapp.HomePageModel;
import com.example.haatbazarecomapp.HorizontalProductScrollAdapter;
import com.example.haatbazarecomapp.HorizontalProductScrollModel;
import com.example.haatbazarecomapp.R;
import com.example.haatbazarecomapp.SliderAdapter;
import com.example.haatbazarecomapp.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.haatbazarecomapp.DBqueries.firebaseFirestore;

import static com.example.haatbazarecomapp.DBqueries.categoryModelList;
import static com.example.haatbazarecomapp.DBqueries.lists;
import static com.example.haatbazarecomapp.DBqueries.loadCategoires;
import static com.example.haatbazarecomapp.DBqueries.loadFragmentData;
import static com.example.haatbazarecomapp.DBqueries.loadedCategoriesNames;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView homepageRecyclerView;

    private HomePageAdapter adapter;

    public HomeFragment() {

    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = root.findViewById(R.id.category_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);




        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        if(categoryModelList.size()==0){
            loadCategoires(categoryAdapter,getContext());

        }else{
            categoryAdapter.notifyDataSetChanged();
        }


        homepageRecyclerView = root.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homepageRecyclerView.setLayoutManager(testingLayoutManager);




        if(lists.size()==0){
            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(0));
            loadFragmentData(adapter,getContext(),0,"HOME");
        }else{
            adapter = new HomePageAdapter(lists.get(0));
            adapter.notifyDataSetChanged();
        }

        homepageRecyclerView.setAdapter(adapter);

        return root;
    }


}