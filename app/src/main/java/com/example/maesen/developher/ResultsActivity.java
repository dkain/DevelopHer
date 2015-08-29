package com.example.maesen.developher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.text.format.Time;
import android.view.MenuItem;
import android.widget.*;

import java.util.Map;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity {
    private Map<Time, Long> timesMap;
    private int getTimesSpoken = 0;
    private double getTimeSpoken = 0;
    private double averageSpeakingTime = 0;
    private double getMeetingLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        SpeakInApp app = ((SpeakInApp)getApplication());
        VoiceTracker tracker = app.getTracker();
        //timesMap = tracker.getTimesSpoken();
        timesMap = new HashMap<Time, Long>();
        //parseTimes(timesMap);

        TextView meetingLength = (TextView)findViewById(R.id.meetingLength);
        meetingLength.setText("This meeting was " + getMeetingLength + " minutes long.");

        TextView timesSpoken = (TextView)findViewById(R.id.timesSpoken);
        timesSpoken.setText("You spoke " + getTimesSpoken + " times.");

        TextView timeSpoken = (TextView)findViewById(R.id.timeSpoken);
        timeSpoken.setText("You spoke for " + getTimeSpoken + " minutes.");

        TextView avgTimeSpoken = (TextView)findViewById(R.id.avgTimeSpoken);
        timeSpoken.setText("On average, you spoke for " + averageSpeakingTime + " minutes at a time.");

        Intent intent = getIntent();
    }

    private void parseTimes(Map<Time, Long> timesSpoken) {
        getTimesSpoken = timesSpoken.keySet().size();
        for (Time startTime : timesSpoken.keySet()) {
            getTimeSpoken += timesMap.get(startTime);
        }
        averageSpeakingTime = getTimeSpoken/getTimesSpoken;
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
