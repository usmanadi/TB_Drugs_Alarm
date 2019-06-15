package com.example.usman.standup;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    static Context cont;
    private AlarmReceiver mReceiver;
    Calendar calendar = Calendar.getInstance();
    Calendar curCallendar = Calendar.getInstance();
    Button pilDate, setAl, stopAl;
    int mYear, mMonth, mDay;
    TextView alarmDate, judul, judulbb, hari, obat, status, judulDate;
    EditText beratBadan;
    Switch swStat;

    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID = 0;
    private static final int NOTIFICATION_ID_LANJUTAN = 1;
    private AlarmManager alarmManager;
    PendingIntent notifyPendingIntent, notifyPendingIntentLanjutan;
    ToggleButton alarmToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = getApplicationContext();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Intent notifyIntent = new Intent(cont, AlarmReceiver.class);
//        notifyIntent.putExtra("alarm", "cek1");

        Intent notifyIntentLanjutan = new Intent(cont, AlarmReceiver.class);
        notifyIntentLanjutan.putExtra("alarm", "cek2");
//        notifyPendingIntent = PendingIntent.getBroadcast
//                (cont, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyPendingIntentLanjutan = PendingIntent.getBroadcast
                (cont, NOTIFICATION_ID_LANJUTAN, notifyIntentLanjutan, PendingIntent.FLAG_UPDATE_CURRENT);
        curCallendar.setTimeInMillis(System.currentTimeMillis());
        alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        judul = (TextView)findViewById(R.id.judul);
        judulbb = (TextView)findViewById(R.id.judulBb);
        //hari = (TextView)findViewById(R.id.hari);
        obat = (TextView)findViewById(R.id.obat);
        //status = (TextView)findViewById(R.id.status);
        alarmDate = (TextView)findViewById(R.id.alarmDate);
        judulDate = (TextView)findViewById(R.id.judulDate);
        setAl = (Button)findViewById(R.id.setAlarm);
        stopAl = (Button)findViewById(R.id.stopAlarm);
        beratBadan = (EditText)findViewById(R.id.beratBadan);
        //swStat = (Switch)findViewById(R.id.swStatus);
        pilDate = (Button)findViewById(R.id.btnDate);
        pilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                //Create a datepicker dialog where user can choose date inside calendar
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                alarmDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        setAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = beratBadan.getText().toString();
                String b = "";
                int berbad = Integer.parseInt(a);
                if (berbad>=30 && berbad<=37 ){
                    b = "2 tablet 4FDC";
                }else if(berbad>=38 && berbad<=54){
                    b = "3 tablet 4FDC";
                }else if(berbad>=55 && berbad<=70){
                    b = "4 tablet 4FDC";
                }else if (berbad>=71){
                    b = "5 tablet 4FDC";
                }
                judul.setText("TAHAP INTENSIF OAT KATEGORI 1");
                judulbb.setText("Berat badan : "+beratBadan.getText());
                beratBadan.setVisibility(View.GONE);
                judulDate.setText("Tanggal Mulai : "+alarmDate.getText());
//                hari.setVisibility(View.VISIBLE);
//                hari.setText("Hari ke : ");
                obat.setVisibility(View.VISIBLE);
                obat.setText("Obat : "+b);
//                status.setVisibility(View.VISIBLE);
//                status.setText("Status : ");
//                swStat.setVisibility(View.VISIBLE);
                setAl.setVisibility(View.GONE);
                stopAl.setVisibility(View.VISIBLE);
                pilDate.setVisibility(View.GONE);
                alarmDate.setVisibility(View.GONE);
                Intent notifyIntent = new Intent(cont, AlarmReceiver.class);
                notifyIntent.putExtra("alarm", "cek1");
                notifyIntent.putExtra("judul", "TAHAP INTENSIF OAT KATEGORI 1");
                notifyIntent.putExtra("bb", a);
                notifyIntent.putExtra("date", "Tanggal Mulai : "+alarmDate.getText());
                notifyIntent.putExtra("obat", b);
                notifyPendingIntent = PendingIntent.getBroadcast
                        (cont, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                /// Instansiasi kalender
                Calendar calendarLanjutan = Calendar.getInstance();

                calendarLanjutan.setTimeInMillis(System.currentTimeMillis());

                calendar.set(Calendar.HOUR_OF_DAY, 05);
                calendarLanjutan.set(Calendar.HOUR_OF_DAY, 14);

                calendar.set(Calendar.MINUTE, 16);
                calendarLanjutan.set(Calendar.MINUTE, 20);

                // setRepeating() lets you specify a precise custom interval--in this case,
                // 20 minutes.
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, notifyPendingIntent);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                        AlarmManager.INTERVAL_DAY, notifyPendingIntent);

//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarLanjutan.getTimeInMillis(),
//                        1000 * 60 * 1, notifyPendingIntentLanjutan);

                //Set the toast message for the "on" case
               String toastMessage = getString(R.string.alarm_on_toast);
                Toast.makeText(cont, toastMessage, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        stopAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cancel the alarm and notification if the alarm is turned off
                alarmManager.cancel(notifyPendingIntent);
                alarmManager.cancel(notifyPendingIntentLanjutan);
                mNotificationManager.cancelAll();

                mReceiver = new AlarmReceiver(0);

                //Set the toast message for the "off" case
               String toastMessage = getString(R.string.alarm_off_toast);
                Toast.makeText(cont, toastMessage, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //Check if the Alarm is already set, and check the toggle accordingly
//        boolean alarmUp = (PendingIntent.getBroadcast(cont, NOTIFICATION_ID, notifyIntent,
//                PendingIntent.FLAG_NO_CREATE) != null);
        boolean alarmUoLanjutan = (PendingIntent.getBroadcast(cont, NOTIFICATION_ID_LANJUTAN, notifyIntentLanjutan,
                PendingIntent.FLAG_NO_CREATE)!=null);

//        alarmToggle.setChecked(alarmUp && alarmUoLanjutan);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                if(isChecked){

//                    /// Instansiasi kalender
//                    Calendar calendar = Calendar.getInstance();
//                    Calendar calendarLanjutan = Calendar.getInstance();
//
//                    calendar.setTimeInMillis(System.currentTimeMillis());
//                    calendarLanjutan.setTimeInMillis(System.currentTimeMillis());
//
//                    calendar.set(Calendar.HOUR_OF_DAY, 01);
//                    calendarLanjutan.set(Calendar.HOUR_OF_DAY, 01);
//
//                    calendar.set(Calendar.MINUTE, 15);
//                    calendarLanjutan.set(Calendar.MINUTE, 20);
//
//                    // setRepeating() lets you specify a precise custom interval--in this case,
//                    // 20 minutes.
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                            1000 * 60 * 1, notifyPendingIntent);
//
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarLanjutan.getTimeInMillis(),
//                            1000 * 60 * 1, notifyPendingIntentLanjutan);
//
//                    //Set the toast message for the "on" case
//                    toastMessage = getString(R.string.alarm_on_toast);
                } else {
//                    //Cancel the alarm and notification if the alarm is turned off
//                    alarmManager.cancel(notifyPendingIntent);
//                    alarmManager.cancel(notifyPendingIntentLanjutan);
//                    mNotificationManager.cancelAll();
//
//                     mReceiver = new AlarmReceiver(0);
//
//                    //Set the toast message for the "off" case
//                    toastMessage = getString(R.string.alarm_off_toast);
                }

                //Show a toast to say the alarm is turned on or off
//                Toast.makeText(cont, toastMessage, Toast.LENGTH_SHORT)
//                        .show();
            }
        });

    }

    public static Context getContext(){
        return cont;
    }

}
