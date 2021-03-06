package com.example.maesen.developher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;

public class ControllerActivity extends AppCompatActivity {

    private static final int END_OF_MEETING_NOTIFICATION_ID = 0;
    private static final int MID_MEETING_NOTIFICATION_ID = 1;

    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        SpeakInApp app = ((SpeakInApp)getApplication());
        VoiceTracker tracker = app.getTracker();
        Log.i("CONTROLLER ", "" + tracker.toString());

        Intent intent = getIntent();
        hour = intent.getIntExtra("endHour", 0);
        minute = intent.getIntExtra("endMinute", 0);

        Log.i("CONTROLLER ", "" + hour);
        Log.i("CONTROLLER ", "" + minute);
    }

    public void onStartSpeakin(View view) {
        if(((RadioButton)findViewById(R.id.radioButton)).isChecked()) {
            // Notifications every 5 minutes
            Log.i("CONTROLLER ", "button 1");
            queueRepeatingNotifications();
        }
        if(((RadioButton)findViewById(R.id.radioButton2)).isChecked()) {
            // Halfway through notification
            queueMidMeetingNotifcation();
        }
        if(((RadioButton)findViewById(R.id.radioButton3)).isChecked()) {
            // No notifications
            Log.i("CONTROLLER ", "button 3");
        }
        EditText urlField = (EditText)findViewById(R.id.editText);
        String url = urlField.getText().toString();
        SpeakInApp app = ((SpeakInApp)getApplication());
        app.setNotesUrl(url);
        Log.i("CONTROLLER ", url);

        sendEndOfMeetingNotification();

        // START RECORDING!

        VoiceTracker tracker = app.getTracker();
        tracker.startTracking();
    }

    private void queueRepeatingNotifications() {
        Calendar now = Calendar.getInstance();

        Intent intent = new Intent(this, MidpointMeetingReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getService(this, MID_MEETING_NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MID_MEETING_NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        SpeakInApp app = ((SpeakInApp)getApplication());
        app.setRepeatingIntent(pendingIntent);

        Log.i("CONTROLLER ", "Set repeating alarm ");
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 1000*60*5, pendingIntent);
    }

    private void queueMidMeetingNotifcation() {
        Calendar now = Calendar.getInstance();

        Intent intent = new Intent(this, MidpointMeetingReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MID_MEETING_NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);

        Log.i("CONTROLLER ", "Set midpoint alarm ");

        double diff = (calendar.getTimeInMillis() - now.getTimeInMillis())/2.0;
        Log.i("CONTROLLER ", "Millis difference: " + diff);

        alarmManager.set(AlarmManager.RTC_WAKEUP, now.getTimeInMillis() + (int)diff, pendingIntent);
    }

    private void sendEndOfMeetingNotification() {
        Intent intent = new Intent(this, EndOfMeetingReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, END_OF_MEETING_NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);

        Log.i("CONTROLLER ", "Set alarm ");

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
