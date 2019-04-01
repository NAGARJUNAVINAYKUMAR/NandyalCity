package com.gathratechnal.nandyalcity.webpages;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.ads.AdsAdapter;
import com.gathratechnal.nandyalcity.ads.AdsModel;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 1/19/2018.
 */

public class WebPagesAdapter extends RecyclerView.Adapter<WebPagesAdapter.pagesViewHolder> {
    private Activity mContext;
    ArrayList<WebPageModel> mPagesList;
    RecyclerViewClickListner mClickListner;

    public WebPagesAdapter(Activity activity, ArrayList<WebPageModel> pagesList, RecyclerViewClickListner clickListner) {
        this.mContext = activity;
        this.mPagesList = pagesList;
        this.mClickListner = clickListner;
    }

    @Override
    public WebPagesAdapter.pagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new WebPagesAdapter.pagesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WebPagesAdapter.pagesViewHolder holder, int position) {
        final WebPageModel model = mPagesList.get(position);
        holder.title.setText(model.getWebTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher);
        requestOptions.error(R.drawable.ic_launcher);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(model.getLogo()).into(holder.web_img);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("selectedPage", new Gson().toJson(model));
                mClickListner.onRecyclerItemClicked(bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPagesList.size();
    }

    class pagesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView web_img;

        pagesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_cat_name);
            web_img = (ImageView) itemView.findViewById(R.id.iv_cat_img);


        }
    }
}
