package com.gathratechnal.nandyalcity.login;

import com.gathratechnal.nandyalcity.BuildConfig;
import com.google.gson.GsonBuilder;
import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.login.model.LoginModel;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class LoginAsyncTask {

    public LoginInterface delegate;

    public void getLoginDetails(JSONObject jsonObject) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        ApiRequest service = retrofit.create(ApiRequest.class);

        String strRequestBody = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),strRequestBody);

        Call<LoginModel> postLogin = service.getLoginDetails(requestBody);

        postLogin.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.isSuccessful()) {
                    delegate.onLoginPostExecute(response.body(),response.message());

                } else {
                    // delegate.onStatusCallBack(ErrorUtils.parseError(response).getMessage());
                    delegate.onLoginPostExecute(null,response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                delegate.onLoginPostExecute(null,t.getMessage());
            }
        });
    }
}
