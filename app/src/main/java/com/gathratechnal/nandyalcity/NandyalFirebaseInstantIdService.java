package com.gathratechnal.nandyalcity;

import android.util.Log;

import com.gathratechnal.nandyalcity.utils.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class NandyalFirebaseInstantIdService extends FirebaseInstanceIdService {


    private static final String TAG = NandyalFirebaseInstantIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if (refreshedToken!=null) {
            new Preferences().savePushToken(this,refreshedToken);
        }
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
// [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }}


    /*extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("PushToken: ",refreshedToken);

        new Preferences().savePushToken(this,refreshedToken);
        //new Preferences().savePushToken(this, refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRefreshedTokenToServer(refreshedToken);
    }

    *//**
     * @param token The new token.
     *//*
    private void sendRefreshedTokenToServer(String token) {
        // TODO: to send token to server.
    }
}*/