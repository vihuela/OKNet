/*
 *
 *   Copyright 2016 YunDi
 *
 *   The code is part of Yunnan, Shenzhen Branch of the internal architecture of YunDi source group
 *
 *   DO NOT DIVULGE
 *
 */

package ricky.oknet.modeinterface;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ricky.oknet.cache.CacheMode;
import ricky.oknet.modeinterface.annotation.CACHEMOD;
import ricky.oknet.modeinterface.annotation.GET;
import ricky.oknet.modeinterface.annotation.PARAMS;
import ricky.oknet.modeinterface.annotation.POST;
import ricky.oknet.model.HttpParams;

@SuppressWarnings("all")
public class NetUtil implements InvocationHandler {

    @Override
    public NetRequest invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        String url = null;
        CacheMode cacheMode = CacheMode.DEFAULT;
        NetRequestData.HttpRequestType type = null;
        NetRequestData.HttpRequestContent requestType = containFileOrBitmap(args);
        NetRequestData data;

        if (method.isAnnotationPresent(POST.class)) {
            POST post = method.getAnnotation(POST.class);
            url = post.value();
            type = NetRequestData.HttpRequestType.POST;
        }

        if (method.isAnnotationPresent(GET.class)) {
            GET post = method.getAnnotation(GET.class);
            url = post.value();
            type = NetRequestData.HttpRequestType.GET;
        }
        if (method.isAnnotationPresent(CACHEMOD.class)) {
            CACHEMOD cacheModeA = method.getAnnotation(CACHEMOD.class);
            cacheMode = cacheModeA.value();
        }
        data = defaultProcess(method, args, url, type);
        data.requestContent = requestType;
        data.types = getTType(method);
        data.cacheMode = cacheMode;
        return new NetRequest(data);
    }

    private Type[] getTType(Method method) {
        Type[] types = null;
        try {
            Type returnType = method.getGenericReturnType();// 返回类型
            types = ((ParameterizedType) returnType).getActualTypeArguments();
        } catch (GenericSignatureFormatError error) {
            error.printStackTrace();
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        } catch (MalformedParameterizedTypeException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
        }
        return types;
    }

    /**
     * 判断方法的参数是否有文件或者bitmap
     *
     * @param objects
     * @return
     */
    private NetRequestData.HttpRequestContent containFileOrBitmap(Object[] objects) {
        if (objects == null)
            return NetRequestData.HttpRequestContent.DEFAULT;
        for (Object obj : objects) {
            if (Bitmap.class.isInstance(obj)) {
                return NetRequestData.HttpRequestContent.FILE;
            } else if (File.class.isInstance(obj)) {
                return NetRequestData.HttpRequestContent.FILE;
            }
        }
        return NetRequestData.HttpRequestContent.DEFAULT;
    }


    /**
     * 没有文件的请求处理
     *
     * @param method
     * @param objs
     * @param url
     * @param type
     */
    private NetRequestData defaultProcess(Method method, Object[] objs, String url, NetRequestData.HttpRequestType type) {

        List<String> paramsName = getMethodParameterNamesByAnnotation(method);

        HttpParams httpParams = new HttpParams();
        int i = 0;
        for (String name : paramsName) {
            if (objs == null || objs[i] == null) {
                httpParams.put(name, "");
            } else if (File.class.isInstance(objs[i])) {
                httpParams.put(name, (File) objs[i]);
            } else if (Bitmap.class.isInstance(objs[i])) {
                httpParams.put(name, bitmap2bytes((Bitmap) objs[i]));
            } else {
                httpParams.put(name, objs[i].toString() + "");
            }
            i++;
        }

        NetRequestData netRequestData = new NetRequestData();
        netRequestData.url = url;
        netRequestData.type = type;
        netRequestData.methodName = method.getName();
        netRequestData.params = httpParams;
        return netRequestData;
    }

    private byte[] bitmap2bytes(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    public static List<String> getMethodParameterNamesByAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return new ArrayList<>();
        }
        List<String> parameteNames = new ArrayList<>();
        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof PARAMS) {
                    PARAMS param = (PARAMS) annotation;
                    parameteNames.add(param.value());
                }
            }
        }
        return parameteNames;
    }

}
