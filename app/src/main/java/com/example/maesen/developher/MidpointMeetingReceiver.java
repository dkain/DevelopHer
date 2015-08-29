package com.example.maesen.developher;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.net.Uri;

import java.util.Random;
/**
 * Created by maesen on 8/28/15.
 */
public class MidpointMeetingReceiver extends BroadcastReceiver {

    private static final String CONFIDENCE = "http://greatist.com/grow/easy-confidence-boosters";

    public void onReceive(Context context, Intent intent) {
        Log.i("MID MEETING", "REACHED");
        Log.i("MID MEETING", context.toString());

        SpeakInApp app = ((SpeakInApp)context.getApplicationContext());
        VoiceTracker tracker = app.getTracker();
        String url = app.getNotesUrl();
        if(url.length() == 0) {
            url = CONFIDENCE;
        }

        Log.i("MID MEETING", url);

        Intent intent2 = new Intent(Intent.ACTION_VIEW);
        intent2.setData(Uri.parse(url));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 4, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.drawable.ic_stat_notification);

        builder.setContentIntent(pendingIntent);

        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        builder.setContentTitle("SpeakIn!");

        Random r = new Random();
        int i1 = r.nextInt(3);

        String[] hasSpoken = {"I love hearing your voice, keep it up!",
                "Keep on speakin'!",
                "You're doing great...don't stop speakin'!"};

        String[] hasNotSpoken = {"I haven't heard your voice yet!",
                "Give me some love...speak up!",
                "Remember to speak before the meeting ends!"};

        if(tracker.hasSpokenEver()) {
            builder.setContentText(hasSpoken[i1]);
        } else {
            builder.setContentText(hasNotSpoken[i1]);
        }

        if(!url.equals(CONFIDENCE)) {
            builder.setSubText("Tap to see your notes.");
        } else {
            builder.setSubText("Tap to see some confidence boosting tips.");
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
