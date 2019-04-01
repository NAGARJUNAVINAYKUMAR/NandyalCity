package com.gathratechnal.nandyalcity;


import com.gathratechnal.nandyalcity.deal.model.DealModel;
import com.gathratechnal.nandyalcity.deal.versionModel.VersionModel;
import com.gathratechnal.nandyalcity.login.model.LoginModel;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequest {

    //Login details
    @POST("login")
    @Headers("Content-Type: application/json")
    Call<LoginModel> getLoginDetails(@Body RequestBody requestBody);//Login details

    @POST("register_New")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> sendRegisterDetails(@Body RequestBody requestBody);

    @POST("otp_Verify")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> sendOtpVerify(@Body RequestBody requestBody);

    //Send Contacts
    @POST("gcmusers")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> sendContactsDetails(@Body RequestBody requestBody);

    //Send Device Id
    @POST("gcm_ids")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> sendDeviceDetails(@Body RequestBody requestBody);

    //Location cities areas details
    @POST("deals")
    @Headers("Content-Type: application/json")
    Call<DealModel> getDealDetails(@Body RequestBody requestBody);


    //resturant_orders
    @POST("resturant_orders")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> sendCart(@Body RequestBody requestBody);


    @POST("resturant_order_status")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> checkOutCart(@Body RequestBody requestBody);

    //Location cities areas details
    @POST("version")
    @Headers("Content-Type: application/json")
    Call<VersionModel> getVersionDetails();


    @POST("forget_pwd")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> getForgotPassword(@Body RequestBody requestBody);

    @POST("ads")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> getAdsList(@Body RequestBody requestBody);

    @POST("change_pass")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> getPasswordChanged(@Body RequestBody requestBody);

    @POST("GetWebPage")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> getWebPages(@Body RequestBody requestBody);

    @POST("addProperty")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> addProperty(@Body RequestBody requestBody);

}
