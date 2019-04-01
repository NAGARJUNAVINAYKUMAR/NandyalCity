package com.gathratechnal.nandyalcity.login.gcm;

import com.gathratechnal.nandyalcity.BuildConfig;
import com.google.gson.GsonBuilder;
import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.login.contacts.ContactsInterface;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

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

public class GcmAsyncTask {

    public ContactsInterface delegate;

    public void sendGcmDetails(JSONObject jsonObject) {

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

        Call<ResponseModel> postLogin = service.sendDeviceDetails(requestBody);

        postLogin.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    delegate.onContactsPostExecute(response.body(),"");

                } else {
                    // delegate.onStatusCallBack(ErrorUtils.parseError(response).getMessage());
                    delegate.onContactsPostExecute(null,"");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                delegate.onContactsPostExecute(null,"");
            }
        });
    }
}
