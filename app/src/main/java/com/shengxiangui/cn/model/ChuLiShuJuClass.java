package com.shengxiangui.cn.model;

import com.shengxiangui.table.GreenDaoManager;
import com.shengxiangui.table.WuPinXinXiMoel;
import com.shengxiangui.table.WuPinXinXiMoelDao;

import java.util.ArrayList;
import java.util.List;

import static com.shengxiangui.cn.MyApp.greenDaoManager;

//整合数据的类
public class ChuLiShuJuClass {
    public static List<HuoHaoAndZhongLiangModel> wuPinXinXi(GreenDaoManager greenDaoManager) {

        List<WuPinXinXiMoel> moels = greenDaoManager.mGoodsModelDao.loadAll();
        List<HuoHaoAndZhongLiangModel> mDatas = new ArrayList<>();
        for (int i = 0; i < moels.size(); i++) {

            HuoHaoAndZhongLiangModel huoHaoAndZhongLiangModel = new HuoHaoAndZhongLiangModel();
            huoHaoAndZhongLiangModel.cs_scale_number = moels.get(i).getJiaQianDiZhi();
            huoHaoAndZhongLiangModel.cs_wares_weight = moels.get(i).getZhongLiang1() + "." + moels.get(i).getZhongLiang2();
            mDatas.add(huoHaoAndZhongLiangModel);
        }
        return mDatas;
    }
}
