package com.shengxiangui.cn.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttPublish;
import com.sample.Video;
import com.sample.Video_BaseFragment;
import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.MyApp;
import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.R;
import com.shengxiangui.cn.Urls;
import com.shengxiangui.cn.adapter.MyFragmentPagerAdapter;
import com.shengxiangui.cn.base.BaseActivity;
import com.shengxiangui.cn.config.AppResponse;
import com.shengxiangui.cn.config.callback.JsonCallback;
import com.shengxiangui.cn.fragment.ShangPinFragment;
import com.shengxiangui.cn.model.ChuLiShuJuClass;
import com.shengxiangui.cn.model.HuoHaoAndZhongLiangModel;
import com.shengxiangui.cn.model.ShangPinJieKouModel;
import com.shengxiangui.cn.model.ShangPinLieBiaoModel;
import com.shengxiangui.mqtt.Addr;
import com.shengxiangui.mqtt.JieXiZhiLing;
import com.shengxiangui.mqtt.YingJianZhiLing;
import com.shengxiangui.table.GreenDaoManager;
import com.shengxiangui.table.WuPinXinXiMoel;
import com.shengxiangui.tool.ShuJuMoNiUtils;
import com.shengxiangui.tool.StreamUtil;
import com.shengxiangui.tool.Tools;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shengxiangui.cn.MyApp.greenDaoManager;
import static com.shengxiangui.cn.Urls.SHENGXIANGUI;
import static com.shengxiangui.cn.Urls.SHENGXIANGUI_SHANGPIN;
import static com.shengxiangui.cn.config.JiBenXinXi.device_ccid;
import static com.shengxiangui.cn.model.ChuLiShuJuClass.zhongLiangBianHua;
import static com.shengxiangui.cn.model.OperateClass.getKaiMenHeRenYuanZhuangTai;
import static com.shengxiangui.cn.model.OperateClass.getOperateType;
import static com.shengxiangui.cn.model.OperateClass.menDiZhi;
import static com.shengxiangui.cn.model.OperateClass.menZhuangTai;
import static com.shengxiangui.cn.model.OperateClass.renYuanZhuagnTai;

public class ShengXianZhuYeActivity extends BaseActivity {

    @BindView(R.id.iv_bajiaoye)
    ImageView ivBajiaoye;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    public static final String TAG = ShengXianZhuYeActivity.class.getSimpleName();
    @BindView(R.id.btn_monikaimen)
    Button btnMonikaimen;
    @BindView(R.id.btn_moniqingling)
    Button btnMoniqingling;
    @BindView(R.id.btn_monijiaozhun)
    Button btnMonijiaozhun;
    @BindView(R.id.btn_huowu_zhengchang)
    Button btnHuowuZhengchang;
    @BindView(R.id.btn_huowu_dange)
    Button btnHuowuDange;
    @BindView(R.id.btn_huowu_suoyou)
    Button btnHuowuSuoyou;

    ChuLiYingJianShujuHandler chuLiYingJianShujuHandler;
    @BindView(R.id.btn_charu)
    Button btnCharu;
    @BindView(R.id.btn_chaxun)
    Button btnChaxun;
    @BindView(R.id.btn_butong)
    Button btnButong;

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
                if (Integer.valueOf(mDats.get(0)) == ConstanceValue.CHAXUNSUOYOU) {
                    getNet(device_ccid, menDiZhi, getKaiMenHeRenYuanZhuangTai(), "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, Integer.parseInt(menDiZhi)));
                } else if (Integer.valueOf(mDats.get(0)) == ConstanceValue.GENGXINJIAQIAN) {
                    getNetJiaQian();
                }
            }
        }));

        ButterKnife.bind(this);
        //1.根据数量创建fragment
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        getNet(device_ccid);
        timeHandler = new TimeHandler();
        chuLiYingJianShujuHandler = new ChuLiYingJianShujuHandler();

        // TODO: 2022-07-17 用过删除
        new ReadThread().start();
        getNetJiaQian();

