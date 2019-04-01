package com.gathratechnal.nandyalcity.push;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.gathratechnal.nandyalcity.utils.Preferences;

public class NandyalFirebaseInstantIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("PushToken: ",refreshedToken);

        Preferences.getInstance().savePushToken(this,refreshedToken);
        //new Preferences().savePushToken(this, refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRefreshedTokenToServer(refreshedToken);
    }

    /**
     * @param token The new token.
     */
    private void sendRefreshedTokenToServer(String token) {
        // TODO: to send token to server.
    }
}