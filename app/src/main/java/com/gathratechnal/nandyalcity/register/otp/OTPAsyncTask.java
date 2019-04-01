package com.gathratechnal.nandyalcity.register.otp;

import com.gathratechnal.nandyalcity.ApiRequest;
import com.gathratechnal.nandyalcity.BuildConfig;
import com.gathratechnal.nandyalcity.register.RegisterInterface;
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
 * Created by praveenkumar on 4/3/18 1:13 AM.
 */
public class OTPAsyncTask {

    public OtpInterface delegate;

    public void sendOtpDetails(JSONObject jsonObject) {

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
            Call<ResponseModel> postLogin = service.sendOtpVerify(requestBody);
            postLogin.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    if (response.isSuccessful()) {
                        delegate.onOtpPostExecute(response.body(), response.message());

                    } else {
                        // delegate.onStatusCallBack(ErrorUtils.parseError(response).getMessage());
                        delegate.onOtpPostExecute(null, response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    delegate.onOtpPostExecute(null, t.getMessage());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
