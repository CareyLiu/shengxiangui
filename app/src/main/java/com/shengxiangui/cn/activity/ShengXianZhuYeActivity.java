package com.shengxiangui.cn.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.MyApp;
import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.R;
import com.shengxiangui.cn.RxBus;
import com.shengxiangui.cn.Urls;
import com.shengxiangui.cn.adapter.MyFragmentPagerAdapter;
import com.shengxiangui.cn.base.BaseActivity;
import com.shengxiangui.cn.config.AppResponse;
import com.shengxiangui.cn.config.callback.JsonCallback;
import com.shengxiangui.cn.fragment.ShangPinFragment;
import com.shengxiangui.cn.model.ChuLiShuJuClass;
import com.shengxiangui.cn.model.HuoHaoAndZhongLiangModel;
import com.shengxiangui.cn.model.OperateClass;
import com.shengxiangui.cn.model.ShangPinLieBiaoModel;
import com.shengxiangui.mqtt.JieXiZhiLing;
import com.shengxiangui.mqtt.YingJianZhiLing;
import com.shengxiangui.table.WuPinXinXiMoel;
import com.shengxiangui.tool.StreamUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shengxiangui.cn.MyApp.greenDaoManager;
import static com.shengxiangui.cn.Urls.SHENGXIANGUI;
import static com.shengxiangui.cn.Urls.SHENGXIANGUI_SHANGPIN;
import static com.shengxiangui.cn.config.JiBenXinXi.device_ccid;
import static com.shengxiangui.cn.model.OperateClass.getOperateType;
import static com.shengxiangui.cn.model.OperateClass.renYuanZhuagnTai;

public class ShengXianZhuYeActivity extends BaseActivity {

