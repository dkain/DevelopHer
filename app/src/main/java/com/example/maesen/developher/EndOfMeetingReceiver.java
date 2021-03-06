package com.example.maesen.developher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.Notification;
import android.os.Vibrator;
import android.net.Uri;

/**
 * Created by maesen on 8/28/15.
 */
public class EndOfMeetingReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Log.i("END OF MEETING", "REACHED");
        Log.i("END OF MEETING", context.toString());


        Intent intent2 = new Intent(context, ResultsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.drawable.ic_stat_notification);

        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE);

        /*builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setLights(0xFF0000FF, 100, 3000);
        builder.setPriority(0);*/


        builder.setContentTitle("SpeakIn!");
        builder.setContentText("Your meeting has now ended!");
        builder.setSubText("Tap to see how it went.");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        /*
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds

        if (v.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
        } else {
            Log.v("Can Vibrate", "NO");
        }


        v.vibrate(500);*/
    }
}

