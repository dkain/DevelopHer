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
    private int getTimesSpoken = 0;
    private double getTimeSpoken = 0;
    private double averageSpeakingTime = 0;
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

        TextView timesSpoken = (TextView)findViewById(R.id.timesSpoken);
        if (getTimesSpoken == 1) {
            timesSpoken.setText("&#8226;You spoke " + getTimesSpoken + " time");
        }
        else {
            timesSpoken.setText("&#8226;You spoke " + getTimesSpoken + " times");
        }

        TextView timeSpoken = (TextView)findViewById(R.id.timeSpoken);
        if ((int)getTimeSpoken == 1) {
            timeSpoken.setText("&#8226;You spoke for " + (int)getTimeSpoken + " minute");
        }
        else {
            timeSpoken.setText("&#8226;You spoke for " + (int)getTimeSpoken + " minutes");
        }

        averageSpeakingTime /= 60;
        TextView avgTimeSpoken = (TextView)findViewById(R.id.avgTimeSpoken);
        if ((int)averageSpeakingTime == 1) {
            avgTimeSpoken.setText("&#8226;On average you spoke for " + (int)averageSpeakingTime + " minute at a time");
        }
        avgTimeSpoken.setText("&#8226;On average you spoke for " + (int)averageSpeakingTime + " minutes at a time");

        int percentSpeaking = 0;
        if (getTimeSpoken > 0) {
            percentSpeaking = (int)(getTimeSpoken / getMeetingLength);
        }
        percentSpeaking = (int)(percentSpeaking * 100);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(percentSpeaking);
        Intent intent = getIntent();
    }

    private void parseTimes(Map<Time, Long> timesSpoken) {
        getTimesSpoken = timesSpoken.keySet().size();
        for (Time startTime : timesSpoken.keySet()) {
            getTimeSpoken += timesMap.get(startTime);
        }
        if (getTimesSpoken != 0) {
            averageSpeakingTime = getTimeSpoken / getTimesSpoken;
        }
        getTimeSpoken /= 60;
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
