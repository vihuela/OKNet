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

import java.lang.reflect.Type;

import ricky.oknet.callback.AbsCallback;
import ricky.oknet.model.HttpParams;

/************************************************************
 * Author:  Zhouml
 * Description:     // 模块描述
 * Date: 2016/3/15
 ************************************************************/
public class NetRequestData {

    public static enum HttpRequestType{
        GET,POST;
    }

    public static enum HttpRequestContent{
        DEFAULT,FILE;
    }

    static final int REQUEST_FILE = 1;
    static final int REQUEST_DEFAULT = 3;
    AbsCallback context;

    String url;
    HttpRequestType type;
    HttpRequestContent requestContent;
    Type[] types;

    String methodName;
    HttpParams params;
    boolean shouldCache;

    public NetRequestData() {
    }

    public NetRequestData(AbsCallback context, String url, HttpRequestType type, HttpRequestContent requestContent, Type[] types, String methodName, HttpParams params, boolean shouldCache) {
        this.context = context;
        this.url = url;
        this.type = type;
        this.requestContent = requestContent;
        this.types = types;
        this.methodName = methodName;
        this.params = params;
        this.shouldCache = shouldCache;
    }
}
