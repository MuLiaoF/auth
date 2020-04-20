package cn.wandingkeji.auth.utils;

import cn.wandingkeji.auth.contants.ResultData;
import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceInvocationHandler<T> implements InvocationHandler {

    private T target;

    private Logger log;

    private String message;

    public ServiceInvocationHandler(T target, Logger log, String message){
        this.target = target;
        this.log = log;
        this.message = message;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object resultData = null;
        try {
            resultData  =  method.invoke(target, args);
        } catch (Exception e) {
            resultData = ExceptionConstantsUtils.printErrorMessage(log, e, message + "失败");
        }
        return resultData;
    }


}
