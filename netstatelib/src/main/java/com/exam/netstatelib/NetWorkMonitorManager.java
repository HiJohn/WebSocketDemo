package com.exam.netstatelib;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class NetWorkMonitorManager {

    private static final String TAG = "NetWorkMonitorManager";
    private Application application;

//    public static NetWorkMonitorManager getInstance() {
//        if (ourInstance==null) {
//            synchronized (NetWorkMonitorManager.class) {
//                if (ourInstance == null) {
//                    ourInstance = new NetWorkMonitorManager();
//                }
//            }
//        }
//        return ourInstance;
//    }

    private static class MonitorHolder{
        private static final NetWorkMonitorManager monitorManager= new NetWorkMonitorManager();
    }

    public static NetWorkMonitorManager getInstance(){
        return MonitorHolder.monitorManager;
    }


    /**
     * 存储接受网络状态变化消息的方法的map
     */
    private HashMap<Object, NetWorkStateReceiverMethod> netWorkStateChangedMethodMap = new HashMap<>();

    private NetWorkMonitorManager() {
    }
    /**
     * 初始化 传入application
     *
     * @param application app
     */
    public void init(Application application) {
        if (application == null) {
            throw new NullPointerException("application can not be null");
        }
        this.application = application;
        initMonitor();
    }

    /**
     * 初始化网络监听 根据不同版本做不同的处理
     */
    private void initMonitor() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.application.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//API 大于26时
            if (connectivityManager != null) {
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//API 大于21时
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            if (connectivityManager != null) {
                connectivityManager.registerNetworkCallback(request, networkCallback);
            }
        }
    }


    /**
     * 注入
     * @param object o
     */
    public void register(Object object) {
        if (this.application == null) {
            throw new NullPointerException("application can not be null,please call the method init(Application application) to add the Application");
        }
        if (object != null) {
            NetWorkStateReceiverMethod netWorkStateReceiverMethod = findMethod(object);
            if (netWorkStateReceiverMethod != null) {
                netWorkStateChangedMethodMap.put(object, netWorkStateReceiverMethod);
            }
        }
    }

    /**
     * 删除
     *
     * @param object o
     */
    public void unregister(Object object) {
        if (object != null && netWorkStateChangedMethodMap != null) {
            netWorkStateChangedMethodMap.remove(object);
        }
    }

    /**
     * 网络状态发生变化，需要去通知更改
     * @param netWorkState ns
     */
    private void postNetState(NetWorkState netWorkState) {
        Set<Object> set = netWorkStateChangedMethodMap.keySet();
        for (Object object : set) {
            NetWorkStateReceiverMethod netWorkStateReceiverMethod = netWorkStateChangedMethodMap.get(object);
            invokeMethod(netWorkStateReceiverMethod, netWorkState);

        }
    }

    /**
     * 具体执行方法
     *
     * @param netWorkStateReceiverMethod nsrm
     * @param netWorkState ns
     */
    private void invokeMethod(NetWorkStateReceiverMethod netWorkStateReceiverMethod, NetWorkState netWorkState) {
        if (netWorkStateReceiverMethod != null) {
            try {
                NetWorkState[] netWorkStates = netWorkStateReceiverMethod.getNetWorkState();
                for (NetWorkState myState : netWorkStates) {
                    if (myState == netWorkState) {
                        Log.d(TAG,"method invoked");
                        netWorkStateReceiverMethod.getMethod().invoke(netWorkStateReceiverMethod.getObject(), netWorkState);
                        return;
                    }
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 找到对应的方法
     *
     * @param object o
     * @return f
     */
    private NetWorkStateReceiverMethod findMethod(Object object) {
        NetWorkStateReceiverMethod targetMethod;
        if (object != null) {
            Class myClass = object.getClass();
            //获取所有的方法
            Method[] methods = myClass.getDeclaredMethods();
            for (Method method : methods) {
                //如果参数个数不是1个 直接忽略
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (method.getParameterCount() != 1) {
                        continue;
                    }
                }
                //获取方法参数
                Class[] parameters = method.getParameterTypes();
                if (parameters.length != 1) {
                    continue;
                }
                //参数的类型需要时NetWorkState类型
                if (parameters[0].getName().equals(NetWorkState.class.getName())) {
                    //是NetWorkState类型的参数
                    NetWorkMonitor netWorkMonitor = method.getAnnotation(NetWorkMonitor.class);
                    targetMethod = new NetWorkStateReceiverMethod();
                    //如果没有添加注解，默认就是所有网络状态变化都通知
                    if (netWorkMonitor != null) {
                        NetWorkState[] netWorkStates = netWorkMonitor.monitorFilter();
                        targetMethod.setNetWorkState(netWorkStates);
                    }
                    targetMethod.setMethod(method);
                    targetMethod.setObject(object);
                    //只添加第一个符合的方法
                    return targetMethod;
                }
            }
        }
        return null;
    }


    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        /**
         * 网络可用的回调连接成功
         */
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            int netType = NetStateUtils.getAPNType(NetWorkMonitorManager.this.application);
            NetWorkState netWorkState = NetWorkState.NONE;
            switch (netType) {
                case 0://None
                    break;
                case 1://Wifi
                    netWorkState = NetWorkState.WIFI;
                    break;
                default://GPRS
                    netWorkState = NetWorkState.GPRS;
                    break;
            }
            postNetState(netWorkState);
        }

        /**
         * 网络不可用时调用和onAvailable成对出现
         */
        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            postNetState(NetWorkState.NONE);
        }

        /**
         * 在网络连接正常的情况下，丢失数据会有回调 即将断开时
         */
        @Override
        public void onLosing(@NonNull Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
        }

        /**
         * 网络功能更改 满足需求时调用
         * @param network net
         * @param networkCapabilities cap
         */
        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
        }

        /**
         * 网络连接属性修改时调用
         * @param network n
         * @param linkProperties lp
         */
        @Override
        public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
        }

        /**
         * 网络缺失network时调用
         */
        @Override
        public void onUnavailable() {
            super.onUnavailable();
        }
    };
}
