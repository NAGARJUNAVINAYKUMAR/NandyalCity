package com.gathratechnal.nandyalcity.property;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 1/12/2018.
 */

public class PropertyImageAdapter extends PagerAdapter {

    private ArrayList<String> slideObj;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerViewClickListner mClickListner;

    public PropertyImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.slideObj = images;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slideObj.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.slide_item, view, false);
        ImageView iv_image = (ImageView) myImageLayout
                .findViewById(R.id.iv_slide);
        TextView click_more = myImageLayout.findViewById(R.id.txt_click_more);
        click_more.setVisibility(View.GONE);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher);
        requestOptions.error(R.drawable.ic_launcher);
        if (slideObj != null && slideObj.get(position) != null && slideObj.get(position) != null) {
            Glide.with(context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(slideObj.get(position))
                    .into(iv_image);
            view.addView(myImageLayout, 0);


        }
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
