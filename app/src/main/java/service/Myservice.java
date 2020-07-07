package service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by Administrator on 2018/1/7 0007.
 */

public class Myservice extends Service {
    String coursename,teachername,courselocation,starttime,phonenumber;
    int time;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        coursename=intent.getStringExtra("coursename")+"";
        teachername=intent.getStringExtra("teachername")+"";
        courselocation=intent.getStringExtra("courselocation")+"";
        time=intent.getIntExtra("time",1);
        starttime=intent.getStringExtra("starttime")+"";
        phonenumber=intent.getStringExtra("phonenumber")+"";


        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //TODO: 在这个地方换成60000*time,然后再判断星期
        int five = 1000*time; // 这是60s
        long triggerAtTime = SystemClock.elapsedRealtime() + five;
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("coursename",coursename);
        i.putExtra("teachername",teachername);
        i.putExtra("courselocation",courselocation);
        i.putExtra("time",time);
        i.putExtra("phonenumber",phonenumber);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
