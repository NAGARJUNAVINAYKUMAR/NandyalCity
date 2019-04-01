package com.gathratechnal.nandyalcity.directory.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.directory.adapter.SubCategoriesListAdapter;
import com.gathratechnal.nandyalcity.directory.model.DirectoryModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class SubCategoryListFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View subCategoryView = inflater.inflate(R.layout.sub_category_layout, container, false);
        recyclerView = (RecyclerView) subCategoryView.findViewById(R.id.sub_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getArguments() != null && getArguments().containsKey("selectedDir")) {
            String selectedCatObj = getArguments().getString("selectedDir");
            ArrayList<DirectoryModel> selectedSub = new ArrayList<>();
            if (selectedCatObj != null) {
                DirectoryModel[] slectedCatList = new Gson().fromJson(selectedCatObj, DirectoryModel[].class);
                if (slectedCatList != null) {
                    for (DirectoryModel directoryModel : slectedCatList) {
                        selectedSub.add(directoryModel);
                        DetailsActivity.txt_title.setText(directoryModel.getSub_cat_name());
                    }
                }

                SubCategoriesListAdapter adapter = new SubCategoriesListAdapter(getActivity(), selectedSub);
                recyclerView.setAdapter(adapter);

            }

        }
        return subCategoryView;
    }
}
