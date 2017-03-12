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
    Header header;

    static class Header {
        public String key;
        public String value;
    }

    enum HttpRequestType {
        GET, POST, JSON, BYTES, STRING, DELETE, HEAD, OPTIONS, PUT
    }

    enum HttpRequestContent {
        DEFAULT, FILE
    }
}
