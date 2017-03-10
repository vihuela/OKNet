package ricky.oknet.retrofit;


import ricky.oknet.OkGo;
import ricky.oknet.callback.AbsCallback;
import ricky.oknet.lifecycle.INetLifecycle;
import ricky.oknet.lifecycle.INetViewLifecycle;
import ricky.oknet.lifecycle.NetLifecycleMgr;
import ricky.oknet.lifecycle.OKNetBehavior;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.request.PostRequest;

public class NetRequest<T> implements INetLifecycle {


    public Object what;
    private NetRequestData data;

    public NetRequest(NetRequestData data) {
        this.data = data;
    }

    public NetRequest<T> bind(INetViewLifecycle viewLifecycle) {
        NetLifecycleMgr.Instance.addRequest(viewLifecycle, this);
        return this;
    }

    public void execute(AbsCallback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback not be null");
        }

        what = this;

        switch (data.type) {
            case GET:
                execInner(OkGo.get(data.url), callback);
                break;
            case POST:
                execInner(OkGo.post(data.url), callback);
                break;
            case JSON:
                PostRequest rJson = OkGo.post(data.url);
                rJson.upJson(data.jsonParam);
                execInner(rJson, callback);
                break;
            case BYTES:
                PostRequest rBytes = OkGo.post(data.url);
                rBytes.upBytes(data.byteParam);
                execInner(rBytes, callback);
                break;
            case STRING:
                PostRequest rString = OkGo.post(data.url);
                rString.upString(data.stringParam);
                execInner(rString, callback);
                break;
        }
    }

    private void execInner(BaseRequest req, AbsCallback callback) {
        if (data.cacheMode != null) {
            req.cacheMode(data.cacheMode);
            req.cacheTime(data.cacheTime);
        }
        req.params(data.params);
        req.tag(this);
        req.execute(callback);
    }

    public void cancel() {
        NetLifecycleMgr.Instance.$("cancel");
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onNetBehavior(@OKNetBehavior int behavior) {
        switch (behavior) {
            case OKNetBehavior.STOP:
            case OKNetBehavior.DESTROY:
                cancel();
                break;
        }
    }
}
