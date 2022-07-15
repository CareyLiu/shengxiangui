package com.shengxiangui.cn.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.shengxiangui.cn.R;
import com.shengxiangui.cn.adapter.ShangPinLieBiaoAdapter;
import com.shengxiangui.cn.model.ShangPinLieBiaoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShangPinFragment extends Fragment {

    public static final String TAG = ShangPinFragment.class.getSimpleName();
    Bundle bundle;
    List<ShangPinLieBiaoModel.DataBean> mDatas = new ArrayList<>();
    ShangPinLieBiaoAdapter shangPinLieBiaoAdapter;
    RecyclerView rlvList;

    RelativeLayout rlMain;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments();
            mDatas = (List<ShangPinLieBiaoModel.DataBean>) bundle.getSerializable("shuju");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_shangpinlist, null);
        rlvList = view.findViewById(R.id.rlv_list);
        rlMain = view.findViewById(R.id.rl_main);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        shangPinLieBiaoAdapter = new ShangPinLieBiaoAdapter(R.layout.item_shangpin_liebiao, mDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rlvList.setLayoutManager(gridLayoutManager);
        rlvList.setAdapter(shangPinLieBiaoAdapter);
    }
}
