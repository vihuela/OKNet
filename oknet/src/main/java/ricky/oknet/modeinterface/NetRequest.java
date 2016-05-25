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


import ricky.oknet.OkHttpUtils;
import ricky.oknet.callback.AbsCallback;
import ricky.oknet.request.BaseRequest;

public class NetRequest<T> {


    BaseRequest baseRequest;

    public Object what;
    private NetRequestData data;

    public NetRequest(NetRequestData data) {
        this.data = data;
    }

    public void execute(AbsCallback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback not be null");
        }

        what = this;

        switch (data.type) {
            case GET:
                baseRequest = OkHttpUtils.get(data.url);
                break;
            case POST:
                baseRequest = OkHttpUtils.post(data.url);
                break;
        }
        baseRequest.cacheMode(data.cacheMode);
        baseRequest.params(data.params);
        baseRequest.tag(this);
        baseRequest.execute(callback);
    }

    public void cancel() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
