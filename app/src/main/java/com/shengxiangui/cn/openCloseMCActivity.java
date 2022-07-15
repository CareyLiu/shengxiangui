/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shengxiangui.cn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.wits.serialport.SerialPort;

public class openCloseMCActivity extends Activity {

    public static SerialPort mSerialPort;
    public static OutputStream mOutputStream;
    public static InputStream mInputStream;
    public static ReadThread mReadThread;
    private String TAG = "openCloseMCActivity";
    private Button mbtnSet = null;
    private Button mbtnOff = null;
    private TextView txtMsg = null;
    private EditText etxtTimes = null;
    private boolean mFlag;
    private Timer timer;
    private Long onTimes = (long) 0;

    private int boardNum = 0;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    if (mReadThread != null)
                        mReadThread.interrupt();
                    txtMsg.setText("即将关机,系统将在" + onTimes + "秒后自动开机!");
                    Log.i(TAG, "mReadThread exit!");
                    myHandler.postDelayed(SDRunnable, 3000);
                    break;

                case 2:

                    Log.i(TAG, msg.obj + "");


                    //   myHandler.postDelayed(SDRunnable, 3000);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    Runnable SDRunnable = new Runnable() {
        public void run() {
            shutDown();
        }
    };

    //读取串口返回数据
    public class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (mFlag) {
                int size;
                try {

                    byte[] buffer = new byte[1];
                    if (mInputStream == null) {

                        break;
                    }

                    size = mInputStream.read(buffer);

                    if (size > 0) {


                        for (int i = 0; i < size; i++) {

                            Log.i(TAG, buffer[i] + "");


                            Message msg = new Message();
                            msg.obj = buffer[i];
                            msg.arg1 = 2;
                            myHandler.sendMessage(msg);
                            //如果串口干扰返回错误数据，重新发送开柜指令
//                            Log.i(TAG,buffer[i]+"");
//                            if (buffer[i] == 240) {
//                                if (timer == null) {
//                                    timer = new Timer();
//                                    timer.schedule(new WriteTask_KaiGui(), 2000, 1000);
//                                }
//                                Log.i(TAG,
//                                        "Data error app will resent data==============="
//                                                + +buffer[i]);
//                                //串口返回0X55,说明单片机已经接收到正确开机数据.
//                            }
                        }
//                        for (int i = 0; i < size; i++) {
//                            //如果串口干扰返回错误数据,重新向单片机写入开机时间
//                            Log.i(TAG, buffer[i] + "");
//                            if (buffer[i] != 0x55) {
//                                if (timer == null) {
//                                    timer = new Timer();
//                                    timer.schedule(new WriteTask(), 2000, 1000);
//                                }
//                                Log.i(TAG,
//                                        "Data error app will resent data==============="
//                                                + +buffer[i]);
//                                //串口返回0X55,说明单片机已经接收到正确开机数据.
//                            } else if (buffer[i] == 0x55) {
//                                Log.i(TAG,
//                                        " Set boot time ok!"
//                                                + buffer[i]);
//                                size = 0;
//                                mFlag = false;
//                                if (timer != null) {
//                                    timer.cancel();
//                                    timer = null;
//                                    Log.i(TAG,
//                                            "timer cancel!");
//                                }
//                                Message msg = new Message();
//                                msg.arg1 = 1;
//                                myHandler.sendMessage(msg);
//
//                            }
//                        }
                        // flush_buffer
                        Arrays.fill(buffer, (byte) 0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    class WriteTask extends TimerTask {
        public void run() {

            writeOnTimeToMC(1, onTimes);
        }
    }


    public void shutDown() {
        Intent intentSetOff = new Intent();
        intentSetOff.setAction("wits.com.simahuan.shutdown");
        sendBroadcast(intentSetOff);
    }

    Button btKaiGui;
    Button chaXun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close_machine);
        btKaiGui = findViewById(R.id.btn_kaigui);
        chaXun = findViewById(R.id.btn_chaxun);
        getBoardNum();
        //etxtTimes = (EditText) findViewById(R.id.etxtTimes);

        txtMsg = (TextView) findViewById(R.id.textView1);
        mbtnSet = (Button) findViewById(R.id.btnSet);
        //mbtnOff = (Button) findViewById(R.id.btnOff);
        mbtnSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mFlag = true;
                //onTimes = Long.parseLong(etxtTimes.getText().toString());
                onTimes = (long) 120;  //设置120S后开机

                writeOnTimeToMC(1, onTimes);
                txtMsg.setText("正尝试设置开机时间,如长时间无响应,请选择正确主板型号");
            }
        });

        //串口读写数据流
        try {
            mSerialPort = getSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mFlag = true;
            Log.i(TAG, "-----------------mReadThread.start");
        } catch (SecurityException e) {
            Log.e(TAG, "-----------------SecurityException");
        } catch (IOException e) {
            Log.e(TAG, "-----------------IOException");
        } catch (InvalidParameterException e) {
            Log.e(TAG, "-----------------InvalidParameterException");
        }
    }

    private void getBoardNum() {
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        boardNum = data.getInt("myinfo");//接收主activity发送过来的主板编号
        System.out.println("111111" + boardNum);
    }

    //打开串口
    public SerialPort getSerialPort() throws SecurityException, IOException,
            InvalidParameterException {
        // M0,M1,M2默认串口号为ttyS7,M3串口号是ttyS5
        String Adress = "/dev/ttyS0";

        txtMsg.setText("请选择正确主板");
        File file = new File(Adress);
        Log.e(TAG, file.toString());
        if (!file.canRead() || !file.canWrite()) {
            Toast.makeText(this, "请选择正确主板！", Toast.LENGTH_SHORT).show();
            finish();
        }

        mSerialPort = new SerialPort(file, 9600, 0);
        return mSerialPort;
    }

    //关闭串口
    public void closeSerialPort() {
        Log.i(TAG, "closeSerialPort");
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 设置或取消自动开机时间
     *
     * @param flags 1 为开启自动开机,0为关闭
     * @param times 开机倒计时(单位秒) ,如果要取消自动开机,请将flags,times都设为0
     */
    public void writeOnTimeToMC(int flags, long times) {

        try {
            byte[] mBuffer = longToByteArray(flags, times);
            Log.e(TAG, "writeOnTimeToMC--------time=" + times);
            int i;
            for (i = 0; i < mBuffer.length; i++)
                Log.i(TAG, "BUFFER-----HASHCODE=" + mBuffer[i]);
            mReadThread = new ReadThread();
            mReadThread.start();

            if (mOutputStream != null) {
                mOutputStream.write(mBuffer);
                Log.e(TAG, "send data 9 byte to serialport.ok");
            } else {
                Log.e(TAG, "mOutputStream:--------null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //向单片机发送的数据9个byte
    public static byte[] longToByteArray(int flags, long times) {
        byte[] result = new byte[9];
        result[0] = (byte) 0x00;
        result[1] = (byte) 0xaa;
        result[2] = (byte) 0xff;
        result[3] = (byte) 0x55;

        result[4] = (byte) (flags);

        result[5] = (byte) ((times >> 16) & 0xFF);
        result[6] = (byte) ((times >> 8) & 0xFF);
        result[7] = (byte) (times & 0xFF);

        result[8] = (byte) 0x55;

        return result;
    }

    @Override
    protected void onDestroy() {
        if (mReadThread != null)
            mReadThread.interrupt();
        closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }
}
