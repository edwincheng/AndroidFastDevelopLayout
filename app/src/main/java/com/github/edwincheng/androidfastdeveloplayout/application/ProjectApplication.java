package com.github.edwincheng.androidfastdeveloplayout.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import com.github.edwincheng.androidfastdeveloplayout.global.Config;
import com.github.edwincheng.androidfastdeveloplayout.global.GlobalConstantsBean;
import com.github.edwincheng.androidfastdeveloplayout.utils.AppUtils;
import com.github.edwincheng.androidfastdeveloplayout.utils.CrashHandle;
import com.github.edwincheng.androidfastdeveloplayout.utils.ELog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @ file name:    :ProjectApplication
 * @ author        : edwincheng
 * @ e-mail        : zwp_edwincheng@163.com
 * @ date          : 20-2-24 15:17
 * @ description   :
 * @ modify author :
 * @ modify date   :
 */
public class ProjectApplication extends Application {
    private static Context mContext;
    private static ProjectApplication mApplication;
    //全局变量类，非静态类可修改值
    private static GlobalConstantsBean mGlobalConstantsBean;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initbugly();
    }

    private void init() {
        mContext = this;
        mGlobalConstantsBean = new GlobalConstantsBean();

        //注册网络监听的广播
        AppUtils.registerReceiverNetwork(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//大于24时。避免wps打开文件时FileUriExposedException
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        /** 是否在控制台输出调试日志 */
        ELog.setShowLog(false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppUtils.unregisterReceiverNetwork(this);
    }

    private void initbugly() {
        CrashHandle.getInstance().init(mContext);
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, Config.BUGLY_APP_ID, false, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static GlobalConstantsBean getmGlobalConstantsBean() {
        return mGlobalConstantsBean;
    }

    public static Context getmContext() {
        return mContext;
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0

    }

}
