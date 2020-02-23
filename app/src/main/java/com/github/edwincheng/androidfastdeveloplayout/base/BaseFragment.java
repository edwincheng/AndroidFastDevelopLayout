package com.github.edwincheng.androidfastdeveloplayout.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.edwincheng.androidfastdeveloplayout.base.mvp.IBaseXPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IBaseXPresenter>  extends Fragment {

    //引用V层和P层
    private P mPresenter;
    public Context mContext;
    public Unbinder unbinder;

    public P getmPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }
    /**
     * 获取 Presenter 对象，在需要获取时才创建`Presenter`，起到懒加载作用
     */
    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        return mPresenter;
    }


    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract P createPresenter();
    public abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
    }
}
