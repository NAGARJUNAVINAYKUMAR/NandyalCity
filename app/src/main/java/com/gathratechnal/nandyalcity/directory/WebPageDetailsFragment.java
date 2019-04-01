package com.gathratechnal.nandyalcity.directory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.property.PropertyImageAdapter;
import com.gathratechnal.nandyalcity.webpages.Gallerydata;
import com.gathratechnal.nandyalcity.webpages.Slidesdata;
import com.gathratechnal.nandyalcity.webpages.WebPageModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by arunmididoddy on 1/20/2018.
 */

public class WebPageDetailsFragment extends Fragment {
    private ViewPager mPager, mGalleryPager;
    private CircleIndicator indicator;
    private TextView service, aboutus, contact, home;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.webpage_details_layout, container, false);
        initViews(page);
        if (getArguments() != null) {
            setDetails(getArguments());
        }
        return page;
    }

    private void setDetails(Bundle arguments) {
        if (arguments.containsKey("selectedPage") && arguments.getString("selectedPage") != null) {
            WebPageModel model = new Gson().fromJson(arguments.getString("selectedPage"), WebPageModel.class);
            if (model != null) {
                service.setText(Html.fromHtml(model.getServices().trim()));
                home.setText(Html.fromHtml(model.getHome().trim()));
                contact.setText(Html.fromHtml(model.getContactus().trim()));
                aboutus.setText(Html.fromHtml(model.getAboutus().trim()));
                DetailsActivity.txt_title.setText(model.getWebTitle().trim());
                Slidesdata slide = model.getSlidesdata();
                ArrayList<String> urlList = new ArrayList<>();
                urlList.add(slide.getSlide1());
                urlList.add(slide.getSlide2());
                urlList.add(slide.getSlide3());
                urlList.add(slide.getSlide4());
                Gallerydata gallery = model.getGallerydata();
                ArrayList<String> galleryList = new ArrayList<>();
                galleryList.add(gallery.getGaleryimg1());
                galleryList.add(gallery.getGaleryimg2());
                galleryList.add(gallery.getGaleryimg3());
                galleryList.add(gallery.getGaleryimg4());
                galleryList.add(gallery.getGaleryimg5());
                galleryList.add(gallery.getGaleryimg6());
                galleryList.add(gallery.getGaleryimg7());
                galleryList.add(gallery.getGaleryimg8());
                PropertyImageAdapter adapter = new PropertyImageAdapter(getActivity(), urlList);
                PropertyImageAdapter galleryadap = new PropertyImageAdapter(getActivity(), galleryList);
                mPager.setAdapter(adapter);
                mGalleryPager.setAdapter(galleryadap);
                indicator.setViewPager(mPager);
            }

        }
    }

    private void initViews(View page) {
        mPager = (ViewPager) page.findViewById(R.id.webpage_pager);
        mGalleryPager = (ViewPager) page.findViewById(R.id.webpage_gallery_pager);
        indicator = (CircleIndicator) page.findViewById(R.id.webpage_indicator);
        home = (TextView) page.findViewById(R.id.webpage_homelink);
        service = (TextView) page.findViewById(R.id.webpage_serviceslink);
        aboutus = (TextView) page.findViewById(R.id.webpage_aboutuslink);
        contact = (TextView) page.findViewById(R.id.webpage_contactlink);
    }
}