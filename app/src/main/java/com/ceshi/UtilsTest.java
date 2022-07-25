package com.ceshi;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sample.Utils;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void isEmpty() {
        byte[] bytes = intToShort(65535);

        System.out.println(bytes[0]);
        System.out.println(bytes[1]);


        System.out.println(shortToInt(bytes[0], bytes[1]));

        System.out.println((byte) 500000);


    }

    public static byte[] intToShort(int num) {
        // 以65535为例，转short = -1	-> 1111 1111 1111 1111
        // 右移8位，转byte				-> 0000 0000 1111 1111	-> 1111 1111
        // 转byte					-> 1111 1111
        short n = (short) num;
        byte[] b = new byte[2];
        b[0] = (byte) (n >> 8);
        b[1] = (byte) n;
        return b;
    }

    public static int shortToInt(byte b0, byte b1) {
        // 以65535[-1,-1]为例
        // b[0]左移八位 -> 1111 1111	-> 1111 1111 0000 0000
        // b[1] 位与0xff去除高位1		-> 0000 0000 1111 1111
        // 位或					-> 1111 1111 1111 1111
        // 位与去除高位1		-> 0000 0000 0000 0000 1111 1111 1111 1111
        return (short) ((b0 << 8) | (b1 & 0xff)) & 0xffff;
    }

    public static void huoDeZiJie() {
//        byte[] packet = new byte[2];
//        // 包长度转2字节
//        packet[0] = (byte) (n >> 8);
//        packet[1] = (byte) (n & 0xFF);
//        System.out.println(packet[0]);
//        System.out.println(packet[1]);
//
//        // 2字节转回包长度
//        int p0 = packet[0] < 0 ? packet[0] + 256 : packet[0];
//        int p1 = packet[1] < 0 ? packet[1] + 256 : packet[1];
//
//        System.out.println(p0 << 8 | p1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void zhuanHua(short a) {


    }


    /**
     * 将int转为高字节在前，低字节在后的byte数组(大端)
     *
     * @param n int
     * @return byte[]
     */

    public static byte[] intToByteBig(int n) {
        byte[] b = new byte[4];

        b[3] = (byte) (n & 0xff);

        b[2] = (byte) (n >> 8 & 0xff);

        b[1] = (byte) (n >> 16 & 0xff);

        b[0] = (byte) (n >> 24 & 0xff);
        return b;

    }

    /**
     * 将int转为低字节在前，高字节在后的byte数组(小端)
     *
     * @param n int
     * @return byte[]
     */

    public static byte[] intToByteLittle(int n) {
        byte[] b = new byte[4];

        b[0] = (byte) (n & 0xff);

        b[1] = (byte) (n >> 8 & 0xff);

        b[2] = (byte) (n >> 16 & 0xff);

        b[3] = (byte) (n >> 24 & 0xff);
        return b;

    }

    /**
     * byte数组到int的转换(小端)
     *
     * @param bytes
     * @return
     */

    public static int bytes2IntLittle(byte[] bytes) {
        int int1 = bytes[0] & 0xff;
        int int2 = (bytes[1] & 0xff) << 8;
        int int3 = (bytes[2] & 0xff) << 16;
        int int4 = (bytes[3] & 0xff) << 24;
        return int1 | int2 | int3 | int4;

    }

    /**
     * byte数组到int的转换(大端)
     *
     * @param bytes
     * @return
     */

    public static int bytes2IntBig(byte[] bytes) {
        int int1 = bytes[3] & 0xff;
        int int2 = (bytes[2] & 0xff) << 8;
        int int3 = (bytes[1] & 0xff) << 16;
        int int4 = (bytes[0] & 0xff) << 24;
        return int1 | int2 | int3 | int4;

    }

    /**
     * 将short转为高字节在前，低字节在后的byte数组(大端)
     *
     * @param n short
     * @return byte[]
     */

    public static byte[] shortToByteBig(short n) {
        byte[] b = new byte[2];

        b[1] = (byte) (n & 0xff);

        b[0] = (byte) (n >> 8 & 0xff);
        return b;

    }

    /**
     * 将short转为低字节在前，高字节在后的byte数组(小端)
     *
     * @param n short
     * @return byte[]
     */

    public static byte[] shortToByteLittle(short n) {
        byte[] b = new byte[2];

        b[0] = (byte) (n & 0xff);

        b[1] = (byte) (n >> 8 & 0xff);
        return b;

    }

    /**
     * 读取小端byte数组为short
     *
     * @param b
     * @return
     */

    public static short byteToShortLittle(byte[] b) {
        return (short) (((b[1] << 8) | b[0] & 0xff));

    }

    /**
     * 读取大端byte数组为short
     *
     * @param b
     * @return
     */

    public static short byteToShortBig(byte[] b) {
        return (short) (((b[0] << 8) | b[1] & 0xff));

    }

    /**
     * long类型转byte[] (大端)
     *
     * @param n
     * @return
     */

    public static byte[] longToBytesBig(long n) {
        byte[] b = new byte[8];

        b[7] = (byte) (n & 0xff);

        b[6] = (byte) (n >> 8 & 0xff);

        b[5] = (byte) (n >> 16 & 0xff);

        b[4] = (byte) (n >> 24 & 0xff);

        b[3] = (byte) (n >> 32 & 0xff);

        b[2] = (byte) (n >> 40 & 0xff);

        b[1] = (byte) (n >> 48 & 0xff);

        b[0] = (byte) (n >> 56 & 0xff);
        return b;

    }

    /**
     * long类型转byte[] (小端)
     *
     * @param n
     * @return
     */

    public static byte[] longToBytesLittle(long n) {
        byte[] b = new byte[8];

        b[0] = (byte) (n & 0xff);

        b[1] = (byte) (n >> 8 & 0xff);

        b[2] = (byte) (n >> 16 & 0xff);

        b[3] = (byte) (n >> 24 & 0xff);

        b[4] = (byte) (n >> 32 & 0xff);

        b[5] = (byte) (n >> 40 & 0xff);

        b[6] = (byte) (n >> 48 & 0xff);

        b[7] = (byte) (n >> 56 & 0xff);
        return b;

    }

    /**
     * byte[]转long类型(小端)
     *
     * @param array
     * @return
     */

    public static long bytesToLongLittle(byte[] array) {
        return ((((long) array[0] & 0xff) << 0) | (((long) array[1] & 0xff) << 8) | (((long) array[2] & 0xff) << 16) | (((long) array[3] & 0xff) << 24) | (((long) array[4] & 0xff) << 32) | (((long) array[5] & 0xff) << 40) | (((long) array[6] & 0xff) << 48) | (((long) array[7] & 0xff) << 56));

    }

    /**
     * byte[]转long类型(大端)
     *
     * @param array
     * @return
     */

    public static long bytesToLongBig(byte[] array) {
        return ((((long) array[0] & 0xff) << 56) | (((long) array[1] & 0xff) << 48) | (((long) array[2] & 0xff) << 40) | (((long) array[3] & 0xff) << 32) | (((long) array[4] & 0xff) << 24) | (((long) array[5] & 0xff) << 16) | (((long) array[6] & 0xff) << 8) | (((long) array[7] & 0xff) << 0));

    }

}
