package com.example.maesen.developher;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
