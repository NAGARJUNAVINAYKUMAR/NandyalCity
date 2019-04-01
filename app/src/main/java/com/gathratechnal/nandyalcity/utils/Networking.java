package com.gathratechnal.nandyalcity.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Developer on 10/5/17.
 * Checks the Network availability
 */
public class Networking {

    /**
     * @return return true if the application can access the Internet
     */
    public static boolean isNetworkAvailable(Context context) {

        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } else {
            return false;
        }
    }
}
