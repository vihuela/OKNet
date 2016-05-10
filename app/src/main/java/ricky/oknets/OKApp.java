package ricky.oknets;

import android.app.Application;

import ricky.oknet.OkHttpUtils;
import ricky.oknet.model.HttpHeaders;
import ricky.oknet.model.HttpParams;

/**
 * @author YaoWeihui on 2016/5/10.
 */
public class OKApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpUtils.init(this);

       /* HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");*/
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        params.put("commonParamsKey2", "这里支持中文参数");

        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)            //全局的写入超时时间
                /*.addCommonHeaders(headers)   */                                      //设置全局公共头
                .addCommonParams(params);
    }
}
