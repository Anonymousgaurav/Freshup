package com.omninos.freshup.FirebaseClasses;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.omninos.freshup.Activities.NotificationActivity;
import com.omninos.freshup.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;

    String title, message, price, barbarName, timeSlote, AppointmentDate, services, subtitle, bookingServiceId;
    NotificationChannel mChannel;
    Notification notification;
    Uri defaultSound;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("message");
        price = remoteMessage.getData().get("price");
        AppointmentDate = remoteMessage.getData().get("apointmentDate");
        barbarName = remoteMessage.getData().get("barberName");
        services = remoteMessage.getData().get("service");
        subtitle = remoteMessage.getData().get("subtitle");
        timeSlote = remoteMessage.getData().get("timeSlot");
        bookingServiceId = remoteMessage.getData().get("bookingServiceId");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setOreoNotification(title, message, price, barbarName, timeSlote, AppointmentDate, services, subtitle, bookingServiceId);
            this.startForegroundService(new Intent(this, NotificationActivity.class));
        } else {
            showNotification(title, message, price, barbarName, timeSlote, AppointmentDate, services, subtitle, bookingServiceId);
            this.startService(new Intent(this, NotificationActivity.class));
        }
    }

    private void showNotification(String title, String message, String price, String barbarName, String timeSlote, String appointmentDate, String services, String subtitle, String bookingServiceId) {

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("barbarName", barbarName);
        intent.putExtra("price", price);
        intent.putExtra("timeSlote", timeSlote);
        intent.putExtra("AppointmentDate", AppointmentDate);
        intent.putExtra("services", services);
        intent.putExtra("subtitle", subtitle);
        intent.putExtra("bookingServiceId", bookingServiceId);
        //startActivity(intent);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText(message)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void setOreoNotification(String title, String message, String price, String barbarName, String timeSlote, String appointmentDate, String services, String subtitle, String bookingServiceId) {


        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("barbarName", barbarName);
        intent.putExtra("price", price);
        intent.putExtra("timeSlote", timeSlote);
        intent.putExtra("AppointmentDate", AppointmentDate);
        intent.putExtra("services", services);
        intent.putExtra("subtitle", subtitle);
        intent.putExtra("bookingServiceId", bookingServiceId);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Sets an ID for the notification, so it can be updated.
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "ove";// The user-visible name of the channel.

        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }

// Create a notification and set the notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.logo)
                    .setSound(defaultSound)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();


        }


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

// Issue the notification.
        mNotificationManager.notify(NOTIFICATION_ID, notification);

    }

}