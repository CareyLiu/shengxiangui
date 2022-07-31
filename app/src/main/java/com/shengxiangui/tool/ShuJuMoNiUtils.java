package com.shengxiangui.tool;

import android.util.Log;

import com.shengxiangui.cn.activity.ShengXianZhuYeActivity;

public class ShuJuMoNiUtils {
    //模拟开门
    public static void monikaimen() {


        byte[] bytes = new byte[8];
        bytes[0] = (byte) 0XAA;
        bytes[1] = 1;//字节长度
        bytes[2] = 1;//指令码
        bytes[3] = 2;//柜门地址
        bytes[4] = 1;//柜门状态 1开 2 关
        ShengXianZhuYeActivity.buffer_moni = bytes;

        Log.i("moni",
                "开门" +
                        "指令码" + bytes[2] +
                        "柜门地址" + bytes[3] +
                        "柜门状态" + "开");

    }

    //模拟关门
    public static void moniguanmen() {


        byte[] bytes = new byte[8];
        bytes[0] = (byte) 0XAA;
        bytes[1] = 1;//字节长度
        bytes[2] = 1;//指令码
        bytes[3] = 2;//柜门地址
        bytes[4] = 2;//柜门状态 1开 2 关
        ShengXianZhuYeActivity.buffer_moni = bytes;

        Log.i("moni",
                "开门" +
                        "指令码" + bytes[2] +
                        "柜门地址" + bytes[3] +
                        "柜门状态" + "关");

    }


    //模拟清零
    public static void moniqingling() {

        byte[] bytes = new byte[8];
        bytes[0] = (byte) 0XAA;
        bytes[1] = 1;//位数
        bytes[2] = 3;//指令
        bytes[3] = 2;//门地址
        bytes[4] = 2;//秤盘号
        ShengXianZhuYeActivity.buffer_moni = bytes;

    }


    //模拟校准
    public static void monijiaozhun() {

    }


    /**
     * 模拟上传货物 0 表示正常上传 1 表示单个上传 2查询所有
     *
     * @param string
     */
    public static void moniHuowu(String string) {
        byte[] bytes = new byte[9];
        bytes[0] = (byte) 0XAA;
        bytes[1] = 1;//以下字节长度
        bytes[2] = 2;//指令码
        bytes[3] = Byte.parseByte(string);

        bytes[4] = 2;//柜门地址

        bytes[5] = 12;//重量
        bytes[6] = 00;//重量

        bytes[7] = 06;
        bytes[8] = 00;


        ShengXianZhuYeActivity.buffer_moni = bytes;


    }

    /**
     * 模拟上传货物 0 表示正常上传 1 表示单个上传 2查询所有
     *
     * @param string
     */
    public static void moniHuowu_butong(String string) {
        byte[] bytes = new byte[19];
        bytes[0] = (byte) 0XAA;
        bytes[1] = 1;//以下字节长度
        bytes[2] = 2;//指令码
        bytes[3] = Byte.parseByte(string);

        bytes[4] = 2;//柜门地址

        bytes[5] = 1;//重量
        bytes[6] = 2;//重量

        bytes[7] = 3;//重量
        bytes[8] = 4;//重量
        bytes[9] = 5;//重量

        bytes[10] = 6;//重量
        bytes[11] = 7;//重量
        bytes[12] = 8;//重量

        bytes[13] = 9;//重量
        bytes[14] = 10;//重量
        bytes[14] = 11;//重量
        bytes[14] = 12;//重量
        bytes[14] = 13;//重量
        bytes[14] = 14;//重量
        bytes[14] = 15;//重量
        bytes[14] = 16;//重量
        bytes[14] = 17;//重量

        ShengXianZhuYeActivity.buffer_moni = bytes;

    }

//    //模拟补货员开门
//    public static void moniBuHuoYuanKaimen() {
//
//
//        byte[] bytes = new byte[8];
//        bytes[0] = (byte) 0XAA;
//        bytes[1] = 1;//字节长度
//        bytes[2] = 1;//指令码
//        bytes[3] = 2;//柜门地址
//        bytes[4] = 1;//柜门状态 1开 2 关
//        ShengXianZhuYeActivity.buffer_moni = bytes;
//
//        Log.i("moni",
//                "开门" +
//                        "指令码" + bytes[2] +
//                        "柜门地址" + bytes[3] +
//                        "柜门状态" + "开");
//
//    }

}
