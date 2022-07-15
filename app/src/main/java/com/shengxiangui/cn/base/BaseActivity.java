package com.shengxiangui.cn.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.shengxiangui.cn.Notice;
import com.shengxiangui.cn.RxBus;
import com.shengxiangui.cn.RxUtils;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends FragmentActivity {

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
}
