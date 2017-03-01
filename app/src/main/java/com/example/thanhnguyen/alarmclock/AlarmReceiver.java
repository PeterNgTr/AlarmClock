package com.example.thanhnguyen.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by thanhnguyen on 3/1/17.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String getExtraString = intent.getExtras().getString("extra");

        Intent ringtoneServiceIntent = new Intent(context, RingtonePlayingService.class);

        ringtoneServiceIntent.putExtra("extra", getExtraString);

        context.startService(ringtoneServiceIntent);
    }
}
