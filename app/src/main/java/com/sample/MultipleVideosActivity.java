package com.sample;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.shengxiangui.cn.R;


public class MultipleVideosActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_multiple_videos);
        if (state == null) {
            addVideoFragment(Video.ORANGE_1, R.id.videoContainer0);
            addVideoFragment(Video.ORANGE_2, R.id.videoContainer1);
            addVideoFragment(Video.ORANGE_3, R.id.videoContainer2);
            addVideoFragment(Video.ORANGE_4, R.id.videoContainer3);
        }
    }

    private void addVideoFragment(Video video, int containerViewId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, VideoFragment.newInstance(video.url))
                .commit();
    }
}
