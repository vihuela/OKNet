package ricky.oknet.retrofit;


import ricky.oknet.OkGo;
import ricky.oknet.callback.AbsCallback;
import ricky.oknet.convert.Converter;
import ricky.oknet.lifecycle.INet;
import ricky.oknet.lifecycle.INetQueue;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.request.DeleteRequest;
import ricky.oknet.request.GetRequest;
import ricky.oknet.request.HeadRequest;
import ricky.oknet.request.OptionsRequest;
import ricky.oknet.request.PostRequest;
import ricky.oknet.request.PutRequest;
import ricky.oknet.rx.RxAdapter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

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

        BaseRequest req;
        switch (data.type) {
            case GET:
                req = OkGo.get(data.url);
                break;
            case POST:
                req = OkGo.post(data.url);
                break;
            case DELETE:
                req = OkGo.delete(data.url);
                break;
            case HEAD:
                req = OkGo.head(data.url);
                break;
            case OPTIONS:
                req = OkGo.options(data.url);
                break;
            case PUT:
                req = OkGo.put(data.url);
                break;
            case JSON:
                PostRequest rJson = OkGo.post(data.url);
                rJson.upJson(data.jsonParam);
                req = rJson;
                break;
            case BYTES:
                PostRequest rBytes = OkGo.post(data.url);
                rBytes.upBytes(data.byteParam);
                req = rBytes;
                break;
            case STRING:
                PostRequest rString = OkGo.post(data.url);
                rString.upString(data.stringParam);
                req = rString;
                break;
            default:
                throw new IllegalArgumentException("no match request type");
        }

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

    /**
     * 执行请求
     *
     * @param converter 数据转换
     * @param callback  传入则使用AbsCallback的回调与错误解析，简化配置默认Rx回调，不影响外部使用
     * @param iNetQueue 队列
     * @return
     */
    @Override
    public Observable<T> rx(Converter<T> converter, final AbsCallback<T> callback, INetQueue... iNetQueue) {

        final BaseRequest req;
        Observable<T> ob;

        switch (data.type) {
            case GET:
                GetRequest g = OkGo.get(data.url);
                ob = g.getCall(converter, RxAdapter.<T>create());//g must a request impl
                req = g;
                break;
            case POST:
                PostRequest p = OkGo.post(data.url);
                ob = p.getCall(converter, RxAdapter.<T>create());
                req = p;
                break;
            case DELETE:
                DeleteRequest d = OkGo.delete(data.url);
                ob = d.getCall(converter, RxAdapter.<T>create());
                req = d;
                break;
            case HEAD:
                HeadRequest h = OkGo.head(data.url);
                ob = h.getCall(converter, RxAdapter.<T>create());
                req = h;
                break;
            case OPTIONS:
                OptionsRequest o = OkGo.options(data.url);
                ob = o.getCall(converter, RxAdapter.<T>create());
                req = o;
                break;
            case PUT:
                PutRequest pu = OkGo.put(data.url);
                ob = pu.getCall(converter, RxAdapter.<T>create());
                req = pu;
                break;
            case JSON:
                PostRequest rJson = OkGo.post(data.url);
                rJson.upJson(data.jsonParam);
                ob = rJson.getCall(converter, RxAdapter.<T>create());
                req = rJson;
                break;
            case BYTES:
                PostRequest rBytes = OkGo.post(data.url);
                rBytes.upBytes(data.byteParam);
                ob = rBytes.getCall(converter, RxAdapter.<T>create());
                req = rBytes;
                break;
            case STRING:
                PostRequest rString = OkGo.post(data.url);
                rString.upString(data.stringParam);
                ob = rString.getCall(converter, RxAdapter.<T>create());
                req = rString;
                break;
            default:
                throw new IllegalArgumentException("no match request type");
        }

        if (data.cacheMode != null) {
            req.cacheMode(data.cacheMode);
            req.cacheTime(data.cacheTime);
        }
        if (data.header != null) {
            req.headers(data.header.key, data.header.value);
        }
        req.params(data.params);


        if (callback != null) {
            //default config
            Subscription subscribe = ob.doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    callback.onBefore(req);
                }
            })
                    .observeOn(AndroidSchedulers.mainThread())//consumer thread
                    .subscribe(new Action1<T>() {
                        @Override
                        public void call(T t) {
                            callback.onSuccess(t, null, null);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            callback.onError(null, null, throwable);
                        }
                    });
            //Take queue to take over
            if (iNetQueue != null && iNetQueue.length == 1) {
                INetQueue mINetQueue = iNetQueue[0];
                mINetQueue.addSubscription(subscribe);
            }
        }

        return ob;

    }

    @Override
    public void cancel() {
        OkGo.getInstance().cancelTag(data.methodName);
    }

}
