package com.shengxiangui.cn.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.R;
import com.shengxiangui.cn.RxBus;
import com.shengxiangui.cn.RxUtils;
import com.shengxiangui.cn.adapter.JieSuanListAdapter;
import com.shengxiangui.cn.model.GouMaiWuPinModel;

import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity<T> extends FragmentActivity {

    protected CompositeSubscription _subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }

    /**
     * 注册事件通知
     */
    public Observable<Notice> toObservable() {
        return RxBus.getDefault().toObservable(Notice.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!_subscriptions.isUnsubscribed()) {
            _subscriptions.unsubscribe();
        }
    }

    public JieSuanListAdapter jieSuanListAdapter;


    public void initAdapter(RecyclerView rlvList, List<T> listBeans) {
        jieSuanListAdapter = new JieSuanListAdapter(R.layout.layout_jiesuan_liebiao, listBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvList.setLayoutManager(linearLayoutManager);
        rlvList.setAdapter(jieSuanListAdapter);
    }
}
