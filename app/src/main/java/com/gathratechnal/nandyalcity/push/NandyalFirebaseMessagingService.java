package com.gathratechnal.nandyalcity.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NandyalFirebaseMessagingService extends FirebaseMessagingService {


    public NandyalFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            JSONObject pushData = new JSONObject(remoteMessage.getData());

            try {
                int type = pushData.getInt("type");
                String message = pushData.getString("body");

//                SqlNotificationHelper.getInstance().saveNotification(this, type, message);

                showNotification(message);

                if (pushData.has("id")) {
                    String bizId = pushData.getString("id");

                  //  downloadData(type, bizId);
                }
            } catch (JSONException ignore) {
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            try {
                JSONObject pushData = new JSONObject(remoteMessage.getNotification().getBody());

                int type = pushData.getInt("type");
                String message = pushData.getString("body");

             //   SqlNotificationHelper.getInstance().saveNotification(this, type, message);

                showNotification(message);
            } catch (Exception ignore) {
            }

        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void showNotification(String messageBody) {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
