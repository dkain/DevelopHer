package com.example.maesen.developher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.net.Uri;

/**
 * Created by maesen on 8/28/15.
 */
public class MidpointMeetingReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Log.i("MID MEETING", "REACHED");
        Log.i("MID MEETING", context.toString());

        SpeakInApp app = ((SpeakInApp)context.getApplicationContext());
        VoiceTracker tracker = app.getTracker();
        String url = app.getNotesUrl();

        Log.i("MID MEETING", url);

        Intent intent2 = new Intent(Intent.ACTION_VIEW);
        intent2.setData(Uri.parse(url));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.drawable.ic_stat_notification);

        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        builder.setContentTitle("SpeakIn!");



        if(tracker.hasSpokenEver()) {
            builder.setContentText("I loved hearing your voice this meeting!");
        } else {
            builder.setContentText("I haven't heard your voice yet!");
        }

        builder.setSubText("Tap to see your notes.");

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
