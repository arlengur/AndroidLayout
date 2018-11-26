package ru.arlen.androidlayout;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String MY_SERVICE = "MY_SERVICE";
    public static final String INTENT_ACTION  = "ru.arlen.INTENT_ACTION";
    private volatile boolean stop;

    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(MY_SERVICE, "onStartCommand");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sendBroadcast(new Intent(INTENT_ACTION));
                        Thread.sleep(1000);
                        if (stop) {
                            break;
                        }
                    }
                } catch (InterruptedException e) {

                }
            }
        }).start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w(MY_SERVICE, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(MY_SERVICE, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.w(MY_SERVICE, "onDestroy");
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MyService.class);
    }

    public int getSum(int a, int b) {
        return a + b;
    }

    public int getProd(int a, int b) {
        return a * b;
    }
}
