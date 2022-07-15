package com.shengxiangui.cn.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.shengxiangui.cn.R;

public class MyActivity extends Activity {
    MyServiceConnection conn;
    Button btAllWinner;
    Button btn328;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAllWinner = findViewById(R.id.btAllWinner);
        btn328 = findViewById(R.id.bt328);
        btAllWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, MyService.class);
                conn = new MyServiceConnection();

                bindService(intent, conn, BIND_AUTO_CREATE);
            }
        });

        btn328.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conn.setMethod1(MyActivity.this);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        conn.setMethod2();


    }
}
