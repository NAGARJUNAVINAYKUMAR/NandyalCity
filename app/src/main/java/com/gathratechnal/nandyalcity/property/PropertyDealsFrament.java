package com.gathratechnal.nandyalcity.property;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.property.model.PropertyModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by arunmididoddy on 1/11/2018.
 */

public class PropertyDealsFrament extends Fragment {
    private ViewPager mPager;
    private CircleIndicator indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View details = inflater.inflate(R.layout.property_details_fragment, container, false);
        initViews(details);
        setdetails();
        return details;
    }

    private void setdetails() {

    }

    private void initViews(View details) {
        mPager = (ViewPager) details.findViewById(R.id.property_pager);
        indicator = (CircleIndicator) details.findViewById(R.id.property_indicator);
        TextView expire_date = (TextView) details.findViewById(R.id.property_expiry_dtl_date);
        TextView pdetails = (TextView) details.findViewById(R.id.property_dtl_deatils);
        TextView amount = (TextView) details.findViewById(R.id.property_dtl_amt);
        TextView type = (TextView) details.findViewById(R.id.property_dtl_type);
        TextView title = (TextView) details.findViewById(R.id.property_dtl_title);
        TextView mobile = (TextView) details.findViewById(R.id.property_dtl_mobile);
        if (getArguments() != null && getArguments().getString("selectedProp") != null) {
            PropertyModel propertyModel = new Gson().fromJson(getArguments().getString("selectedProp"), PropertyModel.class);
            DetailsActivity.txt_title.setText(propertyModel.getPropeartyTitle());
            expire_date.setText("Exp Date :" + propertyModel.getExpDate());
            amount.setText("Rs " + propertyModel.getAmount() + "/-");
            title.setText(propertyModel.getName());
            type.setText(propertyModel.getType() + "( " + propertyModel.getMode() + " )");
            mobile.setText("mobile :" + propertyModel.getMobile());
            pdetails.setText(propertyModel.getDetails());
            ArrayList<String> urlList = new ArrayList<>();
            urlList.add(propertyModel.getImg1());
            urlList.add(propertyModel.getImg2());
            urlList.add(propertyModel.getImg3());
            urlList.add(propertyModel.getImg4());

            PropertyImageAdapter adapter = new PropertyImageAdapter(getActivity(), urlList);
            mPager.setAdapter(adapter);
            indicator.setViewPager(mPager);
        }
    }
}
