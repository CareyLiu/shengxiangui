package com.shengxiangui.cn;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.sample.Video;
import com.sample.Video_BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XiLanHome_video_Activity extends FragmentActivity {
    ImmersionBar mImmersionBar;

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

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerView, Video_BaseFragment.newInstance(Video.ORANGE_1.url))
                    .commit();
        }
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(R.id.rl_tupian).init();


        DisplayMetrics dm = getResources().getDisplayMetrics();

        int screenWidth = dm.widthPixels;

        int screenHeight = dm.heightPixels;

        System.out.println("width" + screenWidth + "height" + screenHeight);

        rlCunbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiLanCunBaoActivity.actionStart(XiLanHome_video_Activity.this);
            }
        });
        rlQubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //XiLianQubaoActivity.actionStart(XiLanHome_video_Activity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
