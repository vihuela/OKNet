package ricky.oknet.retrofit;


import ricky.oknet.OkGo;
import ricky.oknet.callback.AbsCallback;
import ricky.oknet.lifecycle.INet;
import ricky.oknet.lifecycle.INetQueue;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.request.PostRequest;

public class Net<T> implements INet<T> {

    private NetRequestData data;


    Net(NetRequestData data) {
        this.data = data;
    }

    /**
     * 执行请求
     *
     * @param callback  回调
     * @param iNetQueue 网络队列，用于管理网络与View的绑定
     */
    @Override
    public void execute(AbsCallback<T> callback, INetQueue... iNetQueue) {

        if (callback == null) {
            throw new IllegalArgumentException("callback not be null");
        }

        switch (data.type) {
            case GET:
                execInner(OkGo.get(data.url), callback, iNetQueue);
                break;
            case POST:
                execInner(OkGo.post(data.url), callback, iNetQueue);
                break;
            case DELETE:
                execInner(OkGo.delete(data.url), callback, iNetQueue);
                break;
            case HEAD:
                execInner(OkGo.head(data.url), callback, iNetQueue);
                break;
            case OPTIONS:
                execInner(OkGo.options(data.url), callback, iNetQueue);
                break;
            case PUT:
                execInner(OkGo.put(data.url), callback, iNetQueue);
                break;
            case JSON:
                PostRequest rJson = OkGo.post(data.url);
                rJson.upJson(data.jsonParam);
                execInner(rJson, callback, iNetQueue);
                break;
            case BYTES:
                PostRequest rBytes = OkGo.post(data.url);
                rBytes.upBytes(data.byteParam);
                execInner(rBytes, callback, iNetQueue);
                break;
            case STRING:
                PostRequest rString = OkGo.post(data.url);
                rString.upString(data.stringParam);
                execInner(rString, callback, iNetQueue);
                break;
            default:
                throw new IllegalArgumentException("no match request type");
        }

    }

    private void execInner(BaseRequest req, AbsCallback<T> callback, INetQueue... iNetQueue) {

        if (data.cacheMode != null) {
            req.cacheMode(data.cacheMode);
            req.cacheTime(data.cacheTime);
        }
        if (data.header != null) {
            req.headers(data.header.key, data.header.value);
        }
        req.params(data.params);
        req.tag(data.methodName);

        //Take queue to take over
        if (iNetQueue != null && iNetQueue.length == 1) {
            INetQueue mINetQueue = iNetQueue[0];
            mINetQueue.add(this);
        }
        req.execute(callback);

    }

    @Override
    public void cancel() {
        OkGo.getInstance().cancelTag(data.methodName);
    }

}
