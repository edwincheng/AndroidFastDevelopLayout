package com.github.edwincheng.androidfastdeveloplayout.base.listener;

public interface TcpResponseListener{
    /**
     * 网络请求成功
     *
     * @param strType 请求的标记
     * @param json   返回的数据
     */
    void onSuccess(int strType, String json);

    /**
     * 网络请求失败
     *
     * @param strType     请求的标记
     */
    void onFailure(int strType);
}
