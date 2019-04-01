package com.gathratechnal.nandyalcity.directory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.R;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    public static TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        toolbar.addView(mCustomView);

        txt_title = (TextView) mCustomView.findViewById(R.id.txt_action_bar_title);
        ImageView backButton = (ImageView) mCustomView.findViewById(R.id.iv_action_bar_back);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {/*
            if (bundle.get("selectedAd") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new AdsDetailsFragment(), bundle);
            } else if (bundle.get("selectedDir") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new SubCategoryListFragment(), bundle);
            } else if (bundle.getBoolean("changePassword")) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new ChangePasswordFragment());
            } else if (bundle.getBoolean("profile")) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new ProfileUpdateFragment());
            } else if (bundle.getBoolean("bloodDonate")) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new DonateBloodFragment());
            } else if (bundle.get("selectedRchgeType") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new RechargeFragment(), bundle);
            } *//*else if (bundle.get("rechargeDetails") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new RechargeDetailsFragment(), bundle);
            }*//* else if (bundle.get("selectedProp") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new PropertyDealsFrament(), bundle);
            } else if (bundle.get("selectedPage") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new WebPageDetailsFragment(), bundle);
            } else if (bundle.get("busesList") != null) {
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new BusesListFragment(), bundle);
                DetailsActivity.txt_title.setText("Available Buses");
            } else if (bundle.get("addProperty") != null) {
                DetailsActivity.txt_title.setText("Add Property (Sale/Rent)");
                NavigationHandler.getInstance().navigateToFragment(R.id.frame, getFragmentManager(),
                        getFragmentManager().beginTransaction(), new AddPropertyFragment());
            }*/
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ((getFragmentManager().findFragmentByTag("com.gathratechnal.nandyalcity.History.UserTxnHistoryFragment") != null && getFragmentManager().findFragmentByTag("com.gathratechnal.nandyalcity.History.UserTxnHistoryFragment").isVisible())) {
            setResult(1243);
            finish();
        }else{
            if (getFragmentManager().getBackStackEntryCount()==1){
                finish();
            }else {
            super.onBackPressed();}
        }
    }
}
