package com.shengxiangui.cn;

public interface ConstanceValue {

    /**
     * 后台服务器发来的命令
     **/
    int ERWEIMA = 0x10000;//通过mqtt获取后台的二维码连接地址
    int KAIMEN = 0x10001;//开门
    int QINGLING = 0x10002;//清零
    int JIAOZHUN = 0x10003;//校准
    int CHAXUNDANGE = 0x10004;//查询单个
    int CHAXUNSUOYOU = 0x10005;//查询所有

    /**
     * 硬件发过来的数据
     **/
    int GUIMENZHUANGTAI = 0x10006;//柜门状态
    int SHANGCHUANJIAQIANXINXI = 0x10007;//上传柜门重量
    int GUANMEN = 0x10008;//关门

    /**
     * 模拟开门
     */

    int MONIKAIMEN = 0x10009;//模拟开门
    int MONIGUANMEN = 0x100010;//模拟关门
    int MONIQINGLING = 0x100011;//模拟清零
    int MONIJIAOZHUN = 0x100012;//模拟校准
    int MONITONGBUCHENGPAN = 0x100013;//模拟同步单个秤盘
    int MONITONGBUSUOYOUCHENGPAN = 0x100014;//模拟同步所有秤盘

    int GENGXINJIAQIAN = 0x100015;//更新价签
}
