package com.example.alrm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.security.AllPermission;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    TextView textView;
    Button setAlarm;
    int mHour,mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker)findViewById(R.id.timepicker);
        textView = (TextView)findViewById(R.id.tvTime);
        setAlarm = (Button)findViewById(R.id.btnSet);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                mHour=i;
                mMin=i1;
                if(textView.getText()!=null){
                    textView.setText(" ");
                    textView.setText(textView.getText().toString()+ " "+mHour + ":" +mMin);
                    setAlarm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setTimer(textView);
                        }
                    });

                }



            }
        });
    }
    public void setTimer(View v)
    {
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Date date = new Date();
        Calendar calAlarm = Calendar.getInstance();
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(date);
        calAlarm.setTime(date);
        calAlarm.set(Calendar.HOUR_OF_DAY,mHour);
        calAlarm.set(Calendar.MINUTE,mMin);
        calAlarm.set(Calendar.SECOND,0);
        if(calAlarm.before(calNow))
        {
            calAlarm.add(Calendar.DATE,1);
        }

        Intent i = new Intent(MainActivity.this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,24444,i,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calAlarm.getTimeInMillis(),pendingIntent);

    }
}
