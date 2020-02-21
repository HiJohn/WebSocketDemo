package com.exam.netstatelib;

import java.lang.reflect.Method;

public class NetWorkStateReceiverMethod {


    /**
     * 网络改变执行的方法
     */
    Method method;
    /**
     * 网络改变执行的方法所属的类
     */
    Object object;
    /**
     * 监听的网络改变类型
     */
    private NetWorkState[] netWorkState = {NetWorkState.GPRS, NetWorkState.WIFI, NetWorkState.NONE};

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public NetWorkState[] getNetWorkState() {
        return netWorkState;
    }

    public void setNetWorkState(NetWorkState[] netWorkState) {
        this.netWorkState = netWorkState;
    }
}
