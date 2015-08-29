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
        }
        if(((RadioButton)findViewById(R.id.radioButton2)).isChecked()) {
            // Halfway through notification
            Log.i("CONTROLLER ", "button 2");
        }
        if(((RadioButton)findViewById(R.id.radioButton3)).isChecked()) {
            // No notifications
            Log.i("CONTROLLER ", "button 3");
        }
        EditText urlField = (EditText)findViewById(R.id.editText);
        String url = urlField.getText().toString();
        Log.i("CONTROLLER ", url);

        sendEndOfMeetingNotification();

        // START RECORDING!
        SpeakInApp app = ((SpeakInApp)getApplication());
        VoiceTracker tracker = app.getTracker();
        tracker.startTracking();
    }

    private void sendEndOfMeetingNotification() {
        Intent intent = new Intent(this, EndOfMeetingReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
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
