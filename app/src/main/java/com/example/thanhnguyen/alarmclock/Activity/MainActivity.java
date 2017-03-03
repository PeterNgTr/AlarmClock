package com.example.thanhnguyen.alarmclock.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.thanhnguyen.alarmclock.Constant.Constant;
import com.example.thanhnguyen.alarmclock.R;
import com.example.thanhnguyen.alarmclock.Receiver.AlarmReceiver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private TimePicker alarmTimePicker;
    private TextView updateStatus;
    private Context context;
    private PendingIntent pendingIntent;
    private String hourString;
    private String minString;
    private Button alarmOn;
    private Button alarmOff;
    private long goOffTime;
    private long olderTime;
    private ArrayList<String> alarmListString = new ArrayList<>();
    private ArrayList<Long>  goOffTimeList = new ArrayList<>();
    private ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
    private int requestCode = 0;
    private int pendingIntentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initServices();

        final Calendar calendar = Calendar.getInstance();
        final Calendar now = Calendar.getInstance();
        olderTime = now.getTimeInMillis();
        final Intent alarmIntend = new Intent(this.context, AlarmReceiver.class);

        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hourAlarm = alarmTimePicker.getHour();
                int minAlarm = alarmTimePicker.getMinute();

                calendar.set(Calendar.HOUR_OF_DAY, hourAlarm);
                calendar.set(Calendar.MINUTE, minAlarm);

                hourString = String.valueOf(hourAlarm);
                minString = String.valueOf(minAlarm);

                if (hourAlarm > 12) {
                    hourString = String.valueOf(hourAlarm - 12);
                }

                if (minAlarm < 10) {
                    minString = "0" + String.valueOf(minAlarm);
                }

                goOffTime = calendar.getTimeInMillis();

                if(goOffTime < olderTime) {
                    updateStatus.setText(Constant.STRING_TIME_SET_IN_THE_PAST);
                }
                else {
                    alarmListString.add(Constant.STRING_ALARM_IS_SET + hourString + Constant.STRING_COLON + minString);
                    goOffTimeList.add(goOffTime);

                    StringBuffer result = new StringBuffer();
                    for (int i = 0; i < alarmListString.size(); i++) {
                        result.append( alarmListString.get(i) + '\n' );
                    }
                    String mynewstring = result.toString();
                    setAlarmText(mynewstring);
                   // alarmOff.setEnabled(true);

                }


                for (int i = 0; i < goOffTimeList.size(); i++) {
                    alarmIntend.putExtra(Constant.KEY_EXTRA_INTENT, Constant.VALUE_EXTRA_INTENT_ALARM_ON);
                    pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntend, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, goOffTimeList.get(i), pendingIntent);
                    pendingIntents.add(pendingIntent);
                    requestCode++;
                }

            }
        });


        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarmText(Constant.STRING_ALARM_IS_OFF);
                alarmIntend.putExtra(Constant.KEY_EXTRA_INTENT, Constant.VALUE_EXTRA_INTENT_ALARM_OFF);

                alarmManager.cancel(pendingIntents.get(pendingIntentId));
                Log.i("Cancel PI", "PI id is" + pendingIntentId);
                sendBroadcast(alarmIntend);
                pendingIntentId++;
            }
        });

    }

    private void initUI() {
        this.context = this;
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmOn = (Button) findViewById(R.id.alarmOn);
        alarmOff = (Button) findViewById(R.id.alarmOff);
        updateStatus = (TextView) findViewById(R.id.updateStatus);
    }

    private void initServices() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void setAlarmText(String text) {
        updateStatus.setText(text);
    }

}
