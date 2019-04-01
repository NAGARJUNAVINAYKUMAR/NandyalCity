package com.gathratechnal.nandyalcity.directory.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;
import com.gathratechnal.nandyalcity.directory.model.DirectoryModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private Activity mContext;
    ArrayList<DirectoryModel> mCategoryList;
    RecyclerViewClickListner mClickListner;

    public CategoriesAdapter(Activity context, ArrayList<DirectoryModel> categoryList, RecyclerViewClickListner clickListner) {
        this.mContext = context;
        this.mCategoryList = categoryList;
        this.mClickListner = clickListner;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        final DirectoryModel category = mCategoryList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.drawable.ic_launcher);
        requestOptions.override(R.dimen.Ads_width, R.dimen.Ads_height);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(category.getCat_app_icon())
                .into(holder.cat_img);
        holder.cat_name.setText(category.getCat_name());
        holder.ll_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("selected_cat",new Gson().toJson(category));
                mClickListner.onRecyclerItemClicked(bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView cat_name;
        ImageView cat_img;
        LinearLayout ll_list_item;

        CategoryViewHolder(View itemView) {
            super(itemView);
            cat_name = (TextView) itemView.findViewById(R.id.txt_cat_name);
            cat_img = (ImageView) itemView.findViewById(R.id.iv_cat_img);
            ll_list_item = (LinearLayout) itemView.findViewById(R.id.ll_list_item);


        }
    }

}
