package com.gathratechnal.nandyalcity.ads;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.CategoryListAsyncTaskInterface;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class AdsFragment extends Fragment implements CategoryListAsyncTaskInterface {

    private RecyclerView recyclerView;
    private ProgressDialog mProgressDialog;
    private RecyclerViewClickListner clickListner;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ads = inflater.inflate(R.layout.ads_layout, container, false);
        String nc_code = Preferences.getInstance().getNcCode(getActivity());

        recyclerView = (RecyclerView) ads.findViewById(R.id.ads_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        makeDirectoryServiceCall(nc_code);
        return ads;
    }

    private void makeDirectoryServiceCall(String nc_code) {
        if (Networking.isNetworkAvailable(getActivity())) {
            JSONObject mainJsonObject = new JSONObject();
            try {
                mainJsonObject.put("nc_code", nc_code);
                categoryListAsyncTask(mainJsonObject);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void categoryListAsyncTask(JSONObject jsonObject) {
        //Show progress while getting fetching data
        showProgressDialog();

        AdsAsyncTask categoryListAsyncTask = new AdsAsyncTask();
        categoryListAsyncTask.listner = this;
        categoryListAsyncTask.getAdsList(jsonObject);
        clickListner = new RecyclerViewClickListner() {
            @Override
            public void onRecyclerItemClicked(Bundle bundle) {
                moveToAdsDetailsFragment(bundle);
            }
        };
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.please_wait));
        }

        mProgressDialog.show();
    }

    @Override
    public void onCategoryPostExecute(ResponseModel response, String msg) {
        mProgressDialog.dismiss();
        if (response != null) {
            String element = new Gson().toJson(response.getResult(),
                    new TypeToken<ArrayList<Object>>() {
                    }.getType());

            ArrayList<AdsModel> adsList = new ArrayList<>();

            AdsModel[] ads = new Gson().fromJson(element, AdsModel[].class);
            if (ads != null) {
                for (AdsModel ad : ads) {
                    adsList.add(ad);
                }
            }
            AdsAdapter adapter = new AdsAdapter(getActivity(), adsList, clickListner);
            recyclerView.setAdapter(adapter);

        }
    }

    private void moveToAdsDetailsFragment(Bundle bundle) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}