//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.containerView, Video_BaseFragment.newInstance(Video.ORANGE_13.url))
//                .commit();

    }

    private int k = 0;//循环遍历插入数据

    private void getNetJiaQian() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "100057");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);

        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<ShangPinJieKouModel.DataBean>>post(SHENGXIANGUI)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangPinJieKouModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangPinJieKouModel.DataBean>> response) {

                        /**
                         * msg_code	返回码
                         * msg	应答描述
                         * data	应答数据
                         * cs_door_number	货柜号  -- 门地址
                         * cs_scale_number	货道号  --价签地址
                         * cs_wares_name	商品名称中文编码
                         * selling_price	售价
                         * membership_price	会员价
                         */

                        List<WuPinXinXiMoel> list = new ArrayList<>();

                        while (k < response.body().data.size()) {
                            ShangPinJieKouModel.DataBean shangPinJieKouModel = response.body().data.get(i);
                            WuPinXinXiMoel wuPinXinXiMoel = new WuPinXinXiMoel();

                            wuPinXinXiMoel.setMenDiZhi(shangPinJieKouModel.getCs_door_number());//门地址
                            wuPinXinXiMoel.setJiaQianDiZhi(shangPinJieKouModel.getCs_scale_number());//价签地址
                            wuPinXinXiMoel.setShangPinZhongWenBianMa(shangPinJieKouModel.getCs_wares_name());//中文编码
                            wuPinXinXiMoel.setShouJia(shangPinJieKouModel.getSelling_price());//商品售价
                            wuPinXinXiMoel.setHuiYuanJia(shangPinJieKouModel.getMembership_price());//商品会员价
                            wuPinXinXiMoel.setShangPinMingCheng(shangPinJieKouModel.getCs_wares_name());
                            wuPinXinXiMoel.setId((long) i);


                            int zhongLiang = Integer.parseInt(shangPinJieKouModel.getCs_wares_weight());

                            byte[] bytes = Tools.intToShort(zhongLiang);//把重量转换成两个字节型

                            wuPinXinXiMoel.setZhongLiang1(String.valueOf(bytes[0]));
                            wuPinXinXiMoel.setZhongLiang2(String.valueOf(bytes[1]));


                            list.add(wuPinXinXiMoel);

                            try {
                                YingJianZhiLing.xiaChuanDianZiJiaQian(wuPinXinXiMoel);
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            k = k + 1;
                            Log.i("CHAKSHUJU", "执行到了第几个数据：" + k + " 时间:" + System.currentTimeMillis());
                            GreenDaoManager.mGoodsModelDao.update(wuPinXinXiMoel);
                        }
                    }
                });
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
        map.put("cs_door_number", String.format("%02d", Integer.valueOf(cs_door_numbaer)));
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

    public class ChuLiYingJianShujuHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // 截取返回值中的体温数据
            byte[] bytes = (byte[]) msg.obj;
            byte zlm = bytes[2];

            Log.i("moni", "进入handler");

            switch ((int) zlm) {

                case 1://柜门 开门
                    int menDiZhi_KaiMen = bytes[3];//门地址
                    int kaiGuanZhuangTai = bytes[4];//开关状态
                    if (kaiGuanZhuangTai == 1) {//开
                        menDiZhi = String.valueOf(menDiZhi_KaiMen);
                        menZhuangTai = "1";
                        Log.i("moni_handler", "开门地址:" + menDiZhi_KaiMen + "人员状态地址：" + kaiGuanZhuangTai);
                        List<HuoHaoAndZhongLiangModel> huoHaoAndZhongLiangModels = ChuLiShuJuClass.wuPinXinXi(greenDaoManager, menDiZhi_KaiMen);
                        getNet(device_ccid, String.valueOf(menDiZhi_KaiMen), getOperateType(), "1", huoHaoAndZhongLiangModels);
                    } else if (kaiGuanZhuangTai == 2) {//关
                        menZhuangTai = "2";

                        Log.i("moni_handler", "开门地址:" + menDiZhi_KaiMen + "人员状态地址：" + kaiGuanZhuangTai + "关门");
                        getNet(device_ccid, String.valueOf(menDiZhi_KaiMen), getOperateType(), "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, menDiZhi_KaiMen));
                    }
                    break;
                case 3://上传的电子价签信息
                    int state = bytes[3];//重量状态
                    int menDiZhi_DianZiJiaQian = bytes[4];//门地址

                    List<WuPinXinXiMoel> moels = greenDaoManager.mGoodsModelDao.loadAll();
                    Log.i(TAG, "重量之前" + moels.get(17).getZhongLiang1());

                    List<WuPinXinXiMoel> moels1 = ChuLiShuJuClass.getMenHaoShuJu(bytes);
                    Log.i(TAG, "重量之后" + moels.get(17).getZhongLiang1());
                    if (state == 0) {
                        //正常重量上传需要
                        if (zhongLiangBianHua(moels, moels1)) {
                            //如果完全一样什么都不做
                        } else {
                            //不一样 上传数据给后台
                            if (renYuanZhuagnTai.equals("1")) {
                                getNet(device_ccid, String.valueOf(menDiZhi_DianZiJiaQian), getKaiMenHeRenYuanZhuangTai(), "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, menDiZhi_DianZiJiaQian));
                            }
                            greenDaoManager.insertAllData(moels1);
                        }
                    } else if (state == 1) {
                        getNet(device_ccid, String.valueOf(menDiZhi_DianZiJiaQian), "6", "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, menDiZhi_DianZiJiaQian));
                    } else if (state == 2) {
                        getNet(device_ccid, String.valueOf(menDiZhi_DianZiJiaQian), "6", "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, menDiZhi_DianZiJiaQian));
                    }
                    break;

                case 4://指令码，请求柜门配置表
                    getPeiZhiBiaoNet();
                    break;

                case 5://指令码，请求电子价签信息


                    break;

                case 6://指令码，上传全部重量

                    break;

                default:

                    break;
            }
        }

    }

    //请求柜门配置表
    private void getPeiZhiBiaoNet() {

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

                        // TODO: 2022-07-25 配置表接口
                        List<String> list = new ArrayList<>();
                        YingJianZhiLing.xiaChuanPeiZhiBiao(1, list);
                    }

                    @Override
                    public void onError(Response<AppResponse<ShangPinLieBiaoModel.DataBean>> response) {
                        super.onError(response);

                    }
                });

    }

    private int i = 0;//几秒轮播 用于轮播页面

    @OnClick({R.id.btn_monikaimen, R.id.btn_moniqingling, R.id.btn_monijiaozhun,
            R.id.btn_huowu_zhengchang, R.id.btn_huowu_dange, R.id.btn_huowu_suoyou,
            R.id.btn_guanmen, R.id.btn_charu, R.id.btn_chaxun,
            R.id.btn_butong, R.id.btn_monifasong, R.id.btn_moni_buhuoyuan_kaimen, R.id.btn_moni_buhuoyuan_buhuo, R.id.btn_moni_buhuoyuan_guanmen
            , R.id.btn_moni_buhuoyuan_qingling, R.id.btn_moni_buhuoyuan_jiaozhun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_moni_buhuoyuan_kaimen:
                //补货员开门
                break;
            case R.id.btn_moni_buhuoyuan_buhuo:
                //补货员补货
                break;
            case R.id.btn_moni_buhuoyuan_guanmen:
                //补货员关门
                break;
            case R.id.btn_moni_buhuoyuan_qingling:
                //补货员清零
                break;
            case R.id.btn_moni_buhuoyuan_jiaozhun:
                //补货员校准
                break;
            case R.id.btn_monifasong:

                String str = "12345678".replaceAll("(.{2})", ":$1").substring(1);

                String[] arr = str.split(":");

                for (int i = 0; i < arr.length; i++) {
                    Log.i("YingJianZhiLing", arr[i]);
                }

                break;
            case R.id.btn_butong:
                ShuJuMoNiUtils.moniHuowu_butong("0");
                List<WuPinXinXiMoel> moels1 = ChuLiShuJuClass.getMenHaoShuJu(buffer_moni);

                break;
            case R.id.btn_charu:
                getNetJiaQian();
                break;
            case R.id.btn_chaxun:
                //插入数据后 查看数据是否插入成功
                List<WuPinXinXiMoel> wuPinXinXiMoelList = GreenDaoManager.mGoodsModelDao.loadAll();
                for (int i = 0; i < wuPinXinXiMoelList.size(); i++) {
                    WuPinXinXiMoel wuPinXinXiMoel = wuPinXinXiMoelList.get(i);

                    Log.i(TAG, "中文编码" + wuPinXinXiMoel.getShangPinZhongWenBianMa());
                }

                break;
            case R.id.btn_guanmen:
                ShuJuMoNiUtils.moniguanmen();
                break;
            case R.id.btn_monikaimen:
                ShuJuMoNiUtils.monikaimen();
                break;

            case R.id.btn_monijiaozhun:
                break;
            case R.id.btn_huowu_zhengchang:
                ShuJuMoNiUtils.moniHuowu("0");
                break;
            case R.id.btn_huowu_dange:
                ShuJuMoNiUtils.moniHuowu("1");
                break;
            case R.id.btn_huowu_suoyou:
                ShuJuMoNiUtils.moniHuowu("2");
                break;
        }
    }

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
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                message.obj = "aa";
                //   timeHandler.sendEmptyMessage(1);
                timeHandler.handleMessage(message);
                Log.i(TAG, "Thread发送");


            }
        }
    }

    private boolean isOpen = true;
    private Handler handler;

    // TODO: 2022-07-17 模拟数据
    public static byte[] buffer_moni = new byte[1];//模拟数据

    public class ReadThread extends Thread {
        public void run() {
            super.run();
            byte[] buffer = new byte[4096];
            while (isOpen) {

                Log.i("moni_handler", "线程" +
                        "开启" + "buffer_moni长度: " + buffer_moni.length);
                int length = MyApp.driver.ReadData(buffer, 4096);
                //  buffer = buffer_moni;

                if (buffer_moni.length > 5) {
                    //  String recv = toHexString(buffer, length);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = buffer_moni;
                    chuLiYingJianShujuHandler.sendMessage(msg);
                    buffer_moni = new byte[1];
                }


                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
