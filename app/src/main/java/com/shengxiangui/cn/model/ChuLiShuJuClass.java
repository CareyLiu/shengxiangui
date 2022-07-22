package com.shengxiangui.cn.model;

import android.util.Log;

import com.shengxiangui.table.GreenDaoManager;
import com.shengxiangui.table.WuPinXinXiMoel;
import com.shengxiangui.table.WuPinXinXiMoelDao;

import java.util.ArrayList;
import java.util.List;

import static com.shengxiangui.cn.MyApp.greenDaoManager;

//整合数据的类
public class ChuLiShuJuClass {

    public final String TAG = ChuLiShuJuClass.class.getSimpleName();

    /**
     * 从数据库里拿出数据进行整合，上传重量实时数据状态给后台
     *
     * @param greenDaoManager
     * @return
     */
    public static List<HuoHaoAndZhongLiangModel> wuPinXinXi(GreenDaoManager greenDaoManager) {

        List<WuPinXinXiMoel> moels = greenDaoManager.mGoodsModelDao.loadAll();
        List<HuoHaoAndZhongLiangModel> mDatas = new ArrayList<>();
        for (int i = 0; i < moels.size(); i++) {

            HuoHaoAndZhongLiangModel huoHaoAndZhongLiangModel = new HuoHaoAndZhongLiangModel();
            huoHaoAndZhongLiangModel.cs_scale_number = moels.get(i).getJiaQianDiZhi();
            huoHaoAndZhongLiangModel.cs_wares_weight = moels.get(i).getZhongLiang1();
            mDatas.add(huoHaoAndZhongLiangModel);
        }
        return mDatas;
    }


    /**
     * 把byte型数组装载整合数据到list中
     *
     * @param bytes 传入byte型数组
     * @return
     */
    public static List<WuPinXinXiMoel> getMenHaoShuJu(byte[] bytes) {


        int j = 0;//价签地址累加
        //打印整合数据之前的数据库数据
        int zhongLiangZhuangTai = bytes[3];//重量状态
        String menDiZhi = String.valueOf(bytes[4]);//门地址


        List<WuPinXinXiMoel> wuPinXinXiMoels = GreenDaoManager.mGoodsModelDao.loadAll();

        for (int i = 0; i < bytes.length-5; i++) {

            WuPinXinXiMoel wuPinXinXiMoel = wuPinXinXiMoels.get(i);

            if (Integer.valueOf(menDiZhi) == Integer.valueOf(wuPinXinXiMoel.getMenDiZhi())) {

                wuPinXinXiMoel.setJiaQianDiZhi(j + "");
                wuPinXinXiMoel.setZhongLiang1(bytes[i + 5] + "");
                j = j + 1;

            }
            /**
             *     private Long id;
             *
             *     private String menDiZhi;//门地址
             *     private String jiaQianDiZhi;//价签地址
             *     private String zhongLiang1;//重量1
             *     private String zhongLiang2;//重量2
             *
             *     private String shangPinZhongWenBianMa;//商品名称，中文编码
             *     private String shangPinMingCheng;//商品名称
             *     private String shouJia;//售价
             *     private String huiYuanJia;//会员价
             */

            Log.i("ChuLiShuJuClass_正常数据", "字段id: " + wuPinXinXiMoel.getId());
            Log.i("ChuLiShuJuClass_正常数据", "门地址: " + wuPinXinXiMoel.getMenDiZhi());
            Log.i("ChuLiShuJuClass_正常数据", "价签地址: " + wuPinXinXiMoel.getJiaQianDiZhi());
            Log.i("ChuLiShuJuClass_正常数据", "重量1: " + wuPinXinXiMoel.getZhongLiang1());
            Log.i("ChuLiShuJuClass_正常数据", "重量2: " + wuPinXinXiMoel.getZhongLiang2());
            Log.i("ChuLiShuJuClass_正常数据", "中文编码: " + wuPinXinXiMoel.getShangPinZhongWenBianMa());
            Log.i("ChuLiShuJuClass_正常数据", "商品名称: " + wuPinXinXiMoel.getShangPinMingCheng());
            Log.i("ChuLiShuJuClass_正常数据", "售价: " + wuPinXinXiMoel.getShouJia());
            Log.i("ChuLiShuJuClass_正常数据", "会员价: " + wuPinXinXiMoel.getHuiYuanJia());
        }

        List<WuPinXinXiMoel> wuPinXinXiMoelList = new ArrayList<>();


        //插入数据
        GreenDaoManager.mGoodsModelDao.updateInTx(wuPinXinXiMoelList);



        List<WuPinXinXiMoel> wuPinXinXiMoels1 = GreenDaoManager.mGoodsModelDao.loadAll();
        for (int i = 0; i < wuPinXinXiMoels1.size(); i++) {

            WuPinXinXiMoel wuPinXinXiMoel = wuPinXinXiMoels1.get(i);

            Log.i("ChuLiShuJuClass_更改之后的数据", "字段id:" + wuPinXinXiMoel.getId());
            Log.i("ChuLiShuJuClass_更改之后的数据", "门地址:" + wuPinXinXiMoel.getMenDiZhi());
            Log.i("ChuLiShuJuClass_更改之后的数据", "价签地址:" + wuPinXinXiMoel.getJiaQianDiZhi());
            Log.i("ChuLiShuJuClass_更改之后的数据", "重量1:" + wuPinXinXiMoel.getZhongLiang1());
            Log.i("ChuLiShuJuClass_更改之后的数据", "重量2:" + wuPinXinXiMoel.getZhongLiang2());
            Log.i("ChuLiShuJuClass_更改之后的数据", "中文编码:" + wuPinXinXiMoel.getShangPinZhongWenBianMa());
            Log.i("ChuLiShuJuClass_更改之后的数据", "商品名称:" + wuPinXinXiMoel.getShangPinMingCheng());
            Log.i("ChuLiShuJuClass_更改之后的数据", "售价:" + wuPinXinXiMoel.getShouJia());
            Log.i("ChuLiShuJuClass_更改之后的数据", "会员价:" + wuPinXinXiMoel.getHuiYuanJia());
        }
        //返回数据
        return GreenDaoManager.mGoodsModelDao.loadAll();
    }


    public static boolean zhongLiangBianHua(List<WuPinXinXiMoel> moelList1, List<WuPinXinXiMoel> moelList2) {
        if (moelList1.containsAll(moelList2) && moelList2.containsAll(moelList1)) {
            return true;
        } else {
            return false;
        }
    }

}
