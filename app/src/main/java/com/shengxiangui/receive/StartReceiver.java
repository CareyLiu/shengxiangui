package com.shengxiangui.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shengxiangui.cn.XiLanHome_video_Activity;


public class StartReceiver extends BroadcastReceiver {
    public StartReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("StartReceiver", intent.getAction());
        //此处及是重启的之后，打开我们app的方法
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent intent1 = new Intent(context.getApplicationContext(), XiLanHome_video_Activity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //自启动APP（Activity）
            context.startActivity(intent1);
        }
    }
}
