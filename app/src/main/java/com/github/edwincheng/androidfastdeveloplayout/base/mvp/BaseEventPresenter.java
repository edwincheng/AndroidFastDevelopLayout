package com.github.edwincheng.androidfastdeveloplayout.base.mvp;

import com.github.edwincheng.androidfastdeveloplayout.base.BaseView;
import org.greenrobot.eventbus.EventBus;
import java.lang.ref.WeakReference;

public class BaseEventPresenter<V extends BaseView, M> implements IBaseXPresenter {
    // 防止 Activity 不走 onDestory() 方法，所以采用弱引用来防止内存泄漏
    public WeakReference<V> mViewRef;
    protected M mModel;

    public BaseEventPresenter(V view) {
        attachView(view);
        EventBus.getDefault().register(this);
    }

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    public V getView() {
        return mViewRef.get();
    }

    @Override
    public boolean isViewAttach() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        mModel = null;
        EventBus.getDefault().unregister(this);
    }
}
