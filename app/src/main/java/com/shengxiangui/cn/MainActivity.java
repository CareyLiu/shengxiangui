package com.shengxiangui.cn;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btAllWinner, bt328, bt339, bt972, bt3568, bt3128;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        DisplayMetrics dm = getResources().getDisplayMetrics();

        int screenWidth = dm.widthPixels;

        int screenHeight = dm.heightPixels;

        System.out.println("width" + screenWidth + "height" + screenHeight);
    }

    private void initView() {
        btAllWinner = (Button) findViewById(R.id.btAllWinner);
        bt328 = (Button) findViewById(R.id.bt328);
        bt339 = (Button) findViewById(R.id.bt339);
        bt972 = findViewById(R.id.bt972);
        bt3568 = findViewById(R.id.bt3568);
        bt3128 = findViewById(R.id.bt312);
        btAllWinner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle data = new Bundle();

                data.putInt("myinfo", 0);//把主板型号的数组下标发送给controlActivity

                intent.putExtra("data", data);
                System.out.println("1111" + data);
                intent.setClass(MainActivity.this, openCloseMCActivity.class);
                startActivity(intent);                 //启动相应controlActivity
            }
        });

        bt328.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle data = new Bundle();
                data.putInt("myinfo", 5);
                intent.putExtra("data", data);
                intent.setClass(MainActivity.this, openCloseMCActivity.class);
                startActivity(intent);
            }
        });

        bt339.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle data = new Bundle();
                data.putInt("myinfo", 8);
                intent.putExtra("data", data);
                intent.setClass(MainActivity.this, openCloseMCActivity.class);
                startActivity(intent);
            }
        });
        bt972.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle data = new Bundle();
                data.putInt("myinfo", 9);
                intent.putExtra("data", data);
                intent.setClass(MainActivity.this, openCloseMCActivity.class);
                startActivity(intent);
            }
        });
        bt3568.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle data = new Bundle();
                data.putInt("myinfo", 10);
                intent.putExtra("data", data);
                intent.setClass(MainActivity.this, openCloseMCActivity.class);
                startActivity(intent);
            }
        });
        bt3128.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle data = new Bundle();
                data.putInt("myinfo", 11);
                intent.putExtra("data", data);
                intent.setClass(MainActivity.this, openCloseMCActivity.class);
                startActivity(intent);
            }
        });
    }
}
