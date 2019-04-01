package com.gathratechnal.nandyalcity.ads;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.google.gson.Gson;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class AdsDetailsFragment extends Fragment implements View.OnClickListener {

    private String webUrl, appUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adsDetails = inflater.inflate(R.layout.ads_details_fragment, container, false);


        TextView title = (TextView) adsDetails.findViewById(R.id.txt_ad_dtl_title);
        TextView expire_date = (TextView) adsDetails.findViewById(R.id.txt_expiry_dtl_date);
        TextView details = (TextView) adsDetails.findViewById(R.id.txt_ad__dtl_deatils);
        TextView mobile = (TextView) adsDetails.findViewById(R.id.txt_dtl_mobile);
        ImageView ad_img = (ImageView) adsDetails.findViewById(R.id.ad_dtl_image);
        Button ad_web = (Button) adsDetails.findViewById(R.id.ib_web);
        Button ad_app = (Button) adsDetails.findViewById(R.id.ib_app);
        if (getArguments() != null && getArguments().getString("selectedAd") != null) {
            AdsModel ads = new Gson().fromJson(getArguments().getString("selectedAd"), AdsModel.class);
            if (ads.getAdd_title() !=null) {
                DetailsActivity.txt_title.setText(ads.getAdd_title());
            }else{
                DetailsActivity.txt_title.setText(ads.getDeal_title());
            }
            expire_date.setText("Ends : " + ads.getExp_date());
            details.setText(ads.getDetails());
            mobile.setText(ads.getMobile() != null && !ads.getMobile().isEmpty() ? "Mobile : " + ads.getMobile() : "");
            if (ads.getWeb_url() != null && !ads.getWeb_url().equals("0")) {
                ad_web.setVisibility(View.VISIBLE);
                webUrl = ads.getWeb_url();
            } else {
                ad_web.setVisibility(View.GONE);
            }


            if (ads.getApp_url() != null && !ads.getApp_url().equals("0")) {
                ad_app.setVisibility(View.VISIBLE);
                appUrl = ads.getApp_url();
            } else {
                ad_app.setVisibility(View.GONE);
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.drawable.ic_launcher);



            Glide.with(getActivity())
                    .setDefaultRequestOptions(requestOptions)
                    .load(ads.getApp_image())
                    .into(ad_img);

        }
        ad_app.setOnClickListener(this);

        return adsDetails;

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_app) {
            if (appUrl != null && !appUrl.isEmpty())
                openInBrowser(appUrl);

        } else if (view.getId() == R.id.ib_web) {
            if (webUrl != null && !webUrl.isEmpty())
                openInBrowser(webUrl);
        }
    }

    private void openInBrowser(String url) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "No apps perform this action", Toast.LENGTH_SHORT).show();
        }

    }
}
