package com.shengxiangui.mqtt;

import android.content.Context;
import android.widget.Toast;

import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttSubscribe;
import com.shengxiangui.cn.ChuanKouCaoZuoUtils;
import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.RxBus;

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
                Toast.makeText(context, "订阅成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    public void doValue(Context context, String topic, String message) {


        if (message.charAt(0) == 'i') {

            message = "i010111_010221.";

            String message1 = message.substring(1, message.length() - 1);

            String[] data = message1.split("_");


        } else if (message.charAt(0) == 'k') {

        } else if (message.charAt(0) == 'M') {

            String leixing = message.substring(0, 3);
            List<String> list = new ArrayList<>();
            if (leixing.equals("M01")) {// 开门

                String guiMenHao = message.substring(3, 5);
                String renYuanZhuangTai = message.substring(5, 6);


                list.add(String.valueOf(ConstanceValue.KAIMEN));
                list.add(guiMenHao);
                list.add(renYuanZhuangTai);


                Notice notice = new Notice();
                notice.type = ConstanceValue.KAIMEN;
                notice.content = list;
                RxBus.getDefault().sendRx(notice);


            } else if (leixing.equals("M03")) {// 清零

                String menBianHao = message.substring(3, 5);
                String chengPanBianHao = message.substring(5, 7);

                Notice notice = new Notice();
                notice.type = ConstanceValue.QINGLING;

                list.add(String.valueOf(ConstanceValue.QINGLING));
                list.add(menBianHao);
                list.add(chengPanBianHao);

                notice.content = list;
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


                String menBianHao = message.substring(3, 5);
                String chengPanBianHao = message.substring(5, 7);
                String dangQianZhongLiang = message.substring(7, 12);
                String jiaoZhunZhongLiang = message.substring(12, 17);


                list.add(String.valueOf(ConstanceValue.JIAOZHUN));
                list.add(menBianHao);
                list.add(chengPanBianHao);
                list.add(dangQianZhongLiang);
                list.add(jiaoZhunZhongLiang);


                Notice notice = new Notice();
                notice.type = ConstanceValue.JIAOZHUN;
                notice.content = list;
                RxBus.getDefault().sendRx(notice);

            } else if (leixing.equals("M05")) {// 查询/同步秤盘重量（单个）


                /**
                 * M050101. 查询/同步秤盘重量
                 * M05:命令码
                 * 01:门编号
                 * 02:秤盘编号
                 */
                String menBianHao = message.substring(3, 5);
                String chengPanBianHao = message.substring(5, 7);

                Notice notice = new Notice();
                notice.type = ConstanceValue.CHAXUNDANGE;

                list.add(String.valueOf(ConstanceValue.CHAXUNDANGE));
                list.add(menBianHao);
                list.add(chengPanBianHao);
                notice.content = list;
                RxBus.getDefault().sendRx(notice);

            } else if (leixing.equals("M06")) {// 查询生鲜柜下所有秤盘重量

                Notice notice = new Notice();
                notice.type = ConstanceValue.CHAXUNSUOYOU;
                notice.content = list.add(String.valueOf(ConstanceValue.CHAXUNSUOYOU));
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
