package com.example.haatbazarecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.haatbazarecomapp.DBqueries.lists;
import static com.example.haatbazarecomapp.DBqueries.loadFragmentData;
import static com.example.haatbazarecomapp.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private HomePageAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///for support
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        ///for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoryRecyclerView=findViewById(R.id.category_recyclerVieww);

        ////banner slider

        /*List<SliderModel> sliderModelList = new ArrayList<>();

        sliderModelList.add(new SliderModel(R.drawable.user));
        sliderModelList.add(new SliderModel(R.drawable.logout));

        sliderModelList.add(new SliderModel(R.drawable.bell));
        sliderModelList.add(new SliderModel(R.drawable.bkash));
        sliderModelList.add(new SliderModel(R.drawable.home));
        sliderModelList.add(new SliderModel(R.drawable.search));
        sliderModelList.add(new SliderModel(R.drawable.cart));
        sliderModelList.add(new SliderModel(R.drawable.account));
        sliderModelList.add(new SliderModel(R.drawable.user));
        sliderModelList.add(new SliderModel(R.drawable.logout));

        sliderModelList.add(new SliderModel(R.drawable.bell));
        sliderModelList.add(new SliderModel(R.drawable.bkash));
        */////banner slider


        ////horzontal product layout
       /* List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "99"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "100"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "122", "200"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "300"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "99"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "500"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "99"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "79"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.onion, "Onion", "120", "99"));
       */ ////horzontal product layout


        /////////////

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        HomePageAdapter adapter ;
        int listPosition=0;
        for(int i=0;i<DBqueries.loadedCategoriesNames.size();i++){
            if(DBqueries.loadedCategoriesNames.get(i).equals(title.toUpperCase())){
                listPosition=i;
            }
        }
        if(listPosition==0){
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(loadedCategoriesNames.size()-1));
            loadFragmentData(adapter,this,loadedCategoriesNames.size()-1,title);

        }else{
            adapter = new HomePageAdapter(lists.get(listPosition));

        }


        /*homePageModelList.add(new HomePageModel(1, "Exclusive Deals", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(2, "Editor's Choice", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1, "Black Friday Offers", horizontalProductScrollModelList));
*/
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /////////


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.main_search_icon){
            //search icon job
            return  true;
        }else if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
