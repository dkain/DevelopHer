package com.example.maesen.developher;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.format.Time;
import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by an on 8/28/15.
 */
class VoiceTracker {
    private SpeechRecognizer sr;

    public VoiceTracker(Context context) {
        sr = SpeechRecognizer.createSpeechRecognizer(context);
        VoiceDataHandler vdh = new VoiceDataHandler();
    }

    public void startTracking() {
        //sr.startListening();
    }

    public void stopTracking() {
        
    }

    public boolean hasSpokenEver() {
        return false;
    }

    public Map<Time, Long> getTimesSpoken() {
        return null;
    }

    public Time getStartTime() {
        return null;
    }

    public Time getEndTime() {
        return null;
    }

    public long getMeetingLength() {
        return 0;
    }

    class VoiceDataHandler implements RecognitionListener {
        private HashMap<Time, Long> times = new HashMap<Time, Long>();
        private Time currentSpeechStart;

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBeginningOfSpeech() {
            currentSpeechStart = new Time();
            currentSpeechStart.setToNow();
        }

        @Override
        public void onEndOfSpeech() {
            Time currentSpeechEnd = new Time();
            currentSpeechEnd.setToNow();

            if (currentSpeechEnd.before(currentSpeechStart)) {
                throw new Error("WTF");
            }

            long elapsedMillis = currentSpeechEnd.toMillis(true) - currentSpeechStart.toMillis(true);
            long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis);
            times.put(currentSpeechStart, elapsedSeconds);
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
    }
}