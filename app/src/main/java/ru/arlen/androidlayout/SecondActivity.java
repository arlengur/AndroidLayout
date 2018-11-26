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

public class SecondActivity extends Activity {
    public static final String SECOND_ACTIVITY = "SECOND_ACTIVITY";
    private MyService mBoundService;
    private Random random;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((MyService.MyBinder) service).getService();
            Log.w(SECOND_ACTIVITY, "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            Log.w(SECOND_ACTIVITY, "disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        random = new Random();

        View toFirst = findViewById(R.id.btnSecondToFirst);
        toFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, FirstActivity.class));
            }
        });
        View toThird = findViewById(R.id.btnSecondToThird);
        toThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });
        View toFourth = findViewById(R.id.btnSecondToFourth);
        toFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, FourthActivity.class));
            }
        });
        final TextView sumText = findViewById(R.id.textSum);
        View sumBtn = findViewById(R.id.btnSum);
        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = random.nextInt(10);
                int b = random.nextInt(10);
                int sum = mBoundService.getSum(a, b);
                sumText.setText(a + " + " + b + " = " + sum);
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
