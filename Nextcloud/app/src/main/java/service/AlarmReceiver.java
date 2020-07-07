package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import URLAndInterface.Interface;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class AlarmReceiver extends BroadcastReceiver {
    String coursename,teachername,courselocation,starttime,phonenumber;
    int time;

    @Override
    public void onReceive(final Context context, Intent intent) {
        coursename=intent.getStringExtra("coursename")+"";
        teachername=intent.getStringExtra("teachername")+"";
        courselocation=intent.getStringExtra("courselocation")+"";
        time=intent.getIntExtra("time",1);
        starttime=intent.getStringExtra("starttime")+"";
        phonenumber=intent.getStringExtra("phonenumber")+""; //老师电话
        final String uptime=Integer.toString(time);
        Interface interface0=new Interface(context);
        interface0.sendMessage(teachername, coursename, "18861816303"
                , uptime, courselocation, new Interface.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onerror(String error) {

                    }
                });
    }
}
