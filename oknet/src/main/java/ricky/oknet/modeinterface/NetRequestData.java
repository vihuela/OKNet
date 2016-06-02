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

import ricky.oknet.cache.CacheMode;
import ricky.oknet.callback.AbsCallback;
import ricky.oknet.model.HttpParams;

public class NetRequestData {

    public enum HttpRequestType {
        GET, POST,POSTJSON
    }

    public enum HttpRequestContent {
        DEFAULT, FILE
    }

    static final int REQUEST_FILE = 1;
    static final int REQUEST_DEFAULT = 3;

    String url;
    HttpRequestType type;
    HttpRequestContent requestContent;
    Type[] types;

    String methodName;
    HttpParams params;
    String jsonParam;
    CacheMode cacheMode;
}
