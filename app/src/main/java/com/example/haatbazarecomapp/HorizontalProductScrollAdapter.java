package com.example.haatbazarecomapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }


    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImage();
        String name = horizontalProductScrollModelList.get(position).getProductTitle();
        String prePrice = horizontalProductScrollModelList.get(position).getProductPreviousPrice();
        String curPrice = horizontalProductScrollModelList.get(position).getProductCurrentPrice();
        String productID=horizontalProductScrollModelList.get(position).getProduct_ID();

        holder.setData(productID,resource,name,prePrice,curPrice) ;
    }

    ///maximum view for horizontal product view
    @Override
    public int getItemCount() {
        if (horizontalProductScrollModelList.size() > 8)
            return 8;
        return horizontalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPreviousPrice;
        private TextView prooductCurrentPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productName = itemView.findViewById(R.id.h_s_product_name);
            productPreviousPrice = itemView.findViewById(R.id.h_s_product_previous_price);
            prooductCurrentPrice = itemView.findViewById(R.id.h_s_product_current_price);

                    }

        private void setData(final String productID, String resource, String name, String pre_price, String cur_price) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.home)).into(productImage);
            productName.setText(name);
            productPreviousPrice.setText(pre_price);
            prooductCurrentPrice.setText(cur_price);
            if(!name.equals("")){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("PRODUCT_ID",productID);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }
        }
    }
}
