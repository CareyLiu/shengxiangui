package com.shengxiangui.cn.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.shengxiangui.cn.MyApp;
import com.shengxiangui.cn.R;

public class MyService extends Service {
    private static String TAG = "MyService";


    MyBinder downloadBinder = new MyBinder();

        @Override
        public void onCreate() {
            Log.i(TAG, "onCreate");
            super.onCreate();
        }

        Dialog dialog;

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i(TAG, "onStartCommand");


            return super.onStartCommand(intent, flags, startId);
        }


        Boolean screening = false;

        public class MyBinder extends Binder {
            public void setMethod1(Activity str) {
                // 方法1执行代码
                dialog = new Dialog(str);
                dialog.setContentView(R.layout.chuwugui_dialog_input);
                dialog.show();
            }

            public void setMethod2() {
                // 方法2执行代码
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "destroy");
            super.onDestroy();
        }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }


}

