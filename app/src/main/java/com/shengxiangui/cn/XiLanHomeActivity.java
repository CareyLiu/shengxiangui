package com.shengxiangui.cn;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XiLanHomeActivity extends Activity {
    ImmersionBar mImmersionBar;
    @BindView(R.id.rl_tupian)
    RelativeLayout rlTupian;
    @BindView(R.id.tv_zimu)
    TextView tvZimu;
    @BindView(R.id.tv_hanzi)
    TextView tvHanzi;
    @BindView(R.id.rl_cunbao)
    RelativeLayout rlCunbao;
    @BindView(R.id.rl_qubao)
    RelativeLayout rlQubao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(R.id.rl_tupian).init();


        DisplayMetrics dm = getResources().getDisplayMetrics();

        int screenWidth = dm.widthPixels;

        int screenHeight = dm.heightPixels;

        System.out.println("width" + screenWidth + "height" + screenHeight);

        rlCunbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiLanCunBaoActivity.actionStart(XiLanHomeActivity.this);
            }
        });
        rlQubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //XiLianQubaoActivity.actionStart(XiLanHomeActivity.this);
            }
        });
    }
}
