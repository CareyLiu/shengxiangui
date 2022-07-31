package com.shengxiangui.cn.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
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
import com.shengxiangui.cn.model.GouMaiWuPinModel;
import com.shengxiangui.cn.model.HuoHaoAndZhongLiangModel;
import com.shengxiangui.cn.model.OperateClass;
import com.shengxiangui.cn.model.PeiZhiBiaoModel;
import com.shengxiangui.cn.model.ShangPinJieKouModel;
import com.shengxiangui.cn.model.ShangPinLieBiaoModel;
import com.shengxiangui.mqtt.Addr;
import com.shengxiangui.mqtt.JieXiZhiLing;
import com.shengxiangui.mqtt.MqttZhiLing;
import com.shengxiangui.mqtt.YingJianZhiLing;
import com.shengxiangui.table.GreenDaoManager;
import com.shengxiangui.table.WuPinXinXiMoel;
import com.shengxiangui.tool.GPS;
import com.shengxiangui.tool.ShuJuMoNiUtils;
import com.shengxiangui.tool.StreamUtil;
import com.shengxiangui.tool.Tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shengxiangui.cn.MyApp.greenDaoManager;
import static com.shengxiangui.cn.Urls.SHENGXIANGUI;
import static com.shengxiangui.cn.Urls.SHENGXIANGUI_SHANGPIN;
import static com.shengxiangui.cn.config.JiBenXinXi.device_ccid;
import static com.shengxiangui.cn.model.ChuLiShuJuClass.zhongLiangBianHua;
import static com.shengxiangui.cn.model.OperateClass.getKaiMenHeRenYuanZhuangTai;
import static com.shengxiangui.tool.ShuJuMoNiUtils.moniqingling;

