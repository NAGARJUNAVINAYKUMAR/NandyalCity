package com.gathratechnal.nandyalcity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.gathratechnal.nandyalcity.login.model.LoginModel;
import com.gathratechnal.nandyalcity.login.model.LoginResult;

/**
 * Created by praveenkumar on 12/6/17 11:09 PM.
 */

public class Preferences {

    //Class reference
    private static Preferences preferenceManager = null;

    public static Preferences getInstance() {
        if (preferenceManager == null) {
            preferenceManager = new Preferences();
        }

        return preferenceManager;
    }


    private final String USER_DETAILS = "USER_DETAILS";
    private final String fcmToken = "PUSH_TOKEN";
    private final String LOCATION_TOKEN = "LOCATION_TOKEN";


    public void saveUserDetails(Context context, LoginModel loginModel) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences sp = context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", loginModel.getResult().getId());
        editor.putString("nc_code", loginModel.getResult().getNcCode());
        editor.putString("name", loginModel.getResult().getName());
        editor.putString("email", loginModel.getResult().getEmail());
        editor.putString("mobile", loginModel.getResult().getMobile());
        editor.putString("dob", loginModel.getResult().getDob());
        editor.putString("gender", loginModel.getResult().getGender());
        editor.putString("password", loginModel.getResult().getPassword());
        editor.putString("pin", loginModel.getResult().getPin());
        editor.putString("profile_pic", loginModel.getResult().getProfilePic());
        editor.putString("balance", loginModel.getResult().getBalance());
        editor.putString("promo_balance", loginModel.getResult().getPromoBalance());
        editor.putString("address", loginModel.getResult().getAddress());
        editor.putString("input", loginModel.getResult().getInput());
        editor.putString("date", loginModel.getResult().getDate());
        editor.putString("status", loginModel.getResult().getStatus());

        // Commit the edits!
        editor.apply();
    }

    public String getNcCode(Context context) {

        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("nc_code", "");
    }

    public String getUserName(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("name", "");
    }

    public void deleteUserDetails(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE);
        sp.edit().remove("id").apply();
        sp.edit().remove("nc_code").apply();
        sp.edit().remove("name").apply();
        sp.edit().remove("email").apply();
        sp.edit().remove("mobile").apply();
        sp.edit().remove("dob").apply();
        sp.edit().remove("gender").apply();
        sp.edit().remove("password").apply();
        sp.edit().remove("pin").apply();
        sp.edit().remove("profile_pic").apply();
        sp.edit().remove("balance").apply();
        sp.edit().remove("promo_balance").apply();
        sp.edit().remove("address").apply();
        sp.edit().remove("input").apply();
        sp.edit().remove("date").apply();
        sp.edit().remove("status").apply();

    }

    public LoginResult getAllUserDetails(Context context) {

        LoginResult loginResult = new LoginResult();

        loginResult.setId(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("id", ""));
        loginResult.setNcCode(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("nc_code", ""));
        loginResult.setName(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("name", ""));
        loginResult.setEmail(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("email", ""));
        loginResult.setMobile(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("mobile", ""));
        loginResult.setDob(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("dob", ""));
        loginResult.setGender(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("gender", ""));
        loginResult.setPassword(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("password", ""));
        loginResult.setPin(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("pin", ""));
        loginResult.setProfilePic(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("profile_pic", ""));
        loginResult.setBalance(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("balance", ""));
        loginResult.setPromoBalance(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("promo_balance", ""));
        loginResult.setAddress(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("address", ""));
        loginResult.setInput(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("input", ""));
        loginResult.setDate(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("date", ""));
        loginResult.setStatus(context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("status", ""));

        return loginResult;
    }

    public String getUserEmail(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("email", "");
    }

    public String getUserMobile(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("mobile", "");
    }

    public String getUserProfilePic(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("profile_pic", "");
    }

    public String getUserDOB(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("dob", "");
    }

    public String getUserGender(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("gender", "");
    }

    public String getUserPassword(Context context) {
        return context.getSharedPreferences(USER_DETAILS, Activity.MODE_PRIVATE).getString("password", "");
    }

    /**
     * Push token
     */
    public void savePushToken(Context context, String token) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences contactsState = context.getSharedPreferences(fcmToken, 0);
        SharedPreferences.Editor editor = contactsState.edit();
        editor.putString("token", token);

        editor.apply();
    }

    public String getPushToken(Context context) {
        SharedPreferences contactsState = context.getSharedPreferences(fcmToken, 0);
        return contactsState.getString("token", "");
    }


  /*  intent.putExtra("city_id", cityId);
                            intent.putExtra("city_name", cityName);
                            intent.putExtra("area_id", areaId);
                            intent.putExtra("area_name", areaName);
                            */

    /**
     * Push token
     */
    public void saveLocation(Context context, String city_id, String city_name,
                             String area_id, String area_name) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences contactsState = context.getSharedPreferences(LOCATION_TOKEN, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = contactsState.edit();

        editor.putString("city_id", city_id);
        editor.putString("city_name", city_name);
        editor.putString("area_id", area_id);
        editor.putString("area_name", area_name);

        editor.apply();
    }

    public String getCityId(Context context) {
        SharedPreferences contactsState = context.getSharedPreferences(LOCATION_TOKEN, 0);

        return contactsState.getString("city_id", "");
    }

    public String getCityName(Context context) {
        SharedPreferences contactsState = context.getSharedPreferences(LOCATION_TOKEN, 0);

        return contactsState.getString("city_name", "");
    }

    public String getAreaId(Context context) {
        SharedPreferences contactsState = context.getSharedPreferences(LOCATION_TOKEN, 0);

        return contactsState.getString("area_id", "");
    }

    public String getAreaName(Context context) {
        SharedPreferences contactsState = context.getSharedPreferences(LOCATION_TOKEN, 0);

        return contactsState.getString("area_name", "");
    }
}
