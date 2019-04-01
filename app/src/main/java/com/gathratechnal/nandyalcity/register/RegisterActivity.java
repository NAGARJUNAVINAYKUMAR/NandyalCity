package com.gathratechnal.nandyalcity.register;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.BaseActivity;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.dialog.DialogFragment_SingleButton;
import com.gathratechnal.nandyalcity.login.LoginActivity;
import com.gathratechnal.nandyalcity.register.otp.OtpActivity;
import com.gathratechnal.nandyalcity.utils.NavigationHandler;
import com.gathratechnal.nandyalcity.utils.Networking;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterInterface {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mMobileEditText;
    private EditText mDobEditText;
    private AppCompatButton mRegisterButton;
    private TextView alreadyregister;

    private Toolbar mainToolbar;
    private TextView mToolbarTitleTextView;

    final Calendar myCalendar = Calendar.getInstance();

    private AppCompatCheckBox terms_conditions;
    private TextView termsTxt, privacyTxt;
    private Button addProfilrImg;
    private CircleImageView profileImage;
    private byte[] imageData;

    EditText password_editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_register);

        configureToolbar();
        termsTxt = findViewById(R.id.terms_txt);
        privacyTxt = findViewById(R.id.privacy_txt);
        mNameEditText = findViewById(R.id.name_editText);
        mEmailEditText = findViewById(R.id.email_editText);
        mMobileEditText = findViewById(R.id.mobile_editText);
        terms_conditions = findViewById(R.id.terms_conditions);
        profileImage = findViewById(R.id.profileImage);
        addProfilrImg = findViewById(R.id.register_add_prfl_img);
        mDobEditText = findViewById(R.id.dob_editText);
        mRegisterButton = findViewById(R.id.register_button);
        alreadyregister = findViewById(R.id.already_register_textView);

        password_editText = findViewById(R.id.r_password_editText);

        alreadyregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        mRegisterButton.setOnClickListener(this);
        addProfilrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.getInstance().selectImage(RegisterActivity.this);
            }
        });
        profileImage.setVisibility(View.GONE);
        addProfilrImg.setVisibility(View.GONE);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.getInstance().selectImage(RegisterActivity.this);
            }
        });
        privacyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nandyalcity.com/privacypolicy.php"));
                startActivity(browserIntent);
            }
        });
        termsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nandyalcity.com/terms.php"));
                startActivity(browserIntent);
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        mDobEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDobEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void configureToolbar() {
        mainToolbar = findViewById(R.id.toolbar);

        if (mainToolbar != null) {
            mToolbarTitleTextView = mainToolbar.findViewById(R.id.toolbar_title_textView);
            mToolbarTitleTextView.setText(getString(R.string.register));
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

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
    }

    //Login
    private void RegisterAsyncTask(JSONObject jsonObject) {
        //Show progress while getting fetching data
        showProgressDialog();

        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask();
        registerAsyncTask.delegate = RegisterActivity.this;
        registerAsyncTask.sendRegisterDetails(jsonObject);
    }

    String mobile;
    String password;

    @Override
    public void onClick(View view) {

        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        mobile = mMobileEditText.getText().toString();
        password = password_editText.getText().toString();

//        String dob = mDobEditText.getText().toString();

        //error handling
        boolean cancel;
        View focusView = null;
        if (name.isEmpty()) {
            focusView = mNameEditText;
            cancel = true;

            mNameEditText.setError(getString(R.string.error_field_required));

        } else if (password.isEmpty()){
            focusView = password_editText;
            cancel = true;

            password_editText.setError(getString(R.string.error_field_required));
        } else if (email.isEmpty()) {
            focusView = mEmailEditText;
            cancel = true;

            mEmailEditText.setError(getString(R.string.error_field_required));
        } else if (mobile.isEmpty()) {
            focusView = mMobileEditText;
            cancel = true;

            mMobileEditText.setError(getString(R.string.error_field_required));
        } else if (!terms_conditions.isChecked()) {
            cancel = false;
            Toast.makeText(this, "Please select Terms And Conditions", Toast.LENGTH_SHORT).show();
        } else {
            cancel = false;

            if (Networking.isNetworkAvailable(this)) {

                JSONObject mainJsonObject = new JSONObject();

                try {

                    mainJsonObject.put("name", name);
                    mainJsonObject.put("email", email);
                    mainJsonObject.put("mobile", mobile);
                    mainJsonObject.put("password", password);

//                    if (imageData != null) {
//                        mainJsonObject.put("profilepic", imageData);
//                        RegisterAsyncTask(mainJsonObject);
//                    }else{
//                        Toast.makeText(this, "Please Add Profile Image", Toast.LENGTH_SHORT).show();
//                    }

                    RegisterAsyncTask(mainJsonObject);

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

    }

    @Override
    public void onRegisterPostExecute(ResponseModel responseModel, String message) {

        hideProgressDialog();
        if (responseModel != null && responseModel.getStatus().equals("success")) {

            Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
            intent.putExtra("phoneNumber",mobile);
            intent.putExtra("password",password);
            startActivity(intent);
            finish();
        } else {
            try {
                showDialog_singleButton(responseModel.getMessage() != null && !responseModel.getMessage().isEmpty() ?
                        responseModel.getMessage() : getResources().getString(R.string.retry));
            } catch (Exception e) {

            }
        }
    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

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


            }
        } else {

            onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 5432 && NavigationHandler.getInstance().getBitMapFromGalleryResult(this, data) != null) {
                try {
                    imageData = NavigationHandler.getInstance().getBytes(this, data);
                    profileImage.setImageBitmap(NavigationHandler.getInstance().getBitMapFromGalleryResult(this, data));
                } catch (Exception e) {
                    e.printStackTrace();
                    DialogFragment_SingleButton.newInstance("Error fetching Image..try another Image");
                }

            } else if (requestCode == 3214 && NavigationHandler.getInstance().getCapturedImage(data) != null) {
                try {
                    imageData = NavigationHandler.getInstance().getBytes(this, data);
                    profileImage.setImageBitmap(NavigationHandler.getInstance().getCapturedImage(data));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}

