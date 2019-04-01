package com.gathratechnal.nandyalcity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.gathratechnal.nandyalcity.dialog.DialogFragment_SingleButton;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void showDialog_singleButton(String message) {
        DialogFragment_SingleButton newFragment = DialogFragment_SingleButton.newInstance(
                message);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.please_wait));
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
