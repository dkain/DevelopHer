package com.example.maesen.developher;

import android.os.Bundle;
import android.speech.*;
import android.content.Intent;
import android.text.format.Time;
import android.content.Context;
import android.util.Log;
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
        Log.i("stopTracking", "no longer listening to mic");
    }

    public boolean hasSpokenEver() {
        return !vdh.times.isEmpty();
    }

    public Map<Time, Long> getTimesSpoken() {
        Log.i("GET TIMES SPOKEN-----", "IT'S HAPPENING?--------------");
        return vdh.times;
    }

    public Time getStartTime() {
        return vdh.startTime;
    }

    public Time getEndTime() {
        if (vdh.endTimeRecorded) {
            return vdh.endTime;
        } else {
            return new Time();
        }
    }

    public long getMeetingLength() {
        long start = getStartTime().toMillis(true);
        long end = getEndTime().toMillis(true);
        return TimeUnit.MILLISECONDS.toSeconds(end - start);
    }

    class VoiceDataHandler implements RecognitionListener {
        private SpeechRecognizer sr;

        private HashMap<Time, Long> times = new HashMap<Time, Long>();
        private Time startTime = new Time();
        private Time endTime = new Time();
        private Time currentSpeechStart;
        private boolean stopRequested = false;
        private boolean endTimeRecorded = false;

        public VoiceDataHandler(Context context) {
            Log.i("vdh constructor", "test");

            sr = SpeechRecognizer.createSpeechRecognizer(context);
            sr.setRecognitionListener(this);
        }

        public void startListening() {
            Log.i("start listening", "is it happening");
            startTime.setToNow();
            //startListeningChunk();
            while(!stopRequested) {
                startListeningChunk();
                try {
                    Thread.sleep(5 * 1000, 0);
                } catch(java.lang.InterruptedException e) {
                    Log.i("LISTENER", "Interrupted!");
                }
            }
            Log.i("LISTENER", "Started listening");
        }

        public void stopListening() {
            endTime.setToNow();
            stopRequested = true;
            endTimeRecorded = true;
            Log.i("LISTENER", "Stopped listening");
        }

        private void startListeningChunk() {
            Log.i("startListeningChunk", "starting outside of stopRequested check");
            if (!stopRequested) {
                Log.i("startListeningChunk", "listening should occur");
                sr.setRecognitionListener(this);
                sr.cancel();
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
                sr.startListening(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
                Log.i("startListeningChunk", times.toString());
            }
        }

        @Override
        public void onResults(Bundle b) {
            startListeningChunk();
        }

        @Override
        public void onError(int error) {
            if (!stopRequested) {
                Log.e("onError", "RESTARTING LISTENING " + error);
                startListeningChunk();
            }
        }

        @Override
        public void onBeginningOfSpeech() {
            currentSpeechStart = new Time();
            currentSpeechStart.setToNow();
            Log.i("LISTENER", "Speech beginning");
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
            Log.i("onEndOfSpeech", "SPEECH END CALLBACK CALLED");
            Log.i("LISTENER", "Speech ending");
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