package ricky.oknet.utils;

import android.support.annotation.Nullable;

import java.lang.reflect.Proxy;

import ricky.oknet.modeinterface.NetUtil;

/**
 * @author YaoWeihui on 2016/5/25.
 */
@SuppressWarnings("all")
public class ApiHelper {
    /**
     *
     * @param c
     * @param callback 拦截json请求类的回调
     * @param <T> Api接口类
     * @param <F> 如请求方式为PostJson时，需要拦截json请求类
     * @return
     */
    public static <T, F> T get(Class<T> c, @Nullable NetUtil.ICustomerJsonBean<F>... callback) {
        return (T) Proxy.newProxyInstance(
                c.getClassLoader(),
                new Class[]{c},
                new NetUtil<F>(callback != null && callback.length > 0 ? callback[0] : null));
    }
}
