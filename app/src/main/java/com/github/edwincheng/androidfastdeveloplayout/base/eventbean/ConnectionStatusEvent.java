package com.github.edwincheng.androidfastdeveloplayout.base.eventbean;


public class ConnectionStatusEvent {
    /** 断开服务器连接 */
    public final static int SERVER_DISCONNECT = 1001;
    /** 断开网络 */
    public final static int WIFI_DISCONNECT = 1002;
    /** 连接上网络 */
    public final static int ISCONNECT = 1000;
    /** 服务器连接成功 */
    public final static int SERVER_ISCONNECT = 1003;

    private int mConnectionStatus;//
    public ConnectionStatusEvent(int mConnectionStatus) {
        this.mConnectionStatus = mConnectionStatus;
    }

    public int getConnectionStatus() {
        return mConnectionStatus;
    }
}