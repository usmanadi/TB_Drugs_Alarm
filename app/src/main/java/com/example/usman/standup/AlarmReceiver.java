package com.example.usman.standup;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;
    private static int count = 0, countl = 0;
    private static String temp, judul, bb, obat, date;

    public AlarmReceiver() {
    }

    public AlarmReceiver(int i) {
        count = i;
        countl = i;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);



        //Create the content intent for the notification, which launches this activity
        Intent contentIntent = new Intent(context, Main2Activity.class);
        contentIntent.putExtra("judul", judul);
        contentIntent.putExtra("date", date);
        contentIntent.putExtra("bb", bb);
        contentIntent.putExtra("obat", obat);
        String cekok = intent.getExtras().getString("judul");
        String extra = intent.getExtras().getString("alarm");
        if (cekok != null){
            Log.i("masuk cekok", "awal");
            judul = intent.getExtras().getString("judul");
            bb = intent.getExtras().getString("bb");
            obat = intent.getExtras().getString("obat");
            date = intent.getExtras().getString("date");
            Log.i("masuk cekok", judul);
        }

        if (extra != null){
            Log.i("masuk not null", "true");
            temp = extra;
        }else{
            Log.i("masuk else", "else");
            extra = temp;
        }


        AlarmManager manager = (AlarmManager)MainActivity.getContext().getSystemService(MainActivity.getContext().ALARM_SERVICE);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(MainActivity.getContext(), NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent contentPendingIntentL = PendingIntent.getActivity(MainActivity.getContext(), 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (extra.equals("cek1")){
            count+=1;
            //Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_stand_up)
                    .setContentTitle("Minum Obat Hari ke"+count)
                    .setContentIntent(contentPendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            //Deliver the notification
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            Log.i("count cek 1", "lewat cek 1"+count);

            if (count>56){
                Intent contentIntentC = new Intent(MainActivity.getContext(), AlarmReceiver.class);
                contentPendingIntent = PendingIntent.getBroadcast(MainActivity.getContext(), NOTIFICATION_ID, contentIntentC, PendingIntent.FLAG_UPDATE_CURRENT);

                Log.i("lebih dari 2", "lewat"+count);
                manager.cancel(contentPendingIntent);
            }

        }else{
            countl+=1;
            //Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_stand_up)
                    .setContentTitle(extra+countl)
                    .setContentText(context.getString(R.string.notification_text))
                    .setContentIntent(contentPendingIntentL)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            //Deliver the notification
            notificationManager.notify(1, builder.build());
            Log.i("count cek 2", "lewat cek 2"+countl);

            if (countl>3){
                Intent contentIntentC = new Intent(MainActivity.getContext(), AlarmReceiver.class);
                contentPendingIntentL = PendingIntent.getBroadcast(MainActivity.getContext(), 1, contentIntentC, PendingIntent.FLAG_UPDATE_CURRENT);

                manager.cancel(contentPendingIntentL);
                notificationManager.cancelAll();
                Log.i("lebih dari 3", "lewat "+countl);
            }
        }

    }

}