package com.gathratechnal.nandyalcity.directory.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.model.DirectoryModel;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class DirectoryExpandableListAdapter extends BaseExpandableListAdapter {
    private final LayoutInflater inflater;
    private Context mContext;
    LinkedHashMap<String, List<DirectoryModel>> mDirectoryMap;
    Object[] main_catList;
    private TextView p_cat_name;
    private ImageView p_cat_img;
    private TextView c_cat_name;
    private ImageView c_cat_img;


    public DirectoryExpandableListAdapter(Activity context, LinkedHashMap<String, List<DirectoryModel>> directoryMap) {
        this.mContext = context;
        this.mDirectoryMap = directoryMap;
        this.inflater = LayoutInflater.from(context);
        main_catList = mDirectoryMap.keySet().toArray();
    }

    @Override
    public int getGroupCount() {
        return mDirectoryMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mDirectoryMap.get(main_catList[i].toString()).size();
    }

    @Override
    public Object getGroup(int i) {
        return mDirectoryMap.get(main_catList[i].toString());
    }

    @Override
    public Object getChild(int i, int i1) {
        return mDirectoryMap.get(main_catList[i].toString()).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View parentView = inflater.inflate(R.layout.category_list_item, null);
        p_cat_name = (TextView) parentView.findViewById(R.id.txt_cat_name);
        p_cat_img = (ImageView) parentView.findViewById(R.id.iv_cat_img);
        setGroupValues(i);

        return parentView;
    }

    private void setGroupValues(int i) {
        if (main_catList != null && mDirectoryMap.get((String) main_catList[i]) != null) {
            p_cat_name.setText(mDirectoryMap.get((String) main_catList[i]).get(0).getCat_name());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.drawable.ic_launcher);

            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(mDirectoryMap.get((String) main_catList[i]).get(0).getCat_app_icon())
                    .into(p_cat_img);

        }
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View childView = inflater.inflate(R.layout.cat_list_child, null);
        c_cat_name = (TextView) childView.findViewById(R.id.txt_child_cat_name);
        c_cat_img = (ImageView) childView.findViewById(R.id.iv_child_cat_img);
        setChildValues(i, i1);

        return childView;
    }

    private void setChildValues(int groupPos, int childPos) {
        if (main_catList != null && mDirectoryMap.get((String) main_catList[groupPos]) != null) {
            c_cat_name.setText(mDirectoryMap.get((String) main_catList[groupPos]).get(childPos).getSub_cat_name());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.drawable.ic_launcher);

            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(mDirectoryMap.get((String) main_catList[groupPos]).get(childPos).getSub_cat_app_icon())
                    .into(c_cat_img);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
