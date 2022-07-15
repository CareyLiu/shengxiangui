package com.shengxiangui.table;

import android.content.Context;

import com.shengxiangui.cn.MyApp;

import java.util.List;

public class GreenDaoManager {
    private Context mContext;
    public WuPinXinXiMoelDao mGoodsModelDao;

    public GreenDaoManager(Context context) {
        this.mContext = context;
        // 获取DAO实例
        mGoodsModelDao = MyApp.getDaoSession().getWuPinXinXiMoelDao();
    }

    // 插入数据
    public void insertGoods() {
        WuPinXinXiMoel wuPinXinXiMoel = new WuPinXinXiMoel();
        wuPinXinXiMoel.setId(Long.valueOf("1"));
        wuPinXinXiMoel.setJiaQianDiZhi("");
        wuPinXinXiMoel.setMenDiZhi("");
        wuPinXinXiMoel.setZhongLiang1("");
        wuPinXinXiMoel.setZhongLiang2("");


        mGoodsModelDao.insert(wuPinXinXiMoel);


    }

    /**
     * private String menDiZhi;//门地址
     * private String jiaQianDiZhi;//价签地址
     * private String zhongLiang1;//重量1
     * private String zhongLiang2;//重量2
     *
     * @param menDiZhi
     */
    public void insertData(int menDiZhi, int jiaQianDiZhi, int zhongLiang1, int zhongLiang2) {

        //获取对象DAO


        //获取到所有实体类，并在内存中先处理好数据
        List<WuPinXinXiMoel> wuPinXinXiMoelList = mGoodsModelDao.loadAll();
        for (int i = 0; i < wuPinXinXiMoelList.size(); i++) {
            WuPinXinXiMoel wuPinXinXiMoel = wuPinXinXiMoelList.get(i);
            if (wuPinXinXiMoel.getMenDiZhi().equals(menDiZhi + "") && wuPinXinXiMoel.getJiaQianDiZhi().equals(jiaQianDiZhi + "")) {
                wuPinXinXiMoel.setZhongLiang1(zhongLiang1 + "");
                wuPinXinXiMoel.setZhongLiang2(zhongLiang2 + "");
                return;
            }

        }

    }


    /**
     * private String menDiZhi;//门地址
     * private String jiaQianDiZhi;//价签地址
     * private String zhongLiang1;//重量1
     * private String zhongLiang2;//重量2
     *
     * @param menDiZhi
     */
    public void insertAllData(List<WuPinXinXiMoel> moelList) {
        mGoodsModelDao.updateInTx(moelList);

    }


}
