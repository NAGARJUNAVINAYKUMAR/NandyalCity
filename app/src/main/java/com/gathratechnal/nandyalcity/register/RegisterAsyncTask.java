package com.gathratechnal.nandyalcity.register;

import com.gathratechnal.nandyalcity.BuildConfig;
import com.google.gson.GsonBuilder;
import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterAsyncTask {
    public RegisterInterface delegate;

    public void sendRegisterDetails(JSONObject jsonObject) {

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
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),strRequestBody);
            Call<ResponseModel> postLogin = service.sendRegisterDetails(requestBody);
            postLogin.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    if (response.isSuccessful()) {
                        delegate.onRegisterPostExecute(response.body(), response.message());

                    } else {
                        // delegate.onStatusCallBack(ErrorUtils.parseError(response).getMessage());
                        delegate.onRegisterPostExecute(null, response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    delegate.onRegisterPostExecute(null, t.getMessage());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
