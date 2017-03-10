package ricky.oknet.retrofit;

import java.lang.reflect.Type;

import ricky.oknet.cache.CacheMode;
import ricky.oknet.model.HttpParams;

public class NetRequestData {

    HttpRequestContent requestContent;
    Type[] types;
    String methodName;
    String url;
    HttpRequestType type;
    //互斥
    HttpParams params;

    String jsonParam;
    byte[] byteParam;
    String stringParam;

    CacheMode cacheMode;
    long cacheTime;

    enum HttpRequestType {
        GET, POST, JSON, BYTES, STRING
    }
    enum HttpRequestContent {
        DEFAULT, FILE
    }
}
