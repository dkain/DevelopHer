package com.example.maesen.developher;

import android.app.Application;
import android.app.PendingIntent;

/**
 * Created by maesen on 8/28/15.
 */
public class SpeakInApp extends Application {

    private VoiceTracker tracker;
    private String notesUrl;
    private PendingIntent repeatingIntent = null;

    public void setVoiceTracker(VoiceTracker tracker) { this.tracker = tracker; }

    public VoiceTracker getTracker() {
        return tracker;
    }

    public void setNotesUrl(String url) { this.notesUrl = url; }

    public String getNotesUrl() { return notesUrl; }

    public PendingIntent getRepeatingIntent() { return repeatingIntent; }

    public void setRepeatingIntent(PendingIntent intent) { this.repeatingIntent = intent; }
}