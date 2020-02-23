package com.github.edwincheng.androidfastdeveloplayout.base.mvp;

import com.github.edwincheng.androidfastdeveloplayout.base.BaseView;
import java.lang.ref.WeakReference;

public class BasePresenter<V extends BaseView, M> implements IBaseXPresenter {
    // 防止 Activity 不走 onDestory() 方法，所以采用弱引用来防止内存泄漏
    public WeakReference<V> mViewRef;
    protected M mModel;

    public BasePresenter(V view) {
        attachView(view);
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
    }
}