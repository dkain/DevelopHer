package com.example.maesen.developher;

import android.content.Intent;
import com.example.maesen.developher.VoiceTracker;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeakInApp app = ((SpeakInApp)getApplication());
        VoiceTracker tracker = new VoiceTracker(getApplicationContext());
        app.setVoiceTracker(tracker);
        setContentView(R.layout.activity_main);
    }

    public void onStartClicked(View view) {
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        Log.i("HELLO ", "" + timePicker.toString());
        Log.i("HELLO ", "" + timePicker.getCurrentHour());
        Log.i("HELLO ", "" + timePicker.getCurrentMinute());

        Intent myIntent = new Intent(MainActivity.this, ControllerActivity.class);
        myIntent.putExtra("endHour", timePicker.getCurrentHour()); //Optional parameters
        myIntent.putExtra("endMinute", timePicker.getCurrentMinute());
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