public class ShengXianZhuYeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

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
    @BindView(R.id.iv_saomakaimen)
    ImageView ivSaomakaimen;
    @BindView(R.id.btn_monifasong)
    Button btnMonifasong;
    @BindView(R.id.btn_guanmen)
    Button btnGuanmen;
    @BindView(R.id.btn_moni_buhuoyuan_kaimen)
    Button btnMoniBuhuoyuanKaimen;
    @BindView(R.id.btn_moni_buhuoyuan_buhuo)
    Button btnMoniBuhuoyuanBuhuo;
    @BindView(R.id.btn_moni_buhuoyuan_guanmen)
    Button btnMoniBuhuoyuanGuanmen;
    @BindView(R.id.btn_moni_buhuoyuan_qingling)
    Button btnMoniBuhuoyuanQingling;
    @BindView(R.id.btn_moni_buhuoyuan_jiaozhun)
    Button btnMoniBuhuoyuanJiaozhun;
    @BindView(R.id.tv_zhangdanjiesuan)
    TextView tvZhangdanjiesuan;
    @BindView(R.id.containerView)
    LinearLayout containerView;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    List<GouMaiWuPinModel.DataBean.DataListBean> gouWuList;
    @BindView(R.id.tv_zongjine)
    TextView tvZongjine;
    @BindView(R.id.btn_huiyuanka_kaimen)
    Button btnHuiyuankaKaimen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shengxianzhuye);
        viewPager = findViewById(R.id.viewPager);

        String[] perms = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS};
        EasyPermissions.requestPermissions(ShengXianZhuYeActivity.this, "申请开启app需要的权限", 0, perms);
        if (YingJianZhiLing.daKaiSheBei(ShengXianZhuYeActivity.this)) {
            new ReadThread().start();
        }
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice notice) {

                OperateClass.YingJianXinXiModel yingJianXinXiModel = JieXiZhiLing.chuLiMqttMingLing(notice);
                if (yingJianXinXiModel.mqtt_zhiling == ConstanceValue.CHAXUNSUOYOU) {

                    /**
                     *     /**
                     *      * @param device_ccid               设备ccid
                     *      * @param guiHao                    柜号
                     *      * @param suoHao                    锁号
                     *      * @param operate_type              操作类型
                     *      * @param sf_type                   传1成功
                     *      * @param huoHaoAndZhongLiangModels 传递数据
                     *      */

                    getNet(device_ccid, yingJianXinXiModel.guiMenDiZhi + "", yingJianXinXiModel.suoDiZhi + "",
                            OperateClass.getOperateType(yingJianXinXiModel.guiMenDiZhi + "", yingJianXinXiModel.suoDiZhi + ""), "1",
                            ChuLiShuJuClass.wuPinXinXi(greenDaoManager, yingJianXinXiModel.guiMenDiZhi, yingJianXinXiModel.suoDiZhi
                            ));
                } else if (yingJianXinXiModel.mqtt_zhiling == ConstanceValue.GENGXINJIAQIAN) {
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

        new ReadThread().start();
        getPeiZhiBiaoNet();
        getNetJiaQian();
        mDatas = new ArrayList<>();
        initAdapter(rlvList, gouWuList);

        //        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.containerView, Video_BaseFragment.newInstance(Video.ORANGE_13.url))
//                .commit();
        Tools.getShouJiKaHao(ShengXianZhuYeActivity.this);
        GPS gps = new GPS(this);
        gps.startLocation();
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
                            // TODO: 2022-07-31 柜地址
                            wuPinXinXiMoel.setGuiDiZhi(shangPinJieKouModel.guiHao);//柜地址
                            wuPinXinXiMoel.setId((long) i);

                            int zhongLiang = Integer.parseInt(shangPinJieKouModel.getCs_wares_weight());

                            byte[] bytes = Tools.intToShort(zhongLiang);//把重量转换成两个字节型

                            //
                            wuPinXinXiMoel.setZhongLiang1(String.valueOf(bytes[0]));
                            wuPinXinXiMoel.setZhongLiang2(String.valueOf(bytes[1]));

                            list.add(wuPinXinXiMoel);

                            Log.i(TAG, wuPinXinXiMoel.getShouJia());
                            Log.i(TAG, wuPinXinXiMoel.getHuiYuanJia());
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
     * @param device_ccid               设备ccid
     * @param guiHao                    柜号
     * @param suoHao                    锁号
     * @param operate_type              操作类型
     * @param sf_type                   传1成功
     * @param huoHaoAndZhongLiangModels 传递数据
     */

    private void getNet(String device_ccid, String guiHao, String suoHao, String operate_type,
                        String sf_type, List<HuoHaoAndZhongLiangModel> huoHaoAndZhongLiangModels) {
        Map<String, Object> map = new HashMap<>();

        OperateClass.YingJianXinXiModel yingJianXinXiModel = OperateClass.getYingJianXinXi(guiHao, suoHao);
        map.put("code", "100054");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);
        map.put("cs_door_number", String.format("%02d", yingJianXinXiModel.guiMenDiZhi));
        map.put("line_type", yingJianXinXiModel.renYuanZhuagnTai);// 连接类型：1.用户 2.补货员
        map.put("operate_type", operate_type);
        map.put("sf_type", sf_type);
        map.put("cs_card_number", yingJianXinXiModel.huiYuanKaHao);
        map.put("where", huoHaoAndZhongLiangModels);
        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<GouMaiWuPinModel.DataBean>>post(SHENGXIANGUI)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<GouMaiWuPinModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<GouMaiWuPinModel.DataBean>> response) {

                        String str = OperateClass.getKaiMenHeRenYuanZhuangTai(String.valueOf(yingJianXinXiModel.guiMenDiZhi), String.valueOf(yingJianXinXiModel.suoDiZhi));

                        if (str.equals("2") || str.equals("10")) {
                            gouWuList = response.body().data.get(0).getData_list();
                            containerView.setVisibility(View.VISIBLE);
                            if (jieSuanListAdapter != null) {
                                jieSuanListAdapter.notifyDataSetChanged();
                            }
                            tvZongjine.setText(response.body().data.get(0).getAll_money());
                        }


                    }
                });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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

                case 1://打开柜门

                    /**
                     * 	内容	字节长度	说明
                     * ZLM	1	1	指令码，柜门状态
                     * DATA	2	1	地址，2号柜
                     * 	1	1	地址，1号锁
                     * 	1	1	1开状态，2关状态
                     */
                    String guiHao = String.valueOf(bytes[3]);//柜门地址
                    String suoHao = String.valueOf(bytes[4]);//锁地址
                    String kaiGuanZhuangTai = String.valueOf(bytes[5]);

                    OperateClass.YingJianXinXiModel yingJianXinXiModel = OperateClass.gengXinJiBenXinXi(guiHao, suoHao, null, kaiGuanZhuangTai, null);


                    if (kaiGuanZhuangTai.equals("1")) {//开
                        List<HuoHaoAndZhongLiangModel> huoHaoAndZhongLiangModels = ChuLiShuJuClass.wuPinXinXi(greenDaoManager, Integer.parseInt(guiHao), Integer.parseInt(suoHao));

                        /**
                         *
                         * @param device_ccid 设备ccid
                         * @param guiHao 柜号
                         * @param suoHao 锁号
                         * @param operate_type 操作类型
                         * @param sf_type 传1成功
                         * @param huoHaoAndZhongLiangModels 传递数据
                         */

                        OperateClass.gengXinJiBenXinXi(guiHao, suoHao, null, "1", null);
                        getNet(device_ccid, guiHao, suoHao, OperateClass.getOperateType(guiHao, suoHao), "1", huoHaoAndZhongLiangModels);


                    } else if (kaiGuanZhuangTai.equals("2")) {//关

                        List<HuoHaoAndZhongLiangModel> huoHaoAndZhongLiangModels = ChuLiShuJuClass.wuPinXinXi(greenDaoManager, Integer.parseInt(guiHao), Integer.parseInt(suoHao));
                        OperateClass.gengXinJiBenXinXi(guiHao, suoHao, null, "2", null);
                        getNet(device_ccid, guiHao, suoHao, OperateClass.getOperateType(guiHao, suoHao), "1", huoHaoAndZhongLiangModels);

                        //关门后 隐藏结算页面
                        containerView.setVisibility(View.GONE);

                    }
                    break;
                case 3://上传的电子价签信息
                    String guiDiZhi = String.valueOf(bytes[3]);//柜地址
                    String suoDiZhi = String.valueOf(bytes[4]);//锁地址

                    OperateClass.YingJianXinXiModel yingJianXinXiModel1 = OperateClass.gengXinJiBenXinXi(guiDiZhi, suoDiZhi, null, "2", null);


                    //获得数据库基本信息
                    List<WuPinXinXiMoel> moels = greenDaoManager.mGoodsModelDao.loadAll();
                    //解析获得现在的基本信息
                    List<WuPinXinXiMoel> moels1 = ChuLiShuJuClass.getMenHaoShuJu(bytes);


                    //正常重量上传需要
                    if (!zhongLiangBianHua(moels, moels1)) {
                        //不一样 上传数据给后台
                        if (yingJianXinXiModel1.renYuanZhuagnTai.equals("1")) {//用户状态下有变动传值
                            /**
                             *     /**
                             *      * @param device_ccid               设备ccid
                             *      * @param guiHao                    柜号
                             *      * @param suoHao                    锁号
                             *      * @param operate_type              操作类型
                             *      * @param sf_type                   传1成功
                             *      * @param huoHaoAndZhongLiangModels 传递数据
                             *      */

                            getNet(device_ccid, guiDiZhi, suoDiZhi, getKaiMenHeRenYuanZhuangTai(guiDiZhi, suoDiZhi), "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, Integer.parseInt(guiDiZhi), Integer.parseInt(suoDiZhi)));

                        }
                        greenDaoManager.insertAllData(moels1);
                    }

                    break;

                case 4://指令码，请求柜门配置表
                    getPeiZhiBiaoNet();
                    break;

                case 5://指令码，请求电子价签信息
                    getNetJiaQian();
                    break;

                case 6://指令码，上传全部重量

                    break;

                case 7://请求温度 消毒 开灯
                    MqttZhiLing.postYingJian(Addr.ccidAddr, "m01.");
                    break;

                default:

                    break;
            }
        }

    }

    //请求柜门配置表
    private void getPeiZhiBiaoNet() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "100058");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);
        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<PeiZhiBiaoModel.DataBean>>post(SHENGXIANGUI_SHANGPIN)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<PeiZhiBiaoModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<PeiZhiBiaoModel.DataBean>> response) {

                        // TODO: 2022-07-25 配置表接口
                        List<String> list = new ArrayList<>();

                        int list_length = Integer.parseInt(response.body().data.get(0).getDoor_count());
                        for (int i = 0; i < response.body().data.get(0).getDoor_list().size(); i++) {
                            list.add(response.body().data.get(0).getDoor_list().get(i).getCs_door_number());
                        }
                        //YingJianZhiLing.xiaChuanPeiZhiBiao(list_length, list);
                    }

                    @Override
                    public void onError(Response<AppResponse<PeiZhiBiaoModel.DataBean>> response) {
                        super.onError(response);

                    }
                });

    }

    private int i = 0;//几秒轮播 用于轮播页面

    @OnClick({R.id.btn_monikaimen, R.id.btn_moniqingling, R.id.btn_monijiaozhun,
            R.id.btn_huowu_zhengchang, R.id.btn_huowu_dange, R.id.btn_huowu_suoyou,
            R.id.btn_guanmen, R.id.btn_charu, R.id.btn_chaxun,
            R.id.btn_butong, R.id.btn_monifasong, R.id.btn_moni_buhuoyuan_kaimen, R.id.btn_moni_buhuoyuan_buhuo, R.id.btn_moni_buhuoyuan_guanmen
            , R.id.btn_moni_buhuoyuan_qingling, R.id.btn_moni_buhuoyuan_jiaozhun, R.id.btn_huiyuanka_kaimen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_huiyuanka_kaimen:
                // getNet(device_ccid, "02", "7", "1", ChuLiShuJuClass.wuPinXinXi(greenDaoManager, 2));
                break;
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
                moniqingling();
                //补货员清零
                break;
            case R.id.btn_moni_buhuoyuan_jiaozhun:
                //补货员校准
                break;
            case R.id.btn_monifasong:
                GPS gps = new GPS(this);
                gps.startLocation();


                Tools.getShouJiKaHao(ShengXianZhuYeActivity.this);
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
    private int num = 0;

    private int j = 0;//开门时间过于长的时候用于校验

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
                Log.i(TAG, "num数值:" + num);

                if (num % 6 == 0) {
                    MqttZhiLing.faSongG(Addr.ccidAddr);
                    num = 0;
                }
                num = num + 1;


                //关于异常处理
//                if (menZhuangTai.equals("1")) {
//                    if (renYuanZhuagnTai.equals("1") || renYuanZhuagnTai.equals("3")) {
//                        if (j > 12) {
//                            j = 0;
//                            MqttZhiLing.baoJing(Addr.ccidAddr, menDiZhi, "aa", "02", "0101");
//                        } else {
//                            j = j + 1;
//                        }
//                    } else if (renYuanZhuagnTai.equals("2")) {
//
//                        if (j > 360) {
//                            j = 0;
//                            MqttZhiLing.baoJing(Addr.ccidAddr, menDiZhi, "aa", "02", "0101");
//                        } else {
//                            j = j + 1;
//                        }
//                    }
//                }


            }
        }
    }

    private boolean isOpen = true;

    // TODO: 2022-07-17 模拟数据
    public static byte[] buffer_moni = new byte[1];//模拟数据

    public class ReadThread extends Thread {
        public void run() {
            super.run();
            byte[] buffer = new byte[4096];
            while (isOpen) {

                int length = MyApp.driver.ReadData(buffer, 4096);

                if (buffer_moni.length > 5) {
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
        if (thread != null) {
            flag = false;
            thread.interrupt();
            thread = null;
        }
    }

}
