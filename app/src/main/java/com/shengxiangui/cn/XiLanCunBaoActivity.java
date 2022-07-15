package com.shengxiangui.cn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.shengxiangui.ZxingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XiLanCunBaoActivity extends Activity {
    ImmersionBar mImmersionBar;
    @BindView(R.id.rl_tupian)
    RelativeLayout rlTupian;
    @BindView(R.id.tv_zimu)
    TextView tvZimu;
    @BindView(R.id.tv_hanzi)
    TextView tvHanzi;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.rl_cunbao)
    RelativeLayout rlCunbao;
    @BindView(R.id.ll_fanhui)
    LinearLayout llFanhui;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cunbao);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(R.id.rl_tupian).init();


        DisplayMetrics dm = getResources().getDisplayMetrics();

        int screenWidth = dm.widthPixels;

        int screenHeight = dm.heightPixels;

        System.out.println("width" + screenWidth + "height" + screenHeight);

        Bitmap bitmap = ZxingUtils.createQRImage("abc", 175, 175);
        ivErweima.setImageBitmap(bitmap);

        llFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, XiLanCunBaoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
