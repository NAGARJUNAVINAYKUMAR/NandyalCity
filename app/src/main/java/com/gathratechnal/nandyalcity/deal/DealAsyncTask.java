package com.gathratechnal.nandyalcity.deal;

import com.gathratechnal.nandyalcity.BuildConfig;
import com.gathratechnal.nandyalcity.deal.model.DealModel;
import com.google.gson.GsonBuilder;
import com.gathratechnal.nandyalcity.ApiRequest;

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

public class DealAsyncTask {

    public DealInterface delegate;

    public void getLocationDetails(JSONObject jsonObject) {

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
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),strRequestBody);


        Call<DealModel> postLogin = service.getDealDetails(requestBody);

        postLogin.enqueue(new Callback<DealModel>() {
            @Override
            public void onResponse(Call<DealModel> call, Response<DealModel> response) {

                if (response.isSuccessful()) {
                    delegate.onLocationPostExecute(response.body(), "");
                } else {
                    delegate.onLocationPostExecute(null, "");
                }
            }

            @Override
            public void onFailure(Call<DealModel> call, Throwable t) {
                delegate.onLocationPostExecute(null, "");
            }
        });
    }
}
