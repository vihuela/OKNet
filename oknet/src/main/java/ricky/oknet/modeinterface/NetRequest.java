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

/************************************************************
 * Author:  Zhouml
 * Description:     // 模块描述
 * Date: 2016/3/15
 ************************************************************/
public class NetRequest<T> {


    BaseRequest baseRequest;

    AbsCallback<T> callback ;

    NetRequestData data = null;
    public int what;

    public NetRequest(){

    }

    public NetRequest(NetRequestData data) {
        setData(data);
    }

    public void setData(NetRequestData data){
        if (data != null) {
            this.data = data;
            what = data.methodName.hashCode();

            switch (this.data.type){
                case GET:
                    baseRequest = OkHttpUtils.get(this.data.url);
                    break;
                case POST:
                    baseRequest = OkHttpUtils.post(this.data.url);
                    break;

            }
            baseRequest.params(this.data.params);

            //1. 网络回调不为空， 正常执行网络请求
            if(null != data.context){
                this.callback = data.context;
            }
            if (null != this.callback) {
                baseRequest.execute(callback);
            }
        }

    }

    /**
     * 未设置当前当前请求回调时的操作
     * @param callBack
     */
    public void beginRequest(AbsCallback callBack) {
        if (null != callBack) {
            this.setCallBack(callBack);
            if(null != this.data){
                setData(this.data);
            }
        }
    }

    /**
     * 设置请求回调
     * @param callBack
     */
    private void setCallBack(AbsCallback<T> callBack) {
        this.callback = callBack;
    }

    public void cancel() {
        OkHttpUtils.getInstance().cancelTag(this);
    }


}
