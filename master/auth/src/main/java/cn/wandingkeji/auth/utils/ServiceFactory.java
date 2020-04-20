package cn.wandingkeji.auth.utils;

import org.slf4j.Logger;

import java.lang.reflect.Proxy;

public class ServiceFactory {

    /**
     * 获取业务层代理对象
     * @param service
     * @param log
     * @return
     */
    public static <T> T getService(T service, Logger log, String message){
        return (T) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new ServiceInvocationHandler(service, log, message));
    }
}
