package com.example.thanhnguyen.alarmclock.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.thanhnguyen.alarmclock.Service.RingtonePlayingService;
import com.example.thanhnguyen.alarmclock.Constant.*;

/**
 * Created by thanhnguyen on 3/1/17.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String getExtraString = intent.getExtras().getString(Constant.KEY_EXTRA_INTENT);

        Intent ringtoneServiceIntent = new Intent(context, RingtonePlayingService.class);

        ringtoneServiceIntent.putExtra(Constant.KEY_EXTRA_INTENT, getExtraString);

        context.startService(ringtoneServiceIntent);
    }
}
