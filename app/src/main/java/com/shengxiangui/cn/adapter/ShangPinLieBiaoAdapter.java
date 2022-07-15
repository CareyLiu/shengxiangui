package com.shengxiangui.cn.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengxiangui.cn.R;
import com.shengxiangui.cn.model.ShangPinLieBiaoModel;

import java.util.List;

public class ShangPinLieBiaoAdapter extends BaseQuickAdapter<ShangPinLieBiaoModel.DataBean, BaseViewHolder> {


    public ShangPinLieBiaoAdapter(int layoutResId, @Nullable List<ShangPinLieBiaoModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShangPinLieBiaoModel.DataBean item) {
        helper.setText(R.id.tv_name_1, item.cs_wares_name);
        Glide.with(mContext).load(item.cs_wares_img_url).into((ImageView) helper.getView(R.id.iv_tupian_1));
        helper.setText(R.id.tv_jiage_1, item.cs_selling_price);
    }
}
