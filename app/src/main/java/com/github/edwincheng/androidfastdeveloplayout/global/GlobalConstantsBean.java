package com.github.edwincheng.androidfastdeveloplayout.global;


/**
 * Create by zhengwp on 19-2-21.
 * 全局变量类，非静态类可修改值
 */
public class GlobalConstantsBean {
    /** 客户端网络是否正常 */
    private boolean isHasNetwork;
    /** 与服务器的连接是否正常 */
    private boolean isHasServer;
    /** Current ip-Address and meeting room Id */
    private String currentIp;

    public boolean getIsHasNetwork() {
        return isHasNetwork;
    }

    public void setIsHasNetwork(boolean isHasNetwork) {
        this.isHasNetwork = isHasNetwork;
    }

    public boolean getIsHasServer() {
        return isHasServer;
    }

    public void setIsHasServer(boolean isHasServer) {
        this.isHasServer = isHasServer;
    }

    public String getCurrentIp() {
        return currentIp;
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }
}
