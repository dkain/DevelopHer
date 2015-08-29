package com.example.maesen.developher;

import android.app.Application;

/**
 * Created by maesen on 8/28/15.
 */
public class SpeakInApp extends Application {

    private VoiceTracker tracker = new VoiceTracker();

    public VoiceTracker getTracker() {
        return tracker;
    }
}