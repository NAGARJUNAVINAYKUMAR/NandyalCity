package com.gathratechnal.nandyalcity.utils;

import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.BuildConfig;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CommonAsyncTask {

    public CommonOnPostExecuteListener listener;


    public void getWebPages(JSONObject jsonObject) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        ApiRequest service = retrofit.create(ApiRequest.class);

        String strRequestBody = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), strRequestBody);

        Call<ResponseModel> postLogin = service.getWebPages(requestBody);

        postLogin.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    listener.onPostExecuteListener(response.body(), response.message());
                } else {
                    listener.onPostExecuteListener(null, response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                listener.onPostExecuteListener(null, t.getMessage());
            }
        });
    }

    public void addProperty(JSONObject jsonObject) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        ApiRequest service = retrofit.create(ApiRequest.class);

        String strRequestBody = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), strRequestBody);

        Call<ResponseModel> postLogin = service.addProperty(requestBody);

        postLogin.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    listener.onPostExecuteListener(response.body(), response.message());
                } else {
                    listener.onPostExecuteListener(null, response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                listener.onPostExecuteListener(null, t.getMessage());
            }
        });
    }
}
