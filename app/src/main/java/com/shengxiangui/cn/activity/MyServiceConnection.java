package com.shengxiangui.cn.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyServiceConnection implements ServiceConnection {
    private static String TAG = "MyServiceConnection";
    private MyService.MyBinder myBinder = null;

    // 连接初始化
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        myBinder = (MyService.MyBinder) service;
        Log.e(TAG, "onServiceConnected");
    }

    // 方法1-自定义方法
    public void setMethod1(Activity str) {
        if (myBinder == null) {
            Log.e(TAG, "non binder");
        } else {
            myBinder.setMethod1(str);
        }

    }

    // 方法2
    public void setMethod2() {
        myBinder.setMethod2();
    }

    // 连接错误
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "onServiceDisconnected");
    }
}