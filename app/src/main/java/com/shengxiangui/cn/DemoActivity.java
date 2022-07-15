package com.shengxiangui.cn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.shengxiangui.tool.TcnUtility;

public class DemoActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isOpen) {
            int retval = MyApp.driver.ResumeUsbPermission();
            if (retval == 0) {
                retval = MyApp.driver.ResumeUsbList();
                if (retval == -1) {
                    Toast.makeText(this, "打开失败，没有枚举到对应的usb口，请检查再此尝试", Toast.LENGTH_SHORT).show();
                    MyApp.driver.CloseDevice();
                } else if (retval == 0) {
                    if (MyApp.driver.mDeviceConnection != null) {
                        if (!MyApp.driver.UartInit()) {
                            Toast.makeText(this, "初始化失败", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(this, "打开了设备", Toast.LENGTH_SHORT).show();
                            isOpen = true;
                            //btn_peizhi.setEnabled(true);
                            new ReadThread().start();//开启线程
                        }

                    } else {
                        Toast.makeText(this, "打开失败", Toast.LENGTH_SHORT).show();

                    }
                }
            }

        } else {
            //关闭串口
        }


        handler = new Handler() {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg) {
                // 截取返回值中的体温数据
                String num = (String) msg.obj;

                //  Toast.makeText(this, "接收的数据是：" + num + "", Toast.LENGTH_SHORT).show();

                // Log.e(TAG, "num ------- " + num.replace(" ", ""));
                // String temp = num.replace(" ", "");

                //     String humidity = temp.substring(12, 16);

                //  将截取的返回体温数据由16进制转换为10进制
                //  int t = TcnUtility.hexToInt(humidity);

                byte[] bytes = (byte[]) msg.obj;


                byte zlm = bytes[3];
                switch (zlm) {
                    case 1://柜门状态
                        int jiHaoMen = bytes[4];//门地址
                        int kaiGuanZhuangTai = bytes[5];


                        break;
                    case 2://上传的电子价签信息


                        int menDiZhi = bytes[4];//门地址
                        int jiaQianDiZhi = bytes[5];//价签地址
                        int zhongLiang1 = bytes[6];//重量1
                        int zhongLiang2 = bytes[7];//重量2

                        break;

                }

            }
        };
    }


    private Handler handler;
    private boolean isOpen;

    public class ReadThread extends Thread {
        public void run() {
            byte[] buffer = new byte[4096];
            while (true) {
                Message msg = Message.obtain();
                if (!isOpen) {
                    break;
                }

                int length = MyApp.driver.ReadData(buffer, 4096);
                if (length > 0) {
                    //  String recv = toHexString(buffer, length);
                    msg.obj = buffer;
                    handler.sendMessage(msg);
                }
            }
        }
    }


    /**
     * 将byte[]数组转化为String类型
     *
     * @param arg    需要转换的byte[]数组
     * @param length 需要转换的数组长度
     * @return 转换后的String队形
     */
    private String toHexString(byte[] arg, int length) {
        String result = new String();
        if (arg != null) {
            for (int i = 0; i < length; i++) {
                result = result
                        + (Integer.toHexString(
                        arg[i] < 0 ? arg[i] + 256 : arg[i]).length() == 1 ? "0"
                        + Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])
                        : Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])) + " ";
            }
            return result;
        }
        return "";
    }


}
