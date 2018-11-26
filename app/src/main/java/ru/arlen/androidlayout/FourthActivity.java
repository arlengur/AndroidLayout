package ru.arlen.androidlayout;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import static ru.arlen.androidlayout.MyService.newIntent;

public class FourthActivity extends Activity {
    private static final String FOURTH_ACTIVITY = "FOURTH_ACTIVITY";
    private MyService mBoundService;
    private Random random;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((MyService.MyBinder) service).getService();
            Log.w(FOURTH_ACTIVITY, "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            Log.w(FOURTH_ACTIVITY, "disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        random = new Random();

        View toFirst = findViewById(R.id.btnFourthToFirst);
        toFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FourthActivity.this, FirstActivity.class));
            }
        });
        View toSecond = findViewById(R.id.btnFourthToSecond);
        toSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FourthActivity.this, SecondActivity.class));
            }
        });
        View toThird = findViewById(R.id.btnFourthToThird);
        toThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FourthActivity.this, ThirdActivity.class));
            }
        });

        final TextView prodText = findViewById(R.id.textProd);
        View prodBtn = findViewById(R.id.btnProd);
        prodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = random.nextInt(10);
                int b = random.nextInt(10);
                int prod = mBoundService.getProd(a, b);
                prodText.setText(a + " * " + b + " = " + prod);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(newIntent(this), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
    }
}
