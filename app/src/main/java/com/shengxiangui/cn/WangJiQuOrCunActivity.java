package com.shengxiangui.cn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WangJiQuOrCunActivity extends Activity {
    @BindView(R.id.rl_qujianma)
    RelativeLayout rlQujianma;
    @BindView(R.id.rl_shoujimima)
    RelativeLayout rlShoujimima;
    @BindView(R.id.tv_fanhuishouye)
    TextView tvFanhuishouye;
    @BindView(R.id.rl_fanhuishouye)
    RelativeLayout rlFanhuishouye;

    public String shouJiHaoOrQuJianMa;
    @BindView(R.id.tv_daojishifanhui)
    TextView tvDaojishifanhui;
    public boolean xianChengFlag = true;
    Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wangji_qujianma_or_shoujimima);
        ButterKnife.bind(this);
        shouJiHaoOrQuJianMa = getIntent().getStringExtra("shouJiHaoOrQuJianMa");
        if (shouJiHaoOrQuJianMa.equals("1")) {

            rlQujianma.setVisibility(View.VISIBLE);

        } else if (shouJiHaoOrQuJianMa.equals("2")) {

            rlShoujimima.setVisibility(View.VISIBLE);
        }


        rlFanhuishouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WangJiQuOrCunActivity.this, XiLanHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        final handler handle = new handler(tvDaojishifanhui);
        // 线程
        thread = new Thread() {
            public void run() {
                int i = 60;
                while (xianChengFlag) {
                    // 获得消息，并写好message中内容，这里采用了回收机制
                    Message msg = handle.obtainMessage();// 1、新建空消息


                    if (i == 0) {
                        msg.obj = "over";
                    } else {
                        msg.obj = (i--) + "s";// 2、带上消息内容
                    }
                    handle.sendMessage(msg);// 3、发送消息
                    try {
                        if (thread != null || thread.isAlive()) {
                            thread.sleep(1000);
                        }

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            }

        };
        thread.start();

    }

    public class handler extends Handler {

        TextView showCounter;

        public handler(TextView showCounter) {
            super();
            this.showCounter = showCounter;
        }

        // 处理消息
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            Log.i("QuJianActivity", text);
            if (text.equals("over")) {
                thread.interrupt();
                finish();
            } else {
                showCounter.setText(text);
            }


        }

    }

    /**
     * @param context             上下文
     * @param shouJiHaoOrQuJianMa 2 手机号 密码 1 取件码
     */
    public static void actionStart(Context context, String shouJiHaoOrQuJianMa) {
        Intent intent = new Intent(context, WangJiQuOrCunActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("shouJiHaoOrQuJianMa", shouJiHaoOrQuJianMa);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xianChengFlag = false;
        thread.interrupt();
    }
}
