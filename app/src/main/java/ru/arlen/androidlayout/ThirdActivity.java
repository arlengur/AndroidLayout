package ru.arlen.androidlayout;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static ru.arlen.androidlayout.MyService.INTENT_ACTION;

public class ThirdActivity extends Activity {
    private MyReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mReceiver = new MyReceiver();

        View toFirst = findViewById(R.id.btnThirdToFirst);
        toFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdActivity.this, FirstActivity.class));
            }
        });
        View toSecond = findViewById(R.id.btnThirdToSecond);
        toSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdActivity.this, SecondActivity.class));
            }
        });
        View toFourth = findViewById(R.id.btnThirdToFourth);
        toFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdActivity.this, FourthActivity.class));
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
