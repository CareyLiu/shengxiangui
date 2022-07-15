package com.shengxiangui.cn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.danikula.videocache.BuildConfig;
import com.danikula.videocache.HttpProxyCacheServer;
import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttConnect;
import com.sample.Utils;
import com.shengxiangui.mqtt.Addr;
import com.shengxiangui.mqtt.DoMqttValue;
import com.shengxiangui.table.DaoMaster;
import com.shengxiangui.table.DaoSession;
import com.shengxiangui.table.GreenDaoManager;
import com.shengxiangui.table.WuPinXinXiMoel;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;


public class MyApp extends Application {
    private HttpProxyCacheServer proxy;
    DoMqttValue doMqttValue;

    public static CH34xUARTDriver driver;// 需要将CH34x的驱动类写在APP类下面，使得帮助类的生命周期与整个应用程序的生命周期是相同的
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";
    public static Context context;


    public HashMap<Integer, Integer> hashMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG, "[VideoCache]:");
        doMqttValue = new DoMqttValue(getApplicationContext());
        setMqttConnect();

        context = this;
        MyApp.driver = new CH34xUARTDriver(
                (UsbManager) getSystemService(Context.USB_SERVICE), this,
                ACTION_USB_PERMISSION);
        chuShiHuaShuJuKu();//初始化数据库

    }

    public static DaoSession mSession;
    public static GreenDaoManager greenDaoManager;

    private void chuShiHuaShuJuKu() {

        // 1、获取需要连接的数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        // 2、创建数据库连接
        DaoMaster daoMaster = new DaoMaster(db);
        // 3、创建数据库会话
        mSession = daoMaster.newSession();

        greenDaoManager = new GreenDaoManager(this);


    }

    // 供外接使用
    public static DaoSession getDaoSession() {
        return mSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        MyApp app = (MyApp) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(Utils.getVideoCacheDir(this))
                .build();
    }

    private String UserId = "icabinet" + Addr.ccidAddr + System.currentTimeMillis();

    public void setMqttConnect() {


        if (!TextUtils.isEmpty(UserId)) {  //登录再订阅mqtt  如果获取不到 userId 证明没有登录 所有需要优化一下
            if (isAppProcess()) {
                AndMqtt.getInstance().init(this);
                MqttConnect builder = new MqttConnect();
                builder.setClientId(UserId)//连接服务器
                        .setPort(9096)
//                        .setPort(9093)
                        .setAutoReconnect(true)
                        .setCleanSession(true)
                        .setKeepAlive(60)
                        .setCleanSession(true)
                        .setLastWill("K.", "wit/server/01/" + UserId, 2, true)
                        .setUserName("witandroid")
                        .setUserPassword("aG678om34buysdi")
                        .setServer(getMqttUrl()).setTimeout(1);

                builder.setMessageListener(new MqttCallbackExtended() {
                    @Override
                    public void connectComplete(boolean reconnect, String serverURI) {
                        Log.i("Rair", "(MainActivity.java:29)-connectComplete:-&gt;连接完成");
                    }

                    @Override
                    public void connectionLost(Throwable cause) {
                        Log.i("Rair", "(MainActivity.java:34)-connectionLost:-&gt;连接丢失" + cause.getMessage().toString());
                        //UIHelper.ToastMessage(context, "网络不稳定持续连接中", Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {

                        doMqttValue.doValue(getApplicationContext(), topic, message.toString());
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        try {
                            Log.i("Rair", "消息送达完成： " + token.getMessage().toString());
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                    }
                });

                AndMqtt.getInstance().connect(builder, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("Rair", "(MainActivity.java:51)-onSuccess:-&gt;连接成功");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i("Rair", "(MainActivity.java:56)-onFailure:-&gt;连接失败" + exception.getMessage() + "..." + exception.getLocalizedMessage());
                        //System.out.println("exception.getMessage" + exception.getMessage());
                    }
                });
            }
        }
    }

    public String getMqttUrl() {
        if (Urls.SERVER_URL.equals("https://shop.hljsdkj.com/")) {
            return "tcp://mqtt.hljsdkj.com";
        } else {
            return "tcp://mqrn.hljsdkj.com";
        }

    }

    public void sendRx(int strValue, String strContent) {
        Notice n = new Notice();
        n.type = strValue;
//                            n.content = message.toString();
        n.content = strContent;
        RxBus.getDefault().sendRx(n);
    }


    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) info = new PackageInfo();
        return info;
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (TextUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }


    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    /**
     * 判断该进程是否是app进程
     *
     * @return
     */


    public boolean isAppProcess() {
        JinChengUtils jinChengUtils = new JinChengUtils(getApplicationContext());

        String processName = jinChengUtils.getProcessName();
        if (processName == null || !processName.equalsIgnoreCase(this.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }


}
