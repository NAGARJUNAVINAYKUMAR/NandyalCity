package com.gathratechnal.nandyalcity.property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.property.model.PropertyModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 1/6/2018.
 */

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.mProperListViewHolder> {
    ArrayList<PropertyModel> mProperyList;
    Activity mContext;
    boolean isFromHome;

    public PropertyListAdapter(Activity activity, ArrayList<PropertyModel> properyList,boolean b) {
        mProperyList = properyList;
        mContext = activity;
        isFromHome = b;

    }

    @Override
    public PropertyListAdapter.mProperListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_list_item, parent, false);
        return new PropertyListAdapter.mProperListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PropertyListAdapter.mProperListViewHolder holder, int position) {
        final PropertyModel propertyModel = mProperyList.get(position);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );


        if (!isFromHome) {
            holder.propertyMode.setVisibility(View.VISIBLE);
            holder.propertyAmout.setVisibility(View.INVISIBLE);
            params.setMargins(0, 0, 0, 0);
            holder.propertyMain.setLayoutParams(params);

        }else{
            holder.propertyMode.setVisibility(View.INVISIBLE);
            holder.propertyAmout.setVisibility(View.VISIBLE);
            holder.propertyMain.setLayoutParams(params);
            params.setMargins(0, 0, 0, 0);
        }
            holder.propertyMode.setText(propertyModel.getMode());
            holder.propertyTitle.setText(propertyModel.getPropeartyTitle());
            holder.propertyType.setText(propertyModel.getType() + "( " + propertyModel.getMode() + " )");
            holder.propertyAmout.setText("Rs " + propertyModel.getAmount() + "/-");
            holder.propertyAddress.setText(propertyModel.getMobile());
        holder.propertyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(mContext, DetailsActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("selectedProp",new Gson().toJson(propertyModel));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.drawable.ic_launcher);


        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(propertyModel.getImg1())
                .into(holder.propertyImg);


    }


    @Override
    public int getItemCount() {
        return mProperyList.size();
    }

    class mProperListViewHolder extends RecyclerView.ViewHolder {
        TextView propertyType, propertyMode,propertyTitle,propertyAmout,propertyMore,propertyAddress;
        ImageView propertyImg;
        RelativeLayout propertyMain;

        mProperListViewHolder(View itemView) {
            super(itemView);
            propertyType = (TextView) itemView.findViewById(R.id.property_list_type);
            propertyTitle = (TextView) itemView.findViewById(R.id.property_list_title);
            propertyMode = (TextView) itemView.findViewById(R.id.property_list_mode);
            propertyAmout = (TextView) itemView.findViewById(R.id.property_list_amount);
            propertyMore = (TextView) itemView.findViewById(R.id.property_list_more);
            propertyMain = (RelativeLayout) itemView.findViewById(R.id.property_list_main);
            propertyAddress = (TextView) itemView.findViewById(R.id.property_list_address);
            propertyImg = itemView.findViewById(R.id.property_list_img);

        }
    }
}
