package com.gathratechnal.nandyalcity.webpages;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.BaseFragment;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.ads.AdsAdapter;
import com.gathratechnal.nandyalcity.ads.AdsModel;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;
import com.gathratechnal.nandyalcity.utils.CommonAsyncTask;
import com.gathratechnal.nandyalcity.utils.CommonOnPostExecuteListener;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 1/19/2018.
 */

public class WebPagesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewClickListner clickListner;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.sub_category_layout, container, false);

        recyclerView = (RecyclerView) page.findViewById(R.id.sub_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getWebPagesList();
        clickListner = new RecyclerViewClickListner() {
            @Override
            public void onRecyclerItemClicked(Bundle bundle) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        return page;
    }

    private void getWebPagesList() {
        if (Networking.isNetworkAvailable(getActivity())) {
            try {
                JSONObject object = new JSONObject();
                object.put("nc_code", Preferences.getInstance().getNcCode(getActivity()));
//                object.put("nc_code", "883016003");
                showProgressDialog();
                CommonAsyncTask asyncTask = new CommonAsyncTask();
                asyncTask.listener = new CommonOnPostExecuteListener() {
                    @Override
                    public void onPostExecuteListener(ResponseModel responseModel, String message) {
                        mProgressDialog.dismiss();
                        if (responseModel != null) {
                            showList(responseModel);
                        } else {
                            Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                asyncTask.getWebPages(object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void showList(ResponseModel response) {
        String element = new Gson().toJson(response.getResult(),
                new TypeToken<ArrayList<Object>>() {
                }.getType());

        ArrayList<WebPageModel> pagesList = new ArrayList<>();
        WebPageModel[] webPageModels = new Gson().fromJson(element, WebPageModel[].class);
        if (webPageModels != null) {
            for (WebPageModel ad : webPageModels) {
                pagesList.add(ad);
            }
        }
        WebPagesAdapter adapter = new WebPagesAdapter(getActivity(), pagesList, clickListner);
        recyclerView.setAdapter(adapter);

    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.please_wait));
        }

        mProgressDialog.show();
    }
}
