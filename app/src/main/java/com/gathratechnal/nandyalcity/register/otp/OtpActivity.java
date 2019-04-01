package com.gathratechnal.nandyalcity.register.otp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.BaseActivity;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.login.LoginActivity;
import com.gathratechnal.nandyalcity.register.RegisterActivity;
import com.gathratechnal.nandyalcity.register.RegisterAsyncTask;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by praveenkumar on 4/3/18 12:57 AM.
 */
public class OtpActivity extends BaseActivity implements OtpInterface {

    private Button submit_button;
    private EditText otp_editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);
        configureToolbar();
        EditText mobile_editText = findViewById(R.id.mobile_editText);
        mobile_editText.setText(getIntent().getStringExtra("phoneNumber"));
        submit_button = findViewById(R.id.submit_button);
        otp_editText =  findViewById(R.id.otp_editText);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otp_editText.getText().toString().isEmpty()){
                  showDialog_singleButton("Plese enter otp");
                } else {

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("mobile",getIntent().getStringExtra("phoneNumber"));
                        jsonObject.put("otp",otp_editText.getText().toString());

                    OtpAsyncTask(jsonObject);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private Toolbar mainToolbar;
    private TextView mToolbarTitleTextView;

    private void configureToolbar() {
        mainToolbar = findViewById(R.id.toolbar);

        if (mainToolbar != null) {
            mToolbarTitleTextView = mainToolbar.findViewById(R.id.toolbar_title_textView);
            mToolbarTitleTextView.setText("Otp Verification");
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
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Login
    private void OtpAsyncTask(JSONObject jsonObject) {
        //Show progress while getting fetching data
        showProgressDialog();
        OTPAsyncTask otpAsyncTask = new OTPAsyncTask();
        otpAsyncTask.delegate = OtpActivity.this;
        otpAsyncTask.sendOtpDetails(jsonObject);
    }


    @Override
    public void onOtpPostExecute(ResponseModel responseModel, String message) {
        hideProgressDialog();
        if (responseModel!= null && responseModel.getStatus().equals("success")){

            Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
            intent.putExtra("isLogin",true);
            intent.putExtra("phoneNumber",            getIntent().getStringExtra("phoneNumber"));
            intent.putExtra("password",            getIntent().getStringExtra("password"));
            startActivity(intent);

            finish();
        } else{
            showDialog_singleButton(responseModel.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OtpActivity.this, RegisterActivity.class));
        finish();
    }
}
