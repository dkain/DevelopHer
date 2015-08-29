package com.example.maesen.developher;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.text.format.Time;
import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by an on 8/28/15.
 */
class VoiceTracker {
    private VoiceDataHandler vdh;

    public VoiceTracker(Context context) {
        vdh = new VoiceDataHandler(context);
    }

    public void startTracking() {
        vdh.startListening();
    }

    public void stopTracking() {
        vdh.stopListening();
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
        private SpeechRecognizer sr;

        private HashMap<Time, Long> times = new HashMap<Time, Long>();
        private Time currentSpeechStart;
        private boolean stopRequested = false;

        public VoiceDataHandler(Context context) {
            sr = SpeechRecognizer.createSpeechRecognizer(context);
            sr.setRecognitionListener(this);
        }

        public void startListening() {
            startListeningChunk();
        }

        public void stopListening() {
            stopRequested = true;
        }

        private void startListeningChunk() {
            if (!stopRequested) {
                sr.setRecognitionListener(vdh);
                sr.startListening(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
            }
        }

        @Override
        public void onResults(Bundle b) {
            startListeningChunk();
        }

        @Override
        public void onError(int error) {
            startListeningChunk();
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
        public void onRmsChanged(float rmsdB) {
            // will not implement
        }

        @Override
        public void onReadyForSpeech(Bundle b) {
            // will not implement
        }

        @Override
        public void onPartialResults(Bundle b) {
            // will not implement
        }

        @Override
        public void onEvent(int n, Bundle b) {
            // will not implement
        }

        @Override
        public void onBufferReceived(byte[] b) {
            // will not implement
        }

    }
}