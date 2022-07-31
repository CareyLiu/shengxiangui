package com.shengxiangui.mqtt;

import android.util.Log;

import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttPublish;
import com.shengxiangui.cn.model.ChengPanJiBenXinXi;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.util.List;

//服务器软件操作
public class MqttZhiLing {

    /**
     * 获得所有秤盘的重量
     *
     * @param topic
     */
    public void readAllZhongLiang(String topic) {

        String zhiLing = "M06";
        publish(topic, zhiLing);
    }


    /**
     * 查询某一个秤盘的重量
     *
     * @param topic
     * @param menBianHao      门编号
     * @param chengPanBianHao 秤盘编号
     * @param shangPinBianHao 商品编号
     */
    public void chaXunZhongLiang(String topic, String menBianHao, String chengPanBianHao, String shangPinBianHao) {

        String zhiLing = "M05" + menBianHao + chengPanBianHao + shangPinBianHao;
        publish(topic, zhiLing);
    }

    /**
     * 校准重量
     *
     * @param menBianHao         门编号
     * @param chengPanBianHao    秤盘编号
     * @param shangPinBianHao    商品编号
     * @param dangQianZhongLiang 当前重量
     * @param jiaoZhunZhongLiang 校准重量
     */
    public void jiaoZhun(String topic, String menBianHao, String chengPanBianHao, String shangPinBianHao,
                         String dangQianZhongLiang, String jiaoZhunZhongLiang) {

        String zhiLing = "M04" + menBianHao + chengPanBianHao + shangPinBianHao + dangQianZhongLiang + jiaoZhunZhongLiang;
        publish(topic, zhiLing);

    }

    /**
     * 清零功能
     *
     * @param menBianHao      门编号
     * @param chegnPanBianHao 秤盘编号
     * @param shangPinBianHao 商品编号
     */
    public void qingLing(String topic, String menBianHao, String chegnPanBianHao, String shangPinBianHao) {

        String zhiLing = "M03" + menBianHao + chegnPanBianHao + shangPinBianHao;
        publish(topic, zhiLing);
    }


    /**
     * 销售柜mqtt文档 上传价签名称等相关信息
     *
     * @param topic           主题
     * @param menBianHao      门编号
     * @param chengPanBianHao 秤盘编号
     * @param shangPinBianHao 商品编号
     * @param yuanJia         原价
     * @param xianJia         现价
     * @param hanZiMa         汉字码
     */
    public void shangChuanJiaQianDeng(String topic, String menBianHao, String chengPanBianHao, String shangPinBianHao
            , String yuanJia, String xianJia, String hanZiMa) {

        String zhiling = "M02" + menBianHao + chengPanBianHao + shangPinBianHao + yuanJia + xianJia + hanZiMa;
        publish(topic, zhiling);

    }

    public void kaiMen(String topic) {
        publish(topic, "M01");

    }

