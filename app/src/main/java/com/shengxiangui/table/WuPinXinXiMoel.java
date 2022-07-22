package com.shengxiangui.table;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WuPinXinXiMoel {

    @Id(autoincrement = true)
    private Long id;

    private String menDiZhi;//门地址
    private String jiaQianDiZhi;//价签地址
    private String zhongLiang1;//重量1
    private String zhongLiang2;//重量2

    private String shangPinZhongWenBianMa;//商品名称，中文编码
    private String shangPinMingCheng;//商品名称
    private String shouJia;//售价
    private String huiYuanJia;//会员价

    @Generated(hash = 8964145)
    public WuPinXinXiMoel(Long id, String menDiZhi, String jiaQianDiZhi, String zhongLiang1,
            String zhongLiang2, String shangPinZhongWenBianMa, String shangPinMingCheng,
            String shouJia, String huiYuanJia) {
        this.id = id;
        this.menDiZhi = menDiZhi;
        this.jiaQianDiZhi = jiaQianDiZhi;
        this.zhongLiang1 = zhongLiang1;
        this.zhongLiang2 = zhongLiang2;
        this.shangPinZhongWenBianMa = shangPinZhongWenBianMa;
        this.shangPinMingCheng = shangPinMingCheng;
        this.shouJia = shouJia;
        this.huiYuanJia = huiYuanJia;
    }

    @Generated(hash = 53331691)
    public WuPinXinXiMoel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenDiZhi() {
        return this.menDiZhi;
    }

    public void setMenDiZhi(String menDiZhi) {
        this.menDiZhi = menDiZhi;
    }

    public String getJiaQianDiZhi() {
        return this.jiaQianDiZhi;
    }

    public void setJiaQianDiZhi(String jiaQianDiZhi) {
        this.jiaQianDiZhi = jiaQianDiZhi;
    }

    public String getZhongLiang1() {
        return this.zhongLiang1;
    }

    public void setZhongLiang1(String zhongLiang1) {
        this.zhongLiang1 = zhongLiang1;
    }

    public String getZhongLiang2() {
        return this.zhongLiang2;
    }

    public void setZhongLiang2(String zhongLiang2) {
        this.zhongLiang2 = zhongLiang2;
    }

    public String getShangPinMingCheng() {
        return this.shangPinMingCheng;
    }

    public void setShangPinMingCheng(String shangPinMingCheng) {
        this.shangPinMingCheng = shangPinMingCheng;
    }

    public String getShouJia() {
        return this.shouJia;
    }

    public void setShouJia(String shouJia) {
        this.shouJia = shouJia;
    }

    public String getHuiYuanJia() {
        return this.huiYuanJia;
    }

    public void setHuiYuanJia(String huiYuanJia) {
        this.huiYuanJia = huiYuanJia;
    }

    public String getShangPinZhongWenBianMa() {
        return this.shangPinZhongWenBianMa;
    }

    public void setShangPinZhongWenBianMa(String shangPinZhongWenBianMa) {
        this.shangPinZhongWenBianMa = shangPinZhongWenBianMa;
    }


}
