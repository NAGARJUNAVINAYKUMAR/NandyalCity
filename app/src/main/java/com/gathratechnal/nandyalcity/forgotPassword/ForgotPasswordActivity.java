package com.gathratechnal.nandyalcity.forgotPassword;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.BaseActivity;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

import org.json.JSONObject;


public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordInterface{

    private Toolbar mainToolbar;
    private TextView mToolbarTitleTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        configureToolbar();


        final EditText email_editText =  findViewById(R.id.email_editText);
        Button submit_button = findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phno = email_editText.getText().toString();

                if (phno.isEmpty()){
                    showDialog_singleButton("Please Enter Mobile No");
                } else {
                    if (Networking.isNetworkAvailable(ForgotPasswordActivity.this)) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("mobile", phno);

                        getLocationAsyncTask(jsonObject);

                     } catch (Exception e){

                    }

                    } else {
                        showDialog_singleButton(getResources().getString(R.string.no_connection));
                    }
                }
            }
        });
    }

    private void configureToolbar() {
        mainToolbar = findViewById(R.id.toolbar);

        if (mainToolbar != null) {
            mToolbarTitleTextView = mainToolbar.findViewById(R.id.toolbar_title_textView);
            mToolbarTitleTextView.setText("Forgot Password");
            setSupportActionBar(mainToolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Location
    private void getLocationAsyncTask(JSONObject jsonObject) {
        //Show progress while getting fetching data
        showProgressDialog();

        ForgotPasswordAsyncTask forgotPasswordAsyncTask = new ForgotPasswordAsyncTask();
        forgotPasswordAsyncTask.delegate = ForgotPasswordActivity.this;
        forgotPasswordAsyncTask.getForgotPasswordDetails(jsonObject);
    }

    @Override
    public void onForgotPasswordPostExecute(ResponseModel responseModel, String message) {

        hideProgressDialog();
        if (responseModel != null && responseModel.getMessage() != null && !responseModel.getMessage().isEmpty()){
            finish();
        } else {
            try{
                showDialog_singleButton(responseModel.getMessage());

            } catch (Exception e){
                showDialog_singleButton(getResources().getString(R.string.retry));
            }
        }
    }
}
