package com.gathratechnal.nandyalcity.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.BaseActivity;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.deal.DealActivity;
import com.gathratechnal.nandyalcity.forgotPassword.ForgotPasswordActivity;
import com.gathratechnal.nandyalcity.login.contacts.ContactsAsyncTask;
import com.gathratechnal.nandyalcity.login.contacts.ContactsInterface;
import com.gathratechnal.nandyalcity.login.contacts.UserContactModel;
import com.gathratechnal.nandyalcity.login.gcm.GcmAsyncTask;
import com.gathratechnal.nandyalcity.login.model.LoginModel;
import com.gathratechnal.nandyalcity.register.RegisterActivity;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.NormalizePhoneNumber;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity implements View.OnClickListener,
        LoginInterface {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView forgot_password_textView;

    //Holds contact thread
    private String androidId = "";

    //Holds contact data
    private ArrayList<UserContactModel> contactModelArrayList;

    private boolean isContactsSync = false;
    private AppCompatCheckBox terms_conditions;
    private TextView termsTxt, privacyTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_login);

        mEmailEditText = findViewById(R.id.email_editText);
        mPasswordEditText = findViewById(R.id.password_editText);
        forgot_password_textView = findViewById(R.id.forgot_password_textView);
        terms_conditions = findViewById(R.id.terms_conditions);

        AppCompatButton login_button = findViewById(R.id.login_button);
        AppCompatButton register_button = findViewById(R.id.register_button);
        termsTxt = findViewById(R.id.terms_txt);
        privacyTxt = findViewById(R.id.privacy_txt);

        login_button.setOnClickListener(this);
        register_button.setOnClickListener(this);
        forgot_password_textView.setOnClickListener(this);
        termsTxt.setOnClickListener(this);
        privacyTxt.setOnClickListener(this);

        mEmailEditText.setText("");
        mPasswordEditText.setText("");

        try {
            androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Networking.isNetworkAvailable(this)) {

            try {
                FirebaseApp.initializeApp(this);

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Log.d("Firbase id login", "Refreshed token: " + refreshedToken);
            } catch (Exception e) {
                e.printStackTrace();
            }

            gcmAsyncTask();
        } else {
            showDialog_singleButton(getResources().getString(R.string.no_connection));
        }

        Bundle extras = getIntent().getExtras();

        if (extras  != null){
            if (extras.getBoolean("isLogin")){
                mEmailEditText.setText(extras.getString("phoneNumber"));
                mPasswordEditText.setText(extras.getString("password"));
                login_button.performClick();
            }
        }
    }

    //Gcm
    private void gcmAsyncTask() {
        //Show progress while getting fetching data
        // showProgressDialog();
        try {
            String pushToken = new Preferences().getInstance().getPushToken(this);
            //new Preferences().getPushToken(this);

         /*   try {
                email = getEmail();
            } catch (Exception e){
                e.printStackTrace();
            }*/
            if (!pushToken.isEmpty()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("name", "");
                jsonObject.put("email", "");
                jsonObject.put("mobile", "");
                jsonObject.put("gcm_id", pushToken);

                GcmAsyncTask gcmAsyncTask = new GcmAsyncTask();
                gcmAsyncTask.sendGcmDetails(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Login
    private void loginAsyncTask(JSONObject jsonObject) {
        //Show progress while getting fetching data
        showProgressDialog();

        LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
        loginAsyncTask.delegate = LoginActivity.this;
        loginAsyncTask.getLoginDetails(jsonObject);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.forgot_password_textView:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.terms_txt:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nandyalcity.com/terms.php"));
                startActivity(browserIntent);
                break;
            case R.id.privacy_txt:
                Intent privacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nandyalcity.com/privacypolicy.php"));
                startActivity(privacyIntent);
                break;
            case R.id.login_button:
//                localContacts();

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                //error handling
                boolean cancel = true;
                View focusView = null;

                if (email.isEmpty()) {
                    focusView = mEmailEditText;
                    cancel = true;

                    mEmailEditText.setError(getString(R.string.error_field_required));

                } else if (password.isEmpty()) {
                    focusView = mPasswordEditText;
                    cancel = true;

                    mPasswordEditText.setError(getString(R.string.error_field_required));
                } else if (!terms_conditions.isChecked()) {
                    cancel = false;
                    Toast.makeText(this, "Please select Terms And Conditions", Toast.LENGTH_SHORT).show();
                } else {
                    cancel = false;

                    if (Networking.isNetworkAvailable(this)) {

                        JSONObject mainJsonObject = new JSONObject();

                        try {

                            mainJsonObject.put("email", email);
                            mainJsonObject.put("password", password);

                            loginAsyncTask(mainJsonObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        showDialog_singleButton(getResources().getString(R.string.no_connection));
                    }
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }
                break;

            case R.id.register_button:

                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            default:
                break;


        }
    }

    @Override
    public void onLoginPostExecute(LoginModel loginModel, String message) {

        hideProgressDialog();

        if (loginModel != null) {

            if (loginModel.getResult() !=null && !loginModel.getResult().getNcCode().isEmpty()) {
                new Preferences().saveUserDetails(this, loginModel);
                startActivity(new Intent(LoginActivity.this, DealActivity.class));
                finish();

            } else {
                showDialog_singleButton(getResources().getString(R.string.retry));
            }

        } else {
            try {
                showDialog_singleButton(loginModel.getMessage());
            } catch (Exception e) {
                showDialog_singleButton(getResources().getString(R.string.retry));
            }

        }
    }

}
