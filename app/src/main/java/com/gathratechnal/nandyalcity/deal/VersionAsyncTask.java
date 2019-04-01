package com.gathratechnal.nandyalcity.deal;

import com.gathratechnal.nandyalcity.BuildConfig;
import com.google.gson.GsonBuilder;
import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.deal.versionModel.VersionModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by praveenkumar on 12/11/17 9:48 PM.
 */

public class VersionAsyncTask {

    public VersionInterface delegate;

    public void getVersionDetails() {

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


        Call<VersionModel> postLogin = service.getVersionDetails();

        postLogin.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {

                if (response.isSuccessful()) {
                    delegate.onVersionPostExecute(response.body(), "");
                } else {
                    delegate.onVersionPostExecute(null, "");
                }
            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                delegate.onVersionPostExecute(null, "");
            }
        });
    }
}
