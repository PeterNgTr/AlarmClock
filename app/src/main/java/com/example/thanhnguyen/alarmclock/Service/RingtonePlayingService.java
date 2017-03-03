package com.example.thanhnguyen.alarmclock.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.thanhnguyen.alarmclock.Constant.Constant;
import com.example.thanhnguyen.alarmclock.Activity.MainActivity;
import com.example.thanhnguyen.alarmclock.R;

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
            mediaPlayer = MediaPlayer.create(this, R.raw.bluestone);
            mediaPlayer.start();
            this.isPlaying = true;
            this.startId = 0;

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this,0,intentMainActivity,0);

            Notification notifyPopup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off!")
                    .setContentText("Click me!")
                    .setContentIntent(pendingIntentMainActivity)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.notifyicon)
                    .build();

            notificationManager.notify(0, notifyPopup);

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
