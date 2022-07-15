package com.shengxiangui.cn.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    // 定义Fragment列表来存放Fragment
    private List<Fragment> fragmentList;

    // 1. 定义构造方法
    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.fragmentList = listFragment;
    }

    @NonNull
    @Override
    // 2. 显示页面，为数组中的Fragment’
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    // 3. 获取页面的个数，几位列表的长度
    public int getCount() {
        return fragmentList.size();
    }
}
