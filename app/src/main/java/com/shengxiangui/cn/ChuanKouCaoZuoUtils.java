package com.shengxiangui.cn;

import android.util.Log;

import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttPublish;
import com.shengxiangui.mqtt.YingJianZhiLing;
import com.wits.serialport.SerialPort;
import com.shengxiangui.mqtt.Addr;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class ChuanKouCaoZuoUtils {


    public static String TAG = ChuanKouCaoZuoUtils.class.getSimpleName();
    public static SerialPort mSerialPort;
    public static OutputStream mOutputStream;
    public static InputStream mInputStream;
    public static openCloseMCActivity.ReadThread mReadThread;

    public static void lianJieChuanKou() {
        //串口读写数据流
        try {
            mSerialPort = getSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            Log.i(TAG, "-----------------mReadThread.start");
        } catch (SecurityException e) {
            Log.e(TAG, "-----------------SecurityException");
        } catch (IOException e) {
            Log.e(TAG, "-----------------IOException");
        } catch (InvalidParameterException e) {
            Log.e(TAG, "-----------------InvalidParameterException");
        }
    }

    //打开串口
    public static SerialPort getSerialPort() throws SecurityException, IOException,
            InvalidParameterException {
        // M0,M1,M2默认串口号为ttyS7,M3串口号是ttyS5
        String Adress = "/dev/ttyS0";


        File file = new File(Adress);
        Log.e(TAG, file.toString());
        if (!file.canRead() || !file.canWrite()) {
//            Toast.makeText(this, "请选择正确主板！", Toast.LENGTH_SHORT).show();
//            finish();
        }

        mSerialPort = new SerialPort(file, 9600, 0);

        return mSerialPort;
    }

//    /**
//     * 开柜
//     *
//     * @param xiangZiHao 箱子号
//     * @param zlm        指令码
//     */
//    public static void kaiGui(int xiangZiHao, int zlm) {
//
//        try {
//            byte[] mBuffer = YingJianZhiLing.sendZhiLing(xiangZiHao, zlm);
//            Log.i(TAG, "成功发送");
//            if (mOutputStream != null) {
//                mOutputStream.write(mBuffer);
//                Log.e(TAG, "send kaigui 9 byte to serialport.ok");
//            } else {
//                Log.e(TAG, "mOutputStream:--------null");
//            }
//
//
//            String msg = "K" + xiangZiHao + "11" + ".";
//
//            AndMqtt.getInstance().publish(new MqttPublish().setMsg(msg).setQos(2).setTopic(Addr.ccidAddr), new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Log.i("mqtt", "发送成功");
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 展示蔬菜柜的实时数据
     *
     * @param menBianHao         门编号
     * @param chengPanBianHao    秤盘编号
     * @param shangPinBianHao    商品编号
     * @param shangPinZhongLiang 商品重量
     */
    public void shuCaiGuiShiShiShuJu(String menBianHao, String chengPanBianHao, String shangPinBianHao, String shangPinZhongLiang) {
        String msg = "k" + menBianHao + chengPanBianHao + shangPinBianHao + shangPinZhongLiang + ".";

        AndMqtt.getInstance().publish(new MqttPublish().setMsg(msg).setQos(2).setTopic(Addr.ccidAddr), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("mqtt", "发送成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    /**
     * 执行结果 01 打开 02 关闭
     *
     * @param str
     */
    public void zhiXingJieGuo(String str) {

        String msg = "k" + str + ".";

        AndMqtt.getInstance().publish(new MqttPublish().setMsg(msg).setQos(2).setTopic(Addr.ccidAddr), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                if (str.equals("1")) {
                    Log.i("mqtt", "发送打开");
                } else if (str.equals("2")) {
                    Log.i("mqtt", "发送关闭");
                }
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("mqtt", "发送失败");
            }
        });

    }

}
