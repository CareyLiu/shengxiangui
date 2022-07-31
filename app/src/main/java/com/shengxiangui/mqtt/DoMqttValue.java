package com.shengxiangui.mqtt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttSubscribe;
import com.shengxiangui.cn.ChuanKouCaoZuoUtils;
import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.RxBus;
import com.shengxiangui.cn.model.OperateClass;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.ErrorListener;


public class DoMqttValue {

    public Context context;//上下文
    public String message;//消息


    List<String> stringList;

    public DoMqttValue(Context context) {
        this.context = context;
        if (AndMqtt.getInstance() == null) {
            return;
        }
        if (!AndMqtt.getInstance().isConnect()) {
            return;
        }
        AndMqtt.getInstance().subscribe(new MqttSubscribe().setQos(2).setTopic(Addr.ccidAddr), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("rair", "订阅成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    public void doValue(Context context, String topic, String message) {

        Log.i("rair", message);

        if (message.charAt(0) == 'i') {

            message = "i010111_010221.";

            String message1 = message.substring(1, message.length() - 1);

            String[] data = message1.split("_");


        } else if (message.charAt(0) == 'k') {

        } else if (message.charAt(0) == 'M') {

            String leixing = message.substring(0, 3);
            List<String> list = new ArrayList<>();
            if (leixing.equals("M01")) {// 开门

                String guiMenHao = message.substring(3, 5);//柜门号
                String suoHao = message.substring(5, 7);//锁号
                String renYuanZhuangTai = message.substring(7, 8);//人员状态

                OperateClass.YingJianXinXiModel yingJianXinXiModel = new OperateClass.YingJianXinXiModel();

                yingJianXinXiModel.menZhuangTai = "1";
                yingJianXinXiModel.suoDiZhi = Integer.parseInt(suoHao);
                yingJianXinXiModel.renYuanZhuagnTai = renYuanZhuangTai;
                yingJianXinXiModel.mqtt_zhiling = ConstanceValue.KAIMEN;

                OperateClass.gengXinJiBenXinXi(guiMenHao, suoHao, renYuanZhuangTai, "1", null);


                Notice notice = new Notice();
                notice.type = ConstanceValue.KAIMEN;
                notice.content = yingJianXinXiModel;
                RxBus.getDefault().sendRx(notice);


            } else if (leixing.equals("M03")) {// 清零

                /**
                 * M03010202.
                 * M03:命令码 清零
                 * 01:  1号柜门
                 * 02:  2号锁
                 * 02:  2号秤盘
                 * 表示：1号柜门的2号锁的二号秤盘清零操作
                 */
                String guiMenHao = message.substring(3, 5);
                String suoHao = message.substring(5, 7);
                String chengPanBianHao = message.substring(7, 9);

                OperateClass.YingJianXinXiModel yingJianXinXiModel = new OperateClass.YingJianXinXiModel();

                yingJianXinXiModel.suoDiZhi = Integer.parseInt(suoHao);
                yingJianXinXiModel.guiMenDiZhi = Integer.parseInt(guiMenHao);
                yingJianXinXiModel.chengPanHao = Integer.parseInt(chengPanBianHao);
                yingJianXinXiModel.mqtt_zhiling = ConstanceValue.QINGLING;

                yingJianXinXiModel.renYuanZhuagnTai = OperateClass.getYingJianXinXi(guiMenHao, suoHao).renYuanZhuagnTai;
                yingJianXinXiModel.huiYuanKaHao = OperateClass.getYingJianXinXi(guiMenHao, suoHao).huiYuanKaHao;
                yingJianXinXiModel.menZhuangTai = OperateClass.getYingJianXinXi(guiMenHao, suoHao).menZhuangTai;


                Notice notice = new Notice();
                notice.type = ConstanceValue.QINGLING;
                notice.content = yingJianXinXiModel;
                RxBus.getDefault().sendRx(notice);


            } else if (leixing.equals("M04")) {// 校准

                // TODO: 2022-07-11 待讨论
                /**
                 * M0401010102001000. 校准
                 * M04:命令码
                 * 01:门编号
                 * 02:秤盘编号
                 * 01020:当前重量
                 * 01000:校准重量
                 */
                OperateClass.YingJianXinXiModel yingJianXinXiModel = new OperateClass.YingJianXinXiModel();

                String guiHao = message.substring(3, 5);
                String suoHao = message.substring(5, 7);
                String chengPanBianHao = message.substring(7, 9);
                String dangQianZhongLiang = message.substring(9, 14);
                String jiaoZhunZhongLiang = message.substring(14, 19);

                yingJianXinXiModel.guiMenDiZhi = Integer.parseInt(guiHao);
                yingJianXinXiModel.suoDiZhi = Integer.parseInt(suoHao);
                yingJianXinXiModel.chengPanHao = Integer.parseInt(chengPanBianHao);
                yingJianXinXiModel.dangQianZhongLiang = Integer.parseInt(dangQianZhongLiang);
                yingJianXinXiModel.jiaoZhunZhongLiang = Integer.parseInt(jiaoZhunZhongLiang);
                yingJianXinXiModel.mqtt_zhiling = ConstanceValue.JIAOZHUN;

                yingJianXinXiModel.renYuanZhuagnTai = OperateClass.getYingJianXinXi(guiHao, suoHao).renYuanZhuagnTai;
                yingJianXinXiModel.huiYuanKaHao = OperateClass.getYingJianXinXi(guiHao, suoHao).huiYuanKaHao;
                yingJianXinXiModel.menZhuangTai = OperateClass.getYingJianXinXi(guiHao, suoHao).menZhuangTai;


                Notice notice = new Notice();
                notice.type = ConstanceValue.JIAOZHUN;
                notice.content = yingJianXinXiModel;
                RxBus.getDefault().sendRx(notice);

            } else if (leixing.equals("M05")) {// 查询/同步秤盘重量（单个）
                /**
                 * M050101. 查询/同步秤盘重量
                 * M05:命令码
                 * 01:门编号
                 * 02:秤盘编号
                 */

                OperateClass.YingJianXinXiModel yingJianXinXiModel = new OperateClass.YingJianXinXiModel();
                String guiHao = message.substring(3, 5);
                String suoHao = message.substring(5, 7);
                String chengPanHao = message.substring(7, 9);

                yingJianXinXiModel.guiMenDiZhi = Integer.parseInt(guiHao);
                yingJianXinXiModel.suoDiZhi = Integer.parseInt(suoHao);
                yingJianXinXiModel.chengPanHao = Integer.parseInt(chengPanHao);
                yingJianXinXiModel.mqtt_zhiling = ConstanceValue.CHAXUNDANGE;

                yingJianXinXiModel.renYuanZhuagnTai = OperateClass.getYingJianXinXi(guiHao, suoHao).renYuanZhuagnTai;
                yingJianXinXiModel.huiYuanKaHao = OperateClass.getYingJianXinXi(guiHao, suoHao).huiYuanKaHao;
                yingJianXinXiModel.menZhuangTai = OperateClass.getYingJianXinXi(guiHao, suoHao).menZhuangTai;




                Notice notice = new Notice();
                notice.type = ConstanceValue.CHAXUNDANGE;
                notice.content = yingJianXinXiModel;
                RxBus.getDefault().sendRx(notice);

            } else if (leixing.equals("M06")) {// 查询生鲜柜下所有秤盘重量

                OperateClass.YingJianXinXiModel yingJianXinXiModel = new OperateClass.YingJianXinXiModel();
                String guiHao = message.substring(3, 5);
                String suoHao = message.substring(5, 7);

                yingJianXinXiModel.guiMenDiZhi = Integer.parseInt(guiHao);
                yingJianXinXiModel.suoDiZhi = Integer.parseInt(suoHao);
                yingJianXinXiModel.mqtt_zhiling = ConstanceValue.CHAXUNSUOYOU;


                yingJianXinXiModel.renYuanZhuagnTai = OperateClass.getYingJianXinXi(guiHao, suoHao).renYuanZhuagnTai;
                yingJianXinXiModel.huiYuanKaHao = OperateClass.getYingJianXinXi(guiHao, suoHao).huiYuanKaHao;
                yingJianXinXiModel.menZhuangTai = OperateClass.getYingJianXinXi(guiHao, suoHao).menZhuangTai;



                Notice notice = new Notice();
                notice.type = ConstanceValue.CHAXUNSUOYOU;
                notice.content = yingJianXinXiModel;
                RxBus.getDefault().sendRx(notice);


            } else if (leixing.equals("M07")) {
                Notice notice = new Notice();
                notice.type = ConstanceValue.GENGXINJIAQIAN;
                list.add(String.valueOf(ConstanceValue.GENGXINJIAQIAN));
                notice.content = list;
                RxBus.getDefault().sendRx(notice);
            } else if (leixing.equals("M08")) {

                OperateClass.YingJianXinXiModel yingJianXinXiModel = new OperateClass.YingJianXinXiModel();

                //  M0823250101.
                //（3位）M08 ：命令码M08
                //（2位）02:  02号柜门
                //（2位）23：23度（最低温度）
                //（2位）25：25度（最高温度）
                //（2位）01：开（01开 02关）
                //（2位）01：消毒（01消毒 02不消毒）
                Notice notice = new Notice();
                notice.type = ConstanceValue.YINGJIANJICHUXINXI;
                String guiMenHao = message.substring(3, 5);
                String duShu_di = message.substring(5, 7);//度数
                String duShu_gao = message.substring(7, 9);//度数
                String dengKaiGuan = message.substring(9, 11);
                String xiaoDuQingKuang = message.substring(11, 13);

                yingJianXinXiModel.guiMenDiZhi = Integer.parseInt(guiMenHao);
                yingJianXinXiModel.duShuDi = Integer.parseInt(duShu_di);
                yingJianXinXiModel.duShuGao = Integer.parseInt(duShu_gao);
                yingJianXinXiModel.dengKaiGuanZhuangTai = Integer.parseInt(dengKaiGuan);
                yingJianXinXiModel.xiaoDuZhuangTai = Integer.parseInt(xiaoDuQingKuang);
                yingJianXinXiModel.mqtt_zhiling = ConstanceValue.CHAXUNSUOYOU;


//                yingJianXinXiModel.renYuanZhuagnTai = OperateClass.getYingJianXinXi(guiMenHao, suoHao).renYuanZhuagnTai;
//                yingJianXinXiModel.huiYuanKaHao = OperateClass.getYingJianXinXi(guiMenHao, suoHao).huiYuanKaHao;
//                yingJianXinXiModel.menZhuangTai = OperateClass.getYingJianXinXi(guiMenHao, suoHao).menZhuangTai;
                notice.content = yingJianXinXiModel;


                RxBus.getDefault().sendRx(notice);
            }
        } else if (message.charAt(0) == 'r') {
            /**
             * 参数	说明
             * 请求码	r
             * 柜门	0.正常1.报警
             * 货品需要补充	0.正常1.报警提示
             * 货品放错	0.正常1.报警提示
             * 结束符号	点.
             */
            String guimen = message.substring(1, 2);
            String buHuo = message.substring(2, 3);
            String cuoHuo = message.substring(3, 4);
        }
    }
}
