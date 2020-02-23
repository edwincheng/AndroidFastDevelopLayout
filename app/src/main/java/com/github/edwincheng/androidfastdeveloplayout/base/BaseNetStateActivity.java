package com.github.edwincheng.androidfastdeveloplayout.base;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.github.edwincheng.androidfastdeveloplayout.base.eventbean.ConnectionStatusEvent;
import com.github.edwincheng.androidfastdeveloplayout.base.mvp.IBaseXPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseNetStateActivity<P extends IBaseXPresenter> extends AppCompatActivity {
    public P mPresenter;

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract P createPresenter();
    public abstract void init();
    public abstract void receivingNetworkState(int tyle);//网络状态的判断
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        setContentView(getLayoutId());

        // TODO: add setContentView(...) invocation
        bind = ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        EventBus.getDefault().register(this);
        init();
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

    /**
     * 跳转页面
     * @param clazz
     */
    public void go2(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    /**
     * 网络断开
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionStatusEvent(ConnectionStatusEvent connectionStatusEvent) {
        switch (connectionStatusEvent.getConnectionStatus()) {
            case ConnectionStatusEvent.SERVER_DISCONNECT://服务器断开
                /** 设置全局的网络状态标志 */
//                Application.getGlobalConstantsBean().setConnect(false);
                receivingNetworkState(ConnectionStatusEvent.SERVER_DISCONNECT);
                break;
            case ConnectionStatusEvent.WIFI_DISCONNECT://网络断开
                break;
            case ConnectionStatusEvent.ISCONNECT://网络连接
                break;
            case ConnectionStatusEvent.SERVER_ISCONNECT://服务器成功
                /** 设置全局的网络状态标志 */
//                Application.getGlobalConstantsBean().setConnect(true);
                receivingNetworkState(ConnectionStatusEvent.SERVER_ISCONNECT);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.detachView();
    }
}

