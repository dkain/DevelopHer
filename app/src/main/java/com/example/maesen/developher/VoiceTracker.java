package com.example.maesen.developher;

import android.os.Bundle;
import android.speech.RecognitionListener;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by an on 8/28/15.
 */
class VoiceTracker implements RecognitionListener {
    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onReadyForSpeech(Bundle b) {

    }

    @Override
    public void onResults(Bundle b) {

    }

    @Override
    public void onPartialResults(Bundle b) {

    }

    @Override
    public void onEvent(int n, Bundle b) {

    }

    @Override
    public void onBufferReceived(byte[] b) {

    }

    @Override
    public void onError(int error) {

    }

    public boolean hasSpokenEver() {
        return false;
    }

    public Map<Timestamp, Integer> getTimesSpoken() {
        HashMap<Timestamp, Integer> times = new HashMap<Timestamp, Integer>();
        return times;
    }
}