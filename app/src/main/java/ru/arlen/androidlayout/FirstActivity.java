package ru.arlen.androidlayout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static ru.arlen.androidlayout.MyService.INTENT_ACTION;

public class FirstActivity extends Activity {
    private static final String FIRST_ACTIVITY = "FIRST_ACTIVITY";
    private MyReceiver mReceiver;
    private boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mReceiver = new MyReceiver();

        View toSecond = findViewById(R.id.btnFirstToSecond);
        toSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
            }
        });
        View toThird = findViewById(R.id.btnFirstToThird);
        toThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, ThirdActivity.class));
            }
        });
        View toFourth = findViewById(R.id.btnFirstToFourth);
        toFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, FourthActivity.class));
            }
        });

        View send = findViewById(R.id.btnSendBC);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MyService.class);
                if (!started) {
                    started = true;
                    Log.w(FIRST_ACTIVITY, "start service");
                    startService(intent);
                } else {
                    started = false;
                    Log.w(FIRST_ACTIVITY, "stop service");
                    stopService(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(INTENT_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
