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
import ricky.oknet.request.GetRequest;
import ricky.oknet.request.PostRequest;

public class NetRequest<T> {


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
                execInner(OkHttpUtils.get(data.url), callback);
                break;
            case POST:
                execInner(OkHttpUtils.post(data.url), callback);
                break;
            case POSTJSON:
                PostRequest r = OkHttpUtils.post(data.url);
                r.postJson(data.jsonParam);
                execInner(r, callback);
                break;
        }
    }

    private void execInner(BaseRequest req, AbsCallback callback) {
        req.cacheMode(data.cacheMode);
        req.params(data.params);
        req.tag(this);
        req.execute(callback);
    }

    public void cancel() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
