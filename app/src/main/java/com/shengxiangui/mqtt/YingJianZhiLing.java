package com.shengxiangui.mqtt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.shengxiangui.cn.MyApp;

//硬件的操作
public class YingJianZhiLing {

    YingJianZhiLing yingJianZhiLing = null;

    public YingJianZhiLing getInstance() {

        if (yingJianZhiLing == null) {
            yingJianZhiLing = new YingJianZhiLing();
            return yingJianZhiLing;
        } else {
            return yingJianZhiLing;
        }
    }

    public static int baudRate = 9600;//波特率
    public static byte baudRate_byte;
    public static byte stopBit = 1;//停止位
    public static byte dataBit = 8;//数据位
    public static byte parity = 0;//比特位
    public static byte flowControl = 0;//控制流


    public static boolean daKaiSheBei(Context context) {

        boolean flag = false;
        if (MyApp.driver.UsbFeatureSupported()) {
            Dialog dialog = new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("您的手机不支持USB HOSE,请更换其他手机再试")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    }).create();


        }

        int retval = MyApp.driver.ResumeUsbPermission();

        if (retval == 0) {
            retval = MyApp.driver.ResumeUsbList();
            if (retval == -1) {
                Toast.makeText(context, "打开失败，没有枚举到对应的usb口，请检查再此尝试", Toast.LENGTH_SHORT).show();
                MyApp.driver.CloseDevice();
                flag = false;
            } else if (retval == 0) {
                if (MyApp.driver.mDeviceConnection != null) {
                    if (!MyApp.driver.UartInit()) {
                        Toast.makeText(context, "初始化失败", Toast.LENGTH_SHORT).show();
                        flag = false;
                    } else {
                        Toast.makeText(context, "打开了设备", Toast.LENGTH_SHORT).show();
                        if (MyApp.driver.SetConfig(baudRate, dataBit, stopBit, parity, flowControl)) {
                            Log.i("YINGJIAN", "Config successfully");
                        } else {
                            Log.i("YINGJIAN", "Config successfully");
                        }
                        flag = true;
                    }
                } else {
                    Toast.makeText(context, "打开失败", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            }
        }
        return flag;
    }

    //开柜操作

    /**
     * @param guiMenHao 开柜门
     * @return
     */
    public static byte[] kaiGui(int guiMenHao) {
        byte[] DATA = new byte[1];
        return caoZuo(1, DATA);
    }


    /**
     * @param guiMenHao 关柜门
     * @return
     */
    public static byte[] guanGui(int guiMenHao) {
        byte[] DATA = new byte[1];
        return caoZuo(4, DATA);
    }

    /**
     * 下传电子价签
     *
     * @param menDiZhi          门地址
     * @param jiaQianDiZhi      价签地址
     * @param jiaGe1            价格1
     * @param jiaGe2            价格2
     * @param hanZiChuanChangDu 汉字码长度
     * @param hanZiChuanNeirong 汉字码
     * @return
     */
    public static byte[] xiaChuanDianZiJiaQian(int menDiZhi, int jiaQianDiZhi, int jiaGe1,
                                               int jiaGe2, int hanZiChuanChangDu, int hanZiChuanNeirong) {
        byte[] DATA = new byte[6];
        DATA[0] = (byte) menDiZhi;
        DATA[1] = (byte) jiaQianDiZhi;
        DATA[2] = (byte) jiaGe1;
        DATA[3] = (byte) jiaGe2;
        DATA[4] = (byte) hanZiChuanChangDu;
        DATA[5] = (byte) hanZiChuanNeirong;


        return caoZuo(3, DATA);
    }


    /**
     * 查询单个
     * @param state 0正常取数据查询所有1查询单个 2查询所有
     * @param menDiZh 地址，2号柜门
     * @return
     */
    public static byte[] chaXunDanGe(int state, int menDiZh) {
        byte[] DATA = new byte[2];
        DATA[0] = (byte) state;
        DATA[1] = (byte) menDiZh;

        return caoZuo(6, DATA);
    }


    /**
     * 上传电子价签
     *
     * @param menDiZhi     门地址
     * @param jiaQianDiZhi 价签地址
     * @return
     */
    public static byte[] xiaChuanQingLing(int menDiZhi, int jiaQianDiZhi) {
        byte[] DATA = new byte[2];
        DATA[0] = (byte) menDiZhi;
        DATA[1] = (byte) jiaQianDiZhi;

        return caoZuo(3, DATA);
    }

    /**
     * @param ZLM  指令码
     * @param DATA M个字节的数据
     * @return
     */
    private static byte[] caoZuo(int ZLM, byte[] DATA) {

        int N = DATA.length + 3;

        int data = 0;
        for (int i = 0; i < DATA.length; i++) {
            data = data + DATA[i];
        }
        int leijiahe = 0x99 + N + ZLM + data;
        int LRCH = leijiahe / 256;
        int LRCL = leijiahe % 256;


        byte[] to_send = openDevice((byte) 0x99, (byte) N, (byte) ZLM, DATA, (byte) LRCH, (byte) LRCL);


        //写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
        //int retval = MyApp.driver.WriteData(to_send, to_send.length);
        return to_send;

    }


    /**
     * @param touxinxi 头信息 0x99
     * @param N        以下字节长度
     * @param ZLM      指令码
     * @param DATA     M个字节的数据
     * @param LRCH     以上所有字节的累加和==高字节
     * @param LRCL     低字节
     * @return
     */
    public static byte[] openDevice(byte touxinxi, byte N, byte ZLM, byte[] DATA, byte LRCH, byte LRCL) {

        byte[] bytes = new byte[DATA.length + 5];


        bytes[0] = touxinxi;
        bytes[1] = N;
        bytes[2] = ZLM;

        for (int i = 3; i < DATA.length - 2; i++) {
            for (int j = 0; j < DATA.length; j++) {
                bytes[i] = DATA[j];
            }
        }
        bytes[DATA.length + 4] = LRCH;
        bytes[DATA.length + 5] = LRCL;


        return bytes;
    }


}
