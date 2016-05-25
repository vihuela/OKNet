package ricky.oknet.utils;

import java.lang.reflect.Proxy;

import ricky.oknet.modeinterface.NetUtil;

/**
 * @author YaoWeihui on 2016/5/25.
 */
@SuppressWarnings("all")
public class ApiHelper {
    public static <T> T get(Class<T> c) {
        return (T) Proxy.newProxyInstance(
                c.getClassLoader(),
                new Class[]{c},
                new NetUtil());
    }
}
