package com.shengxiangui.cn.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.sample.Video;
import com.sample.Video_BaseFragment;
import com.shengxiangui.cn.R;

public class ShiPinActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //val dm = resources.displayMetrics

        //val screenWidth = dm.widthPixels

        //val screenHeight = dm.heightPixels

        // println("width" + screenWidth + "height" + screenHeight)

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerView, Video_BaseFragment.newInstance(Video.ORANGE_13.url))
                    .commit();

        }
    }
}
