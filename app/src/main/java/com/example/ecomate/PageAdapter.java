package com.example.ecomate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecomate.Product;
import com.example.ecomate.R;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public PageAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mypage_list, null);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PageViewHolder holder, int position) {
        Product product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class PageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public PageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView55);
        }
    }
}