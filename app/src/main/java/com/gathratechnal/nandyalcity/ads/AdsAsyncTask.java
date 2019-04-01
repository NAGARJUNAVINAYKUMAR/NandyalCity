package com.gathratechnal.nandyalcity.ads;

import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.BuildConfig;
import com.gathratechnal.nandyalcity.directory.CategoryListAsyncTaskInterface;
import com.gathratechnal.nandyalcity.utils.ResponseModel;
import com.google.gson.GsonBuilder;

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

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class AdsAsyncTask {
    public CategoryListAsyncTaskInterface listner;

    public void getAdsList(JSONObject jsonObject) {

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
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), strRequestBody);


        Call<ResponseModel> postLogin = service.getAdsList(requestBody);

        postLogin.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    listner.onCategoryPostExecute(response.body(), "success");

                } else {
                    listner.onCategoryPostExecute(null, "failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                listner.onCategoryPostExecute(null, "failed");
            }


        });
    }
}


