package com.gathratechnal.nandyalcity.ads;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {
    private Activity mContext;
    ArrayList<AdsModel> mAdsList;
    RecyclerViewClickListner mClickListner;

    public AdsAdapter(Activity context, ArrayList<AdsModel> AdsList, RecyclerViewClickListner clickListner) {
        this.mContext = context;
        this.mAdsList = AdsList;
        this.mClickListner = clickListner;
    }


    @Override
    public AdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_list_item, parent, false);
        return new AdsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdsViewHolder holder,final int position) {
         AdsModel Ads = mAdsList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.drawable.ic_launcher);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(Ads.getApp_image()).into(holder.ad_img);

        holder.details.setText(Ads.getDetails());
        if (Ads.getAdd_title() !=null) {
            holder.title.setText(Ads.getAdd_title());
        }else if (Ads.getDeal_title() !=null){
            holder.title.setText(Ads.getDeal_title());
        }
        holder.expire_date.setText(Ads.getExp_date());
        holder.read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("selectedAd", new Gson().toJson(mAdsList.get(position)));
                mClickListner.onRecyclerItemClicked(bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAdsList.size();
    }

    class AdsViewHolder extends RecyclerView.ViewHolder {
        TextView title, expire_date, read_more, details;
        ImageView ad_img;

        AdsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_ad_title);
            expire_date = (TextView) itemView.findViewById(R.id.txt_expiry_date);
            read_more = (TextView) itemView.findViewById(R.id.txt_read_more);
            details = (TextView) itemView.findViewById(R.id.txt_ad_deatils);
            ad_img = (ImageView) itemView.findViewById(R.id.ad_image);


        }
    }

}
