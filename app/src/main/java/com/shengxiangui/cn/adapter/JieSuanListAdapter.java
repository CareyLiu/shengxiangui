package com.shengxiangui.cn.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengxiangui.cn.R;
import com.shengxiangui.cn.model.GouMaiWuPinModel;
import com.shengxiangui.table.WuPinXinXiMoel;

import java.util.List;

public class JieSuanListAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {


    public JieSuanListAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

        GouMaiWuPinModel.DataBean.DataListBean item1 = (GouMaiWuPinModel.DataBean.DataListBean) item;
        helper.setText(R.id.tv_mingcheng, item1.getCs_wares_name());
        Glide.with(mContext).load(item1.getCs_wares_img_url()).into((ImageView) helper.getView(R.id.iv_tupian));

        helper.setText(R.id.danjia_jinshu, "销售价： " + item1.getCs_selling_price() + "/" + item1.getCs_per_unit());
        helper.setText(R.id.tv_zongjinshu, "本次购买： " + item1.getCs_wares_wm() + "/" + item1.getCs_wares_unit());
        helper.setText(R.id.tv_zongjinshu, "总金额： " + item1.getCs_total_price());
    }
}
