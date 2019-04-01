package com.gathratechnal.nandyalcity.deal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.BaseActivity;

import com.gathratechnal.nandyalcity.BuildConfig;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.ads.AdsAdapter;
import com.gathratechnal.nandyalcity.ads.AdsModel;
import com.gathratechnal.nandyalcity.deal.model.DealModel;
import com.gathratechnal.nandyalcity.deal.versionModel.VersionModel;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.directory.RecyclerViewClickListner;
import com.gathratechnal.nandyalcity.login.LoginActivity;
import com.gathratechnal.nandyalcity.login.contacts.ContactsAsyncTask;
import com.gathratechnal.nandyalcity.login.contacts.ContactsInterface;
import com.gathratechnal.nandyalcity.login.contacts.UserContactModel;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.NormalizePhoneNumber;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class DealActivity extends BaseActivity implements View.OnClickListener,
        DealInterface, VersionInterface,ContactsInterface {

    private Toolbar mainToolbar;
    private TextView mToolbarTitleTextView;

    private DealModel mDealModel;

    private RecyclerView mDealRecyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewClickListner clickListner;
    boolean isFromHome;

    //Holds normalize phone object
    private NormalizePhoneNumber normalizePhoneNumber;
    private FetchContactsTask mFetchContactsTask;

    //Holds contact data
    private ArrayList<UserContactModel> contactModelArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);

        configureToolbar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFromHome = bundle.getBoolean("fromHome");
        }
        mDealRecyclerView = findViewById(R.id.deal_recyclerView);
        mDealRecyclerView.setHasFixedSize(true);
        mDealRecyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        mDealRecyclerView.setLayoutManager(layoutManager);

        TextView continueTextView = findViewById(R.id.continueTextView);

        continueTextView.setOnClickListener(this);

        if (Networking.isNetworkAvailable(this)) {
            getLocationAsyncTask();
            getVersionAsyncTask();
        } else {
            showDialog_singleButton(getResources().getString(R.string.no_connection));
        }

        clickListner = new RecyclerViewClickListner() {
            @Override
            public void onRecyclerItemClicked(Bundle bundle) {
                Intent intent = new Intent(DealActivity.this, DetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        };

        isContactsPermissionGranted();
    }

    private void configureToolbar() {
        mainToolbar = findViewById(R.id.toolbar);

        if (mainToolbar != null) {
            mToolbarTitleTextView = mainToolbar.findViewById(R.id.toolbar_title_textView);
            mToolbarTitleTextView.setText("Nandyal City Business Offers");
            setSupportActionBar(mainToolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    //Location
    private void getLocationAsyncTask() {
        //Show progress while getting fetching data
        showProgressDialog();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nc_code", Preferences.getInstance().getNcCode(this));

            DealAsyncTask locationAsyncTask = new DealAsyncTask();
            locationAsyncTask.delegate = DealActivity.this;
            locationAsyncTask.getLocationDetails(jsonObject);

        } catch (Exception e) {
            //DO Nothing
        }
    }

    private void getVersionAsyncTask() {
        //Show progress while getting fetching data
        showProgressDialog();

        VersionAsyncTask versionAsyncTask = new VersionAsyncTask();
        versionAsyncTask.delegate = DealActivity.this;
        versionAsyncTask.getVersionDetails();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.continueTextView:


                break;

            default:

                break;
        }
    }

    @Override
    public void onLocationPostExecute(DealModel locationModel, String message) {
        hideProgressDialog();

        if (locationModel != null) {
            String element = new Gson().toJson(locationModel.getResult(),
                    new TypeToken<ArrayList<Object>>() {
                    }.getType());

            ArrayList<AdsModel> adsList = new ArrayList<>();

            AdsModel[] ads = new Gson().fromJson(element, AdsModel[].class);
            if (ads != null) {
                for (AdsModel ad : ads) {
                    adsList.add(ad);
                }
            }
            AdsAdapter adapter = new AdsAdapter(this, adsList, clickListner);
            mDealRecyclerView.setAdapter(adapter);
        } else {
            try {
                showDialog_singleButton(locationModel.getMessage());
            } catch (Exception e) {
                showDialog_singleButton(getResources().getString(R.string.retry));
            }
        }
    }

    Dialog fbDialogue;

    public void showPopup() {

        fbDialogue = new Dialog(DealActivity.this, android.R.style.Theme_Black_NoTitleBar);
        fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        fbDialogue.setContentView(R.layout.popup_version_update_layout);

        fbDialogue.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    finish();
                return false;
            }
        });

        TextView updateTextView = fbDialogue.findViewById(R.id.update_textView);
        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.gathratechnal.nandyalcity")));


            }
        });

        TextView update_later_textView = fbDialogue.findViewById(R.id.update_later_textView);
        update_later_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbDialogue.dismiss();
            }
        });

        //update_later_textView

        fbDialogue.setCancelable(false);
        fbDialogue.show();

    }

    @Override
    public void onVersionPostExecute(VersionModel locationModel, String message) {

        if (locationModel != null) {

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;

            if (locationModel.getResult() != null &&
                    !versionName.equals(locationModel.getResult().get(0).getVersion())) {
                if (!isFromHome) {
                    showPopup();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isFromHome) {

        }
    }

    //Contacts
    private void sendContactsAsyncTask(JSONArray jsonArray) {
        //Show progress while getting fetching data
        //showProgressDialog();

        ContactsAsyncTask contactsAsyncTask = new ContactsAsyncTask();
        contactsAsyncTask.delegate = this;
        contactsAsyncTask.sendContactsDetails(jsonArray);
    }

    public  void isContactsPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);

            }
        } else {

            //permission is automatically granted on sdk<23 upon installation
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Log.v("","Permission: "+permissions[0]+ "was "+grantResults[0]);

                if (Networking.isNetworkAvailable(this)) {

                    mFetchContactsTask = new FetchContactsTask();
                    mFetchContactsTask.execute();

                } else {
                    showDialog_singleButton(getResources().getString(R.string.no_connection));
                }
            } else {

                onBackPressed();
        }
    }}

    private void getPhoneNumber(String id, String name, String photo, int isStarred) {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = '" + id + "'", null, null);

        if (cursor != null) {

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    String phone = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                    if (!TextUtils.isEmpty(phone)) {
                        UserContactModel contactModel = new UserContactModel();

                        contactModel.setId(id);
                        contactModel.setName(name);
                        contactModel.setPhone(phone);
                        contactModel.setNormalizedPhone(normalize(phone));
                        contactModel.setIsMobileNumber(
                                normalizePhoneNumber.isMobileNumber() ? 1 : 0);
                        contactModel.setPhoto(photo);
                        contactModel.setIsStaredContact(isStarred);

                        //If normalized number is empty, it represents number is not valid
                        if (!TextUtils.isEmpty(contactModel.getNormalizedPhone())) {
                            setContactModel(contactModel);
                        }
                    }
                }
            }

            cursor.close();
        }
    }

    private void setContactModel(UserContactModel contactModel) {
        this.contactModelArrayList.add(contactModel);

    }

    @Override
    public void onContactsPostExecute(ResponseModel contactsResponseModel, String message) {

    }

    private boolean getContacts() {
        contactModelArrayList = new ArrayList<>();

        try {
            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                            ContactsContract.Contacts.PHOTO_URI,
                            ContactsContract.Contacts.HAS_PHONE_NUMBER,
                            ContactsContract.Contacts.STARRED}, null, null, null);

            if (cursor != null) {

                if (cursor.getCount() > 0) {
                    while (!mFetchContactsTask.isCancelled() && cursor.moveToNext()) {
                        if (Integer.valueOf(cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            String id = cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.Contacts._ID));
                            String name = cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                            String photo = cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.Contacts.PHOTO_URI));
                            int isStarred = cursor.getInt(cursor.getColumnIndex(
                                    ContactsContract.Contacts.STARRED));

                            getPhoneNumber(id, name, photo, isStarred);

                        }

                    }
                }

                cursor.close();
            }
        } catch (Exception ignore) {
        }

        return !mFetchContactsTask.isCancelled();
    }

    //Normalize a phone number
    private String normalize(String phone) {
        if (normalizePhoneNumber == null) {
            normalizePhoneNumber = new NormalizePhoneNumber();
        }

        return normalizePhoneNumber.normalizePhone(phone, "");
    }

    public class FetchContactsTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            //Get contacts
            if (getContacts()
                    && (contactModelArrayList != null && contactModelArrayList.size() > 0)) {
                //Save retrieved contacts into database
            /*    SqlContactHelper.getInstance().insertContacts(GetContactsService.this,
                        contactModelArrayList, contentValueArrayList);*/
                // Log.v("LoginContacts", "Contacts : " + contactModelArrayList.toString());

                try {
                    JSONArray contactsJsonArray = new JSONArray();

                    for (int i = 0; i < contactModelArrayList.size(); i++) {
                        JSONObject contactObject = new JSONObject();

                        contactObject.put("email", Preferences.getInstance().getUserEmail(DealActivity.this));
                        contactObject.put("mobile_id", Preferences.getInstance().getUserMobile(DealActivity.this));
                        contactObject.put("name", contactModelArrayList.get(i).getName());
                        contactObject.put("one", contactModelArrayList.get(i).getPhone().replace("-", "").replace(" ", ""));
                        //contactObject.put("two",contactModelArrayList.get(i).getNormalizedPhone());

                        contactsJsonArray.put(contactObject);

                    }
                    sendContactsAsyncTask(contactsJsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            //Notify listeners got contacts from mobile
            //notifyListener();

            //Stop service
            // stopSelf();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            mFetchContactsTask = new FetchContactsTask();
            mFetchContactsTask.execute();

        }
    }
}
