package com.shengxiangui.cn.model;

import android.util.Log;

import com.shengxiangui.table.GreenDaoManager;
import com.shengxiangui.table.WuPinXinXiMoel;
import com.shengxiangui.table.WuPinXinXiMoelDao;
import com.shengxiangui.tool.Tools;

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
    public static List<HuoHaoAndZhongLiangModel> wuPinXinXi(GreenDaoManager greenDaoManager, int menDiZhi) {

        List<WuPinXinXiMoel> moels = greenDaoManager.mGoodsModelDao.loadAll();
        List<HuoHaoAndZhongLiangModel> mDatas = new ArrayList<>();
        for (int i = 0; i < moels.size(); i++) {
            if (menDiZhi == Integer.valueOf(moels.get(i).getMenDiZhi())) {
                HuoHaoAndZhongLiangModel huoHaoAndZhongLiangModel = new HuoHaoAndZhongLiangModel();
                huoHaoAndZhongLiangModel.cs_scale_number = moels.get(i).getJiaQianDiZhi();
                byte[] bytes = new byte[2];
                bytes[0] = Byte.parseByte(moels.get(i).getZhongLiang1());
                bytes[1] = Byte.parseByte(moels.get(i).getZhongLiang2());
                huoHaoAndZhongLiangModel.cs_wares_weight = String.valueOf(Tools.shortToInt(bytes[0], bytes[1]));
                mDatas.add(huoHaoAndZhongLiangModel);
            }
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


        //打印整合数据之前的数据库数据
        int zhongLiangZhuangTai = bytes[3];//重量状态
        String menDiZhi = String.valueOf(bytes[4]);//门地址


        List<WuPinXinXiMoel> wuPinXinXiMoels = new ArrayList<>();

        wuPinXinXiMoels = GreenDaoManager.mGoodsModelDao.loadAll();


        List<String> zhongLiangList = new ArrayList<>();
        for (int i = 5; i < bytes.length; i++) {
            if (i % 2 == 1) {
                String var1 = String.valueOf(bytes[i]);
                String var2 = String.valueOf(bytes[i + 1]);

                if (var1.length() == 1) {
                    var1 = "0" + var1;
                }

                if (var2.length() == 1) {
                    var2 = "0" + var2;
                }
                zhongLiangList.add(var1 + var2);
            }

        }


        for (int i = 0; i < wuPinXinXiMoels.size(); i++) {

            int menDiZhi_int = Integer.valueOf(wuPinXinXiMoels.get(i).getMenDiZhi());

            if (menDiZhi_int == Integer.valueOf(menDiZhi)) {

                for (int j = 0; j < zhongLiangList.size(); j++) {

                    String var1 = zhongLiangList.get(j).substring(0, 2);
                    String var2 = zhongLiangList.get(j).substring(2, 4);

                    wuPinXinXiMoels.get(i + j).setZhongLiang1(var1);
                    wuPinXinXiMoels.get(i + j).setZhongLiang2(var2);

                }
                break;
            }

        }


        //返回数据
        return wuPinXinXiMoels;
    }


    public static boolean zhongLiangBianHua(List<WuPinXinXiMoel> moelList1, List<WuPinXinXiMoel> moelList2) {
        boolean flag = true;
        for (int i = 0; i < moelList1.size(); i++) {
            if (!moelList1.get(i).getZhongLiang1().equals(moelList2.get(i))) {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
