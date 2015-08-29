package com.example.maesen.developher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.text.format.Time;
import android.view.MenuItem;
import android.widget.*;
import android.os.Vibrator;

import java.util.Map;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity {
    private Map<Time, Long> timesMap;
    private double getMeetingLength = 0;
    private ProgressBar progressBar = null;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("RESULTS", "REACHED");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        SpeakInApp app = ((SpeakInApp)getApplication());
        VoiceTracker tracker = app.getTracker();

        tracker.stopTracking();

        // Cancel the reeating alarms, if they are going.
        PendingIntent repeatingIntent = app.getRepeatingIntent();
        if(repeatingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(repeatingIntent);
        }

        timesMap = new HashMap<Time, Long>();
        timesMap = tracker.getTimesSpoken();
        parseTimes(timesMap);
        getMeetingLength = (int)(tracker.getMeetingLength()/60);

        TextView meetingLength = (TextView)findViewById(R.id.meetingLength);
        if (getMeetingLength == 1) {
            meetingLength.setText("&#8226;Your meeting was " + getMeetingLength + " minute long");
        }
        meetingLength.setText("&#8226;Your meeting was " + getMeetingLength + " minutes long");


        double getTimeSpoken = .5;
        double percentSpeaking = 0;
        if (getMeetingLength > 0) {
            percentSpeaking = (int)(100 * getTimeSpoken / getMeetingLength);
        }
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress((int)percentSpeaking);
        Intent intent = getIntent();

        TextView progressPercent = (TextView)findViewById(R.id.editText2);
        progressPercent.setText("You spoke for " + percentSpeaking + "% of the time.");

    }

    private void parseTimes(Map<Time, Long> timesSpoken) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
