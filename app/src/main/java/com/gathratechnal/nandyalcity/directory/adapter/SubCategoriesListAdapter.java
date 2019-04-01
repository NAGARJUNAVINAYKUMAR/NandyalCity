package com.gathratechnal.nandyalcity.directory.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.model.DirectoryModel;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class SubCategoriesListAdapter extends RecyclerView.Adapter<SubCategoriesListAdapter.SubCategoriesViewHolder> {


    private Activity mContext;
    ArrayList<DirectoryModel> mSubCatList;

    public SubCategoriesListAdapter(Activity context, ArrayList<DirectoryModel> categoryList) {
        this.mContext = context;
        this.mSubCatList = categoryList;
    }


    @Override
    public SubCategoriesListAdapter.SubCategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat_list_item, parent, false);
        return new SubCategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubCategoriesViewHolder holder, int position) {
        final DirectoryModel subCategory = mSubCatList.get(position);
        if (subCategory != null) {
            if (subCategory.getName() != null && !subCategory.getName().isEmpty())
                checkVisibility(holder.sub_cat_name, "Name : " + subCategory.getName());
            if (subCategory.getAddress() != null && !subCategory.getAddress().isEmpty())
                checkVisibility(holder.sub_cat_address, "Address : " + subCategory.getAddress());
            if (subCategory.getEmail() != null && !subCategory.getEmail().isEmpty())
                checkVisibility(holder.sub_cat_email, "email-Id : " + subCategory.getEmail());
            else
                holder.sub_cat_email.setVisibility(View.GONE);
            if (subCategory.getShop_ofc() != null && !subCategory.getShop_ofc().isEmpty())
                checkVisibility(holder.sub_cat_main_title, subCategory.getShop_ofc());
            else
                holder.sub_cat_main_title.setVisibility(View.GONE);
            if (subCategory.getOffice() != null && !subCategory.getOffice().isEmpty()) {
                checkVisibility(holder.sub_cat_ofc_no, "Ofc No : " + subCategory.getOffice());
            } else {
                holder.sub_cat_ofc_no.setVisibility(View.GONE);
            }
            if (subCategory.getMobile() != null && !subCategory.getMobile().isEmpty()) {
                checkVisibility(holder.sub_cat_mobile, "Mobile : " + subCategory.getMobile());
            } else {
                holder.sub_cat_mobile.setVisibility(View.GONE);
            }
            holder.sub_cat_item_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (subCategory.getMobile() != null && !subCategory.getMobile().isEmpty()) {
                        callNumber(subCategory.getMobile());

                    } else if (subCategory.getOffice() != null && !subCategory.getOffice().isEmpty()) {
                        callNumber(subCategory.getOffice());
                    }
                }
            });
        }
    }

    private void callNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CALL_PHONE}, 5);
            return;
        } else {
            mContext.startActivity(intent);
        }
    }

    private void checkVisibility(TextView view, String Value) {
        if (Value != null) {
            view.setVisibility(View.VISIBLE);
            view.setText(Value);
        }

    }

    @Override
    public int getItemCount() {
        return mSubCatList.size();
    }

    class SubCategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView sub_cat_name, sub_cat_email, sub_cat_ofc_no, sub_cat_mobile, sub_cat_address, sub_cat_main_title;
        ImageView sub_cat_item_call;

        SubCategoriesViewHolder(View itemView) {
            super(itemView);
            sub_cat_name = (TextView) itemView.findViewById(R.id.sub_cat_item_name);
            sub_cat_email = (TextView) itemView.findViewById(R.id.sub_cat_item_email);
            sub_cat_ofc_no = (TextView) itemView.findViewById(R.id.sub_cat_item_ofc_no);
            sub_cat_mobile = (TextView) itemView.findViewById(R.id.sub_cat_item_mobile);
            sub_cat_address = (TextView) itemView.findViewById(R.id.sub_cat_item_adress);
            sub_cat_main_title = (TextView) itemView.findViewById(R.id.sub_cat_item_main_title);
            sub_cat_item_call = (ImageView) itemView.findViewById(R.id.sub_cat_item_call);


        }
    }
}
