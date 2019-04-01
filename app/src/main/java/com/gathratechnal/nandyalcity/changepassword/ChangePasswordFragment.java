package com.gathratechnal.nandyalcity.changepassword;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.directory.DetailsActivity;
import com.gathratechnal.nandyalcity.login.LoginActivity;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by arunmididoddy on 12/26/2017.
 */

public class ChangePasswordFragment extends Fragment implements View.OnClickListener, ChangePasswordListener {
    private EditText mNewPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private AppCompatButton mUpdateButton;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View passwordView = inflater.inflate(R.layout.change_password, container, false);
        initializeViews(passwordView);
        setTitle();

        return passwordView;

    }

    private void setTitle() {
        DetailsActivity.txt_title.setText(getActivity().getResources().getString(R.string.change_password));
    }

    private void initializeViews(View passwordView) {
        mNewPasswordEditText = passwordView.findViewById(R.id.et_new_password);
        mConfirmPasswordEditText = passwordView.findViewById(R.id.et_confirm_password);
        mUpdateButton = passwordView.findViewById(R.id.password_change_button);
        mUpdateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.password_change_button) {
            String newPassword = mNewPasswordEditText.getText().toString();
            String confirmPassword = mConfirmPasswordEditText.getText().toString();
            if (mNewPasswordEditText.getText().toString().isEmpty()) {
                mNewPasswordEditText.setError("cannot be empty..!");
            } else if (mConfirmPasswordEditText.getText().toString().isEmpty()) {
                mConfirmPasswordEditText.setError("cannot be empty..!");
            } else if (newPassword.length() >= 6 || confirmPassword.length() > 6) {
                if (newPassword.equalsIgnoreCase(confirmPassword)) {
                    makeServiceCall(confirmPassword);
                } else {
                    Toast.makeText(getActivity(), "Passwords did not match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Min Password length is 6", Toast.LENGTH_SHORT).show();
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

    private void makeServiceCall(String confirmPassword) {

        String nc_code = Preferences.getInstance().getNcCode(getActivity());
        try {
            JSONObject passWordObj = new JSONObject();
            passWordObj.put("nc_code", nc_code);
            passWordObj.put("password", confirmPassword);
            showProgressDialog();
            ChangePasswordAsyncTask changePasswordAsyncTask = new ChangePasswordAsyncTask();
            changePasswordAsyncTask.listener = this;
            changePasswordAsyncTask.getPasswordChanged(passWordObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPasswordChangePostExecute(ResponseModel responseModel, String message) {
        mProgressDialog.dismiss();
        if (message.equalsIgnoreCase("success")) {

            Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
            logoutUser();
        } else {
            Toast.makeText(getActivity(), "Password Changed Failed, Please try Again", Toast.LENGTH_SHORT).show();
        }

    }

    private void logoutUser() {
        SharedPreferences mPreferences = getActivity().getSharedPreferences("CurrentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove("UserName");
        editor.remove("PassWord");
        editor.commit();
        Message myMessage = new Message();
        myMessage.obj = "NOTSUCCESS";
        handler.sendMessage(myMessage);
        Preferences.getInstance().deleteUserDetails(getActivity());
        getActivity().finish();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String loginmsg = (String) msg.obj;
            if (loginmsg.equals("NOTSUCCESS")) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("LoginMessage", "Logged Out");
                startActivity(intent);
            }
        }
    };
}
