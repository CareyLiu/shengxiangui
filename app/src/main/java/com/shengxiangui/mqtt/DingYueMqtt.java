package com.shengxiangui.mqtt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttSubscribe;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class DingYueMqtt {
    public static void hardWareMqtt() {


        AndMqtt.getInstance().subscribe(new MqttSubscribe()
                .setTopic(Addr.ccidAddr)
                .setQos(2), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "订阅成功" );
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:68)-onFailure:-&gt;订阅失败");
            }
        });
    }
}