    @BindView(R.id.iv_bajiaoye)
    ImageView ivBajiaoye;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    public static final String TAG = ShengXianZhuYeActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shengxianzhuye);
        viewPager = findViewById(R.id.viewPager);

        if (YingJianZhiLing.daKaiSheBei(ShengXianZhuYeActivity.this)) {
            new ReadThread().start();
        }
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice notice) {

                List<String> mDats = JieXiZhiLing.chuLiMqttMingLing(ShengXianZhuYeActivity.this, notice);
            }
        }));
        jieShouYingJianXinXi();
        ButterKnife.bind(this);
        //获取布局
        //1.根据数量创建fragment
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        getNet(device_ccid);
        timeHandler = new TimeHandler();
    }

    /**
     * @param device_ccid               设备id
     * @param cs_door_numbaer           门id
     * @param operate_type              1.用户：安卓屏开门响应 2.用户：开门时重量上传 3.用户：关门时重量上传
     *                                  4.补货员：安卓屏开门响应 5.补货员：关门时重量上传 6.补货员：同步某个货道重量
     * @param sf_type                   1.开门成功 2.开门失败（operate_type为1，3，4，5时）
     * @param huoHaoAndZhongLiangModels [{"cs_scale_number":"02","cs_wares_weight":"1510"}]
     *                                  cs_scale_number 货道号 cs_wares_weight 货道重量
     */
    private void getNet(String device_ccid, String cs_door_numbaer, String operate_type,
                        String sf_type, List<HuoHaoAndZhongLiangModel> huoHaoAndZhongLiangModels) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "100054");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);
        map.put("cs_door_number", cs_door_numbaer);
        map.put("line_type", renYuanZhuagnTai);// 连接类型：1.用户 2.补货员
        map.put("operate_type", operate_type);
        map.put("sf_type", sf_type);
        map.put("where", huoHaoAndZhongLiangModels);

        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<HuoHaoAndZhongLiangModel>>post(SHENGXIANGUI)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<HuoHaoAndZhongLiangModel>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<HuoHaoAndZhongLiangModel>> response) {

                    }
                });
    }

    private void jieShouYingJianXinXi() {
        handler = new Handler() {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg) {
                // 截取返回值中的体温数据
                byte[] bytes = (byte[]) msg.obj;
                byte zlm = bytes[3];
                switch (zlm) {

                    case 1://柜门 开门
                        int menDiZhi_KaiMen = bytes[4];//门地址
                        int kaiGuanZhuangTai = bytes[5];

                        if (kaiGuanZhuangTai == 1) {//开
                            //开门获得人员状态


                            Notice notice = new Notice();
                            notice.type = ConstanceValue.KAIMEN;

                            RxBus.getDefault().sendRx(notice);

                            getNet("ccid", String.valueOf(menDiZhi_KaiMen), getOperateType(), "1", null);

                        } else if (kaiGuanZhuangTai == 2) {//关

                            List<HuoHaoAndZhongLiangModel> huoHaoAndZhongLiangModels = ChuLiShuJuClass.wuPinXinXi(greenDaoManager);
                            /**
                             *      * @param operate_type              1.用户：安卓屏开门响应 2.用户：开门时重量上传 3.用户：关门时重量上传
                             *      *                                  4.补货员：安卓屏开门响应 5.补货员：关门时重量上传 6.补货员：同步某个货道重量
                             *      * @param sf_type                   1.开门成功 2.开门失败（operate_type为1，3，4，5时）
                             */
                            getNet("ccid", String.valueOf(menDiZhi_KaiMen), getOperateType(), "1", huoHaoAndZhongLiangModels);
                        }
                        break;
                    case 2://上传的电子价签信息
                        int state = bytes[4];//重量状态
                        int menDiZhi_DianZiJiaQian = bytes[4];//门地址
                        int jiaQianDiZhi = bytes[5];//价签地址
                        int zhongLiang1 = bytes[6];//重量1
                        int zhongLiang2 = bytes[7];//重量2
                        List<WuPinXinXiMoel> moels = greenDaoManager.mGoodsModelDao.loadAll();
                        List<WuPinXinXiMoel> moels1 = new ArrayList<>();
                        if (state == 0) {
                            if (zhongLiangBianHua(moels, moels1)) {
                                //如果完全一样什么都不做
                            } else {
                                //不一样 上传数据给后台
                                getNet("ccid", String.valueOf(menDiZhi_DianZiJiaQian), OperateClass.getKaiMenHeRenYuanZhuangTai(), "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager));
                                greenDaoManager.insertAllData(moels);
                            }
                        } else if (state == 1) {
                            getNet("ccid", String.valueOf(menDiZhi_DianZiJiaQian), "7", "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager));
                        } else if (state == 2) {
                            getNet("ccid", String.valueOf(menDiZhi_DianZiJiaQian), "8", "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager));
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public boolean zhongLiangBianHua(List<WuPinXinXiMoel> moelList1, List<WuPinXinXiMoel> moelList2) {
        if (moelList1.containsAll(moelList2) && moelList2.containsAll(moelList1)) {
            return true;
        } else {
            return false;
        }
    }

    private int i = 0;

    public class TimeHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.obj.equals("aa")) {
                Log.i(TAG, "handlermessage " + i + "lunboshuliang: " + lunBoShuLiang);
                viewPager.setCurrentItem(i, true);
                if (i == lunBoShuLiang - 1) {
                    i = 0;
                } else {
                    i = i + 1;
                }
            }
        }
    }

    TimeHandler timeHandler;
    private boolean flag = true;

    public class DingShiThread extends Thread {
        @Override
        public void run() {
            super.run();

            while (flag) {

                Message message = new Message();
                message.what = 1;
                message.obj = "aa";
                //   timeHandler.sendEmptyMessage(1);
                timeHandler.handleMessage(message);
                Log.i(TAG, "Thread发送");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isOpen;
    private Handler handler;

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

    List<ShangPinLieBiaoModel.DataBean> mDatas = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();
    private int lunBoShuLiang = 0;//轮播数量

    private void getNet(String device_ccid) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "100055");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);
        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<ShangPinLieBiaoModel.DataBean>>post(SHENGXIANGUI_SHANGPIN)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangPinLieBiaoModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangPinLieBiaoModel.DataBean>> response) {
                        mDatas = response.body().data;
                        List<List<ShangPinLieBiaoModel.DataBean>> lists = StreamUtil.splitList(mDatas, 10);
                        lunBoShuLiang = lists.size();

                        for (int i = 0; i < lists.size(); i++) {

                            ShangPinFragment shangPinFragment = new ShangPinFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("shuju", (Serializable) lists.get(i));
                            shangPinFragment.setArguments(bundle);

                            fragmentList.add(shangPinFragment);
                            myFragmentPagerAdapter.notifyDataSetChanged();
                        }

                        thread = new DingShiThread();
                        thread.start();

                    }

                    @Override
                    public void onError(Response<AppResponse<ShangPinLieBiaoModel.DataBean>> response) {
                        super.onError(response);


                    }
                });
    }

    Thread thread;

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;
        thread.stop();
        thread = null;
    }
}
