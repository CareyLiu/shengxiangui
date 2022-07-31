package com.shengxiangui.mqtt;

import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.model.OperateClass;

public class JieXiZhiLing {

    /**
     * if (leixing.equals("M01.")) {// 开门
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.KAIMEN;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M03")) {// 清零
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.QINGLING;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M04")) {// 校准
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.JIAOZHUN;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M05")) {// 查询/同步秤盘重量（单个）
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.CHAXUNDANGE;
     * RxBus.getDefault().sendRx(notice);
     * } else if (leixing.equals("M06")) {// 查询生鲜柜下所有秤盘重量
     * Notice notice = new Notice();
     * notice.type = ConstanceValue.CHAXUNSUOYOU;
     * RxBus.getDefault().sendRx(notice);
     * }
     *
     * @param notice
     */
    public static OperateClass.YingJianXinXiModel chuLiMqttMingLing(Notice notice) {
        OperateClass.YingJianXinXiModel yingJianXinXiModel = (OperateClass.YingJianXinXiModel) notice.content;
        if (notice.type == ConstanceValue.KAIMEN) {
            YingJianZhiLing.kaiGui(Integer.valueOf(yingJianXinXiModel.guiMenDiZhi), Integer.valueOf(yingJianXinXiModel.suoDiZhi));//开柜
            return yingJianXinXiModel;
        } else if (notice.type == ConstanceValue.QINGLING) {//清零功能
            YingJianZhiLing.xiaChuanQingLing(Integer.valueOf(yingJianXinXiModel.guiMenDiZhi), Integer.valueOf(yingJianXinXiModel.suoDiZhi), Integer.valueOf(yingJianXinXiModel.chengPanHao));//清零功能
            return yingJianXinXiModel;
        } else if (notice.type == ConstanceValue.JIAOZHUN) {
            YingJianZhiLing.xiaChuanJiaoZhun(yingJianXinXiModel.guiMenDiZhi, yingJianXinXiModel.suoDiZhi, yingJianXinXiModel.chengPanHao, yingJianXinXiModel.jiaoZhunZhongLiang);//校准功能
            return yingJianXinXiModel;
        } else if (notice.type == ConstanceValue.CHAXUNDANGE) {
            YingJianZhiLing.chaXunDanGe(yingJianXinXiModel.guiMenDiZhi, yingJianXinXiModel.suoDiZhi, yingJianXinXiModel.chengPanHao);
        } else if (notice.type == ConstanceValue.CHAXUNSUOYOU) {
            YingJianZhiLing.chaXunSuoYou(yingJianXinXiModel.guiMenDiZhi, yingJianXinXiModel.suoDiZhi);
        } else if (notice.type == ConstanceValue.YINGJIANJICHUXINXI) {
            YingJianZhiLing.yingJianXinXi(yingJianXinXiModel.guiMenDiZhi, yingJianXinXiModel.duShuDi, yingJianXinXiModel.duShuGao, yingJianXinXiModel.dengKaiGuanZhuangTai, yingJianXinXiModel.xiaoDuZhuangTai);
            return yingJianXinXiModel;
        }
        return yingJianXinXiModel;
    }


}
