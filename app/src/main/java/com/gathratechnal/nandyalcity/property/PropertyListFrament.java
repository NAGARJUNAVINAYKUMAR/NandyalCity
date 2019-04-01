package com.gathratechnal.nandyalcity.property;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.property.model.PropertyModel;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arunmididoddy on 1/6/2018.
 */

public class PropertyListFrament extends Fragment {
    private RecyclerView properyListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View propertiesView = inflater.inflate(R.layout.sub_category_layout, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        properyListView = (RecyclerView) propertiesView.findViewById(R.id.sub_category);
        properyListView.setLayoutManager(layoutManager);
        getPropertiesList();
        return propertiesView;
    }

    private void getPropertiesList() {
       /* final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait..");
        mProgressDialog.show();
        try {
            JSONObject object = new JSONObject();
            object.put("nc_code", Preferences.getInstance().getNcCode(getActivity()));
            object.put("max_amt", "");
            object.put("min_amt", "");


            HomeSlidesAsyncTask getPropertiesAsyncTask = new HomeSlidesAsyncTask();
            getPropertiesAsyncTask.listner = new HomeSlideInterface() {
                @Override
                public void onHomeSlidePostExecute(ResponseModel message, String msg) {
                    mProgressDialog.dismiss();
                    if (message != null && message.getStatusCode() == 200) {
                        showPropertiesList(message.getResult());

                    } else {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }

                }
            };
            getPropertiesAsyncTask.getPropertiesList(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            mProgressDialog.dismiss();
        }*/
    }


    private void showPropertiesList(List<Object> result) {
        if (result != null) {
            String element = new Gson().toJson(result,
                    new TypeToken<ArrayList<Object>>() {
                    }.getType());
            ArrayList<PropertyModel> propertyList = new ArrayList<>();

            PropertyModel[] propertyModels = new Gson().fromJson(element, PropertyModel[].class);
            if (propertyModels != null) {
                for (PropertyModel slide : propertyModels) {
                    propertyList.add(slide);
                }


                PropertyListAdapter adapter = new PropertyListAdapter(getActivity(), propertyList,false);
                properyListView.setAdapter(adapter);
            }
        }
    }
}
