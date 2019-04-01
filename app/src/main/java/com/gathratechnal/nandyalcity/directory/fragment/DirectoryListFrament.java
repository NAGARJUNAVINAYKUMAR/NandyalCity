package com.gathratechnal.nandyalcity.directory.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.CategoryListAsyncTaskInterface;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.directory.adapter.DirectoryExpandableListAdapter;
import com.gathratechnal.nandyalcity.directory.model.DirectoryModel;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class DirectoryListFrament extends Fragment {
    private ProgressDialog mProgressDialog;
    private ExpandableListView cat_listview;
    private LinkedHashMap<String, List<DirectoryModel>> parentMap;
    private String nc_code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View directoryView = inflater.inflate(R.layout.directory_screen_layout, container, false);
        cat_listview = (ExpandableListView) directoryView.findViewById(R.id.lv_categories);
        nc_code = Preferences.getInstance().getNcCode(getActivity());
        makeDirectoryServiceCall(nc_code);
        //HomeActivity.getInstance().setMainTitle("Directory");
        cat_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                if (parentMap != null) {
                    Object[] keys = parentMap.keySet().toArray();
                    String sub_cat_id = parentMap.get(keys[i].toString()).get(i1).getDirectory_subcatid();
                    showSubCatList(sub_cat_id);
                }

                return false;
            }
        });
        return directoryView;
    }

    private void showSubCatList(String subCatId) {
        if (Networking.isNetworkAvailable(getActivity())) {
            JSONObject mainJsonObject = new JSONObject();
            try {
                mainJsonObject.put("nc_code", nc_code);
                mainJsonObject.put("category", "");
                mainJsonObject.put("sub_category", subCatId);
                getSelectedSubCatList(mainJsonObject);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void getSelectedSubCatList(JSONObject jsonObject) {/*
        //Show progress while getting fetching data
        showProgressDialog();

        CategoryListAsyncTask categoryListAsyncTask = new CategoryListAsyncTask();
        categoryListAsyncTask.listner = new CategoryListAsyncTaskInterface() {
            @Override
            public void onCategoryPostExecute(ResponseModel response, String msg) {
                hideProgressBar();
                if (response != null && response.getResult() != null && response.getResult().size() > 0) {
                    String subCatList = new Gson().toJson(response.getResult(),
                            new TypeToken<ArrayList<Object>>() {
                            }.getType());
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedDir", subCatList);
                    moveToFragment(bundle);

                }else{
                    Toast.makeText(getActivity(), "Please Try Again..", Toast.LENGTH_SHORT).show();
                }
            }
        };
        categoryListAsyncTask.getCategoryList(jsonObject);*/
    }

    private void hideProgressBar() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void categoryListAsyncTask(JSONObject jsonObject) {
      /*  //Show progress while getting fetching data
        showProgressDialog();

        CategoryListAsyncTask categoryListAsyncTask = new CategoryListAsyncTask();
        categoryListAsyncTask.listner = new CategoryListAsyncTaskInterface() {
            @Override
            public void onCategoryPostExecute(ResponseModel response, String msg) {
                hideProgressBar();
                showDirectoryList(response);
            }
        };
        categoryListAsyncTask.getCategoryList(jsonObject);*/
    }

    private void showDirectoryList(ResponseModel response) {
        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            String responseJson = new Gson().toJson(response.getResult(),
                    new TypeToken<ArrayList<Object>>() {
                    }.getType());
            if (responseJson != null) {

                DirectoryModel[] category = new Gson().fromJson(responseJson, DirectoryModel[].class);

                ArrayList<DirectoryModel> tempParentCategoryList = new ArrayList<>();

                parentMap = new LinkedHashMap<>();

                LinkedHashSet<String> main_cat_list = new LinkedHashSet<>();
                if (category != null) {
                    for (int i = 0; i < category.length; i++) {
                        tempParentCategoryList.add(category[i]);
                        main_cat_list.add(category[i].getCat_name());
                    }
                }
                List<String> mainDirList = new ArrayList<String>(main_cat_list);

                ArrayList<DirectoryModel> wholeList;
                for (int i = 0; i < mainDirList.size(); i++) {
                    wholeList = new ArrayList<>();
                    for (int j = 0; j < tempParentCategoryList.size(); j++) {
                        if (mainDirList.get(i).equalsIgnoreCase(tempParentCategoryList.get(j).getCat_name())) {
                            if (!isDirExits(tempParentCategoryList.get(j).getSub_cat_name(), wholeList)) {
                                wholeList.add(tempParentCategoryList.get(j));
                            }
                        }
                    }

                    parentMap.put(mainDirList.get(i), wholeList);
                }


                DirectoryExpandableListAdapter adapter = new DirectoryExpandableListAdapter(getActivity(), parentMap);
                cat_listview.setAdapter(adapter);
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.please_wait));
        }

        mProgressDialog.show();
    }


    private boolean isDirExits(String cat_name, ArrayList<DirectoryModel> directoryList) {
        for (int i = 0; i < directoryList.size(); i++) {
            if (cat_name.equalsIgnoreCase(directoryList.get(i).getSub_cat_name())) {
                return true;
            }
        }
        return false;
    }


    private void makeDirectoryServiceCall(String nc_code) {
        if (Networking.isNetworkAvailable(getActivity())) {
            JSONObject mainJsonObject = new JSONObject();
            try {
                mainJsonObject.put("nc_code", nc_code);
                mainJsonObject.put("category", "");
                mainJsonObject.put("sub_category", "");
                categoryListAsyncTask(mainJsonObject);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToFragment(Bundle bundle) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


}
