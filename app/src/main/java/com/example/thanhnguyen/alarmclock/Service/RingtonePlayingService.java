package com.example.thanhnguyen.alarmclock.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.thanhnguyen.alarmclock.Constant.Constant;
import com.example.thanhnguyen.alarmclock.R;

/**
 * Created by thanhnguyen on 3/1/17.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    boolean isPlaying = false;
    int startId;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString(Constant.KEY_EXTRA_INTENT);

        assert state != null;
        switch (state) {
            case Constant.VALUE_EXTRA_INTENT_ALARM_ON:
                this.startId = 1;
                break;
            case Constant.VALUE_EXTRA_INTENT_ALARM_OFF:
                this.startId = 0;
                break;
            default:
                this.startId = 0;
                break;
        }

        if (!this.isPlaying && this.startId == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.alarm1);
            mediaPlayer.start();
            this.isPlaying = true;
            this.startId = 0;

        } else if (this.isPlaying && this.startId == 0) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isPlaying = false;
            this.startId = 0;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isPlaying = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
