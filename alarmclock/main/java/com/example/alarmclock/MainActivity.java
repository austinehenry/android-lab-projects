package com.example.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    Button btnSetAlarm;
    TextView tvStatus;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link XML views with Java
        timePicker = findViewById(R.id.timePicker);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        tvStatus = findViewById(R.id.tvStatus);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        btnSetAlarm.setOnClickListener(v -> setAlarm());
    }

    private void setAlarm() {
        // Get selected hour and minute
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Create calendar instance with selected time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Create intent for BroadcastReceiver
        Intent intent = new Intent(this, AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Schedule the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Update status TextView
        tvStatus.setText("Alarm set for " + hour + ":" + String.format("%02d", minute));
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
}