    public static void publish(String topic, String msg) {
        AndMqtt.getInstance().publish(new MqttPublish()
                .setMsg(msg)
                .setQos(2).setRetained(false)
                .setTopic(topic), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(CAR_NOTIFY.java:79)-onSuccess:-&gt;发布成功" + "k001 我是在类里面订阅的");

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:84)-onFailure:-&gt;发布失败");
            }
        });
    }

    public static void publish_msg(String topic, String msg, String push) {
        AndMqtt.getInstance().publish(new MqttPublish()
                .setMsg(msg)
                .setQos(2).setRetained(false)
                .setTopic(topic), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(CAR_NOTIFY.java:79)-onSuccess:-&gt;发布成功" + "k001 我是在类里面订阅的");

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:84)-onFailure:-&gt;发布失败");
            }
        });
    }


    //下面是主机相关命令

    /**
     * 关门
     *
     * @param topic
     */
    public void guanMen(String topic) {
        String zhiLing = "m01";
        publish(topic, zhiLing);
    }

    /**
     * 关门重量有大变化上传
     *
     * @param topic  主题
     * @param mDatas 秤盘信息
     */
    public void guanMenShangChuanZhongLiang(String topic, List<ChengPanJiBenXinXi> mDatas) {

        String zhiLing = "m02";


        for (int i = 0; i < mDatas.size(); i++) {
            ChengPanJiBenXinXi chengPanJiBenXinXi = mDatas.get(i);
            zhiLing = zhiLing + chengPanJiBenXinXi.menBianHao + chengPanJiBenXinXi.chengPanBianHao + chengPanJiBenXinXi.shangPinBianHao + chengPanJiBenXinXi.cunHuoZhongLiang;
            if (i == mDatas.size() - 1) {

            } else {
                zhiLing = zhiLing + "_";
            }
        }
        publish(topic, zhiLing);

    }


    /**
     * 没关门重量上传
     *
     * @param topic  主题
     * @param mDatas 秤盘信息
     */
    public void meiGuanMenZhongLiangBianHua(String topic, List<ChengPanJiBenXinXi> mDatas) {

        String zhiLing = "m03";


        for (int i = 0; i < mDatas.size(); i++) {
            ChengPanJiBenXinXi chengPanJiBenXinXi = mDatas.get(i);
            zhiLing = zhiLing + chengPanJiBenXinXi.menBianHao + chengPanJiBenXinXi.chengPanBianHao + chengPanJiBenXinXi.shangPinBianHao + chengPanJiBenXinXi.cunHuoZhongLiang;
            if (i == mDatas.size() - 1) {

            } else {
                zhiLing = zhiLing + "_";
            }
        }
        publish(topic, zhiLing);

    }

    /**
     * 缺货报警
     *
     * @param topic  主题
     * @param mDatas 秤盘信息
     */
    public void quHuoBaoJing(String topic, List<ChengPanJiBenXinXi> mDatas) {

        String zhiLing = "m04";


        for (int i = 0; i < mDatas.size(); i++) {
            ChengPanJiBenXinXi chengPanJiBenXinXi = mDatas.get(i);
            zhiLing = zhiLing + chengPanJiBenXinXi.menBianHao + chengPanJiBenXinXi.chengPanBianHao + chengPanJiBenXinXi.shangPinBianHao + chengPanJiBenXinXi.cunHuoZhongLiang;
            if (i == mDatas.size() - 1) {

            } else {
                zhiLing = zhiLing + "_";
            }
        }
        publish(topic, zhiLing);

    }


    /**
     * 报警解除
     *
     * @param topic  主题
     * @param mDatas 秤盘信息
     */
    public void baoJingJieChu(String topic, List<ChengPanJiBenXinXi> mDatas) {

        String zhiLing = "m05";


        for (int i = 0; i < mDatas.size(); i++) {
            ChengPanJiBenXinXi chengPanJiBenXinXi = mDatas.get(i);
            zhiLing = zhiLing + chengPanJiBenXinXi.menBianHao + chengPanJiBenXinXi.chengPanBianHao + chengPanJiBenXinXi.shangPinBianHao + chengPanJiBenXinXi.cunHuoZhongLiang;
            if (i == mDatas.size() - 1) {

            } else {
                zhiLing = zhiLing + "_";
            }
        }
        publish(topic, zhiLing);

    }


    /**
     * 销售柜mqtt文档 上传价签名称等相关信息
     *
     * @param topic            主题
     * @param menBianHao       门编号
     * @param chengPanBianHao  秤盘编号
     * @param shangPinBianHao  商品编号
     * @param cunHuoZhongLiang 存货重量
     */
    public void mouYiGeChengPanShangPin(String topic, String menBianHao, String chengPanBianHao, String shangPinBianHao
            , String cunHuoZhongLiang) {

        String zhiling = "M06" + menBianHao + chengPanBianHao + shangPinBianHao + cunHuoZhongLiang;
        publish(topic, zhiling);

    }

    /**
     * 所有秤盘的重量
     *
     * @param topic  主题
     * @param mDatas 秤盘信息
     */
    public void suoYouChengPanZhongLiang(String topic, List<ChengPanJiBenXinXi> mDatas) {

        String zhiLing = "m07";


        for (int i = 0; i < mDatas.size(); i++) {
            ChengPanJiBenXinXi chengPanJiBenXinXi = mDatas.get(i);
            zhiLing = zhiLing + chengPanJiBenXinXi.menBianHao + chengPanJiBenXinXi.chengPanBianHao + chengPanJiBenXinXi.shangPinBianHao + chengPanJiBenXinXi.cunHuoZhongLiang;
            if (i == mDatas.size() - 1) {

            } else {
                zhiLing = zhiLing + "_";
            }
        }
        publish(topic, zhiLing);

    }


    /**
     * 查询价签的相关信息
     *
     * @param topic
     */
    public void chaXunJiaQian(String topic) {
        String zhiling = "M08";
        publish(topic, zhiling);
    }


    /**
     * 设备实时数据,获得实时数据，每次接收数据时候都发送一次
     *
     * @param topic  主题
     * @param mDatas 秤盘信息
     */
    public void faSongShiShiShuJu(String topic, List<ChengPanJiBenXinXi> mDatas) {

        String zhiLing = "i$a";


        for (int i = 0; i < mDatas.size(); i++) {
            ChengPanJiBenXinXi chengPanJiBenXinXi = mDatas.get(i);
            zhiLing = zhiLing + chengPanJiBenXinXi.menBianHao + chengPanJiBenXinXi.chengPanBianHao + chengPanJiBenXinXi.shangPinBianHao + chengPanJiBenXinXi.cunHuoZhongLiang;
            if (i == mDatas.size() - 1) {
                zhiLing = zhiLing + "$.";
            } else {
                zhiLing = zhiLing + "_";
            }

        }
        publish(topic, zhiLing);

    }

    /**
     * 给硬件发送心跳
     *
     * @param topic
     */
    public static void faSongG(String topic) {
        String zhiling = "g.";
        publish(topic, zhiling);
    }


    /**
     * 请求码	r	1
     * 01	货柜	2
     * 01	货道（没有货道传aa）	2
     * 01	何种异常：01.交易异常 02.门异常	2
     * 01	0101 交易异常-柜门未关闭（6分钟未关闭，补货员是30分钟未关闭）
     * 0102 交易异常-柜门未关闭，且重量减少（记录后续使用）
     * 0201 门异常-异常开门（开门失败）
     * 0202 门异常-异常关门 (关门失败)	4
     *
     * @param topic       主题
     * @param huoGui      货柜
     * @param huoDao      货道
     * @param yiChang     异常
     * @param juTiYiChang 具体异常
     */
    public static void baoJing(String topic, String huoGui, String huoDao, String yiChang
            , String juTiYiChang) {
        String zhili = "r" + huoGui + huoDao + yiChang + juTiYiChang;
        publish(topic, zhili);
    }

    /**
     * 请求硬件基本信息
     *
     * @param topic
     * @param zhiLing m01.
     */
    public static void postYingJian(String topic, String zhiLing) {
        String zhili = zhiLing;
        publish(topic, zhili);
    }


}
