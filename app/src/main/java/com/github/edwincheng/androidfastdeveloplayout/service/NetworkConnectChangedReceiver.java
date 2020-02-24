package com.github.edwincheng.androidfastdeveloplayout.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.github.edwincheng.androidfastdeveloplayout.application.ProjectApplication;
import com.github.edwincheng.androidfastdeveloplayout.base.eventbean.ConnectionStatusEvent;

import org.greenrobot.eventbus.EventBus;


public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())|| intent.getAction().equals(
                "android.net.conn.CONNECTIVITY_CHANGE")){
            if (isNetworkAvailable(context)==0){//没网络
                ProjectApplication.getmGlobalConstantsBean().setIsHasNetwork(false);
                Log.i("fafaafaafh","没网络连接");
                EventBus.getDefault().post(new ConnectionStatusEvent(ConnectionStatusEvent.WIFI_DISCONNECT));
            }else if (isNetworkAvailable(context)==1||(isNetworkAvailable(context)==2)){
                ProjectApplication.getmGlobalConstantsBean().setIsHasNetwork(true);
                Log.i("fafaafaafh","网络连接"+isNetworkAvailable(context));
                EventBus.getDefault().post(new ConnectionStatusEvent(ConnectionStatusEvent.ISCONNECT));
            }
        }

    }

    public int isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ethNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (ethNetInfo != null && ethNetInfo.isConnected()) {
            return 1;
        } else if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
            return 2;
        } else {
            return 0;
        }
    }
}
