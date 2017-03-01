package com.example.thanhnguyen.alarmclock;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by thanhnguyen on 3/1/17.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    int startId;
    boolean isPlaying;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "alarm on":
                this.startId = 1;
                break;
            case "alarm off":
                this.startId = 0;
                break;
            default:
                this.startId = 0;
                break;
        }



        if (!this.isPlaying && this.startId == 1) {
            mediaPlayer = MediaPlayer.create(this,R.raw.BluestoneArray);
            mediaPlayer.start();
            this.isPlaying = true;
            this.startId = 0;
        }else if (this.isPlaying && this.startId == 0) {

            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isPlaying = false;
            this.startId = 0;

        } else if (!this.isPlaying && this.startId == 0) {
            this.isPlaying = false;
            this.startId = 0;

        } else if (this.isPlaying && this.startId == 1) {
            this.isPlaying = false;
            this.startId = 0;

        } else {

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